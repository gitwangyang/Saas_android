package com.mmbao.saas.utils.im;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.mmbao.saas.R;
import com.mmbao.saas.fragment.base.BaseFragment;
import com.mmbao.saas.utils.EaseUtil;
import com.mmbao.saas.utils.SharedPrenfenceUtil;

import butterknife.OnClick;

/**
 * 用户选择客服组界面
 */
public class WorkgroupSelectFragment extends BaseFragment {
    private int currentGroupId = 1;
    private String currentGroupName = "";
    private String currentIMCode = "";


    @Override
    public int setContentView(int layout) {
        layout = R.layout.im_workgroupselect;
        return layout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            //如果进入技能组选择页面时候，系统存有推送消息，那么直接跳转对应的技能组聊天页面
            if(SharedPrenfenceUtil.getInstance().getBleanValue("DXDL", false)){
                currentIMCode = EaseUtil.getEasemobIMCode1();
                currentGroupId = 1;
                currentGroupName = "电线电缆";

                SharedPrenfenceUtil.getInstance().putBooleanValue("DXDL", false);
                //延迟0.2秒后直接进入对应的聊天页面
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(200);
                            mHandler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }else if(SharedPrenfenceUtil.getInstance().getBleanValue("DGDQ_JJFJ", false)){
                currentIMCode = EaseUtil.getEasemobIMCode2();
                currentGroupId = 2;
                currentGroupName = "电工电气";

                SharedPrenfenceUtil.getInstance().putBooleanValue("DGDQ_JJFJ", false);
                //延迟0.2秒后直接进入对应的聊天页面
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(200);
                            mHandler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

        }
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                //带着推送消息直接进入对应的聊天页面
                case 1:
                    checkAccount();
                    break;
                default:
                    break;
            }
        }
    };

    @OnClick({R.id.im_select_dxdl, R.id.im_select_dgdq, R.id.im_select_jjfj})
    void click(final View v) {
        //记录用户选择了哪一个频道
        switch (v.getId()) {
            case R.id.im_select_dxdl:
                currentIMCode = EaseUtil.getEasemobIMCode1();
                currentGroupId = 1;
                currentGroupName = "电线电缆";
                break;
            case R.id.im_select_dgdq:
                currentIMCode = EaseUtil.getEasemobIMCode2();
                currentGroupId = 2;
                currentGroupName = "电工电气";
                break;
            case R.id.im_select_jjfj:
                currentIMCode = EaseUtil.getEasemobIMCode2();
                currentGroupId = 2;
                currentGroupName = "金具附件";
                break;
            default:
                break;
        }

        checkAccount();
    }

    private void checkAccount() {
        Intent intent = new Intent(getActivity(), IMActivity.class);
        intent.putExtra("imGroup", currentGroupId);
        intent.putExtra("imGroupName", currentGroupName);
        intent.putExtra(IMConstant.EXTRA_USER_ID, currentIMCode);
        startActivity(intent);
//        if (SystemInfo.getInstance(getActivity()).getMemberid().equals("")) {
//            if(EMChat.getInstance().isLoggedIn()){
//                registerStartIM();
//            }else {
//                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE).setTitleText("友情提示").setContentText("您当前尚未登录买卖宝，以游客身份进行的在线咨询不保留聊天记录。").setConfirmText("登录").setCancelText("游客登录").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sDialog) {
//                        sDialog.dismiss();
//                        startActivityForResult(new Intent(getActivity(), Login.class), 204);
//                    }
//                }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.dismiss();
//                        registerStartIM();
//                    }
//                }).show();
//            }
//        } else {  //已登录MMB
//            startIM();
//        }
    }

//    /**
//     * 登录状态下登录IM并进入聊天界面
//     */
//    private void startIM() {
//        loginIMServer("mmbao_android_" + SystemInfo.getInstance(MMBApplication.getInstance()).getMemberid(), "123456");
//    }
//
//    /**
//     * 未登录状态下，注册并登录IM并进入聊天界面
//     */
//    private void registerStartIM() {
//        final String imAccount = MMBApplication.getInstance().imei.replace("-", "");
//        final String userPwd = IMConstant.DEFAULT_ACCOUNT_PWD;
//
//        EaseChatUtil.register(imAccount,  new EMCallBack() {
//            @Override
//            public void onSuccess() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //登录环信服务器
//                        loginIMServer(imAccount, userPwd);
//                    }
//                });
//            }
//
//            @Override
//            public void onError(final int errorCode, final String message) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (errorCode == EMError.NONETWORK_ERROR) {
//                            LogcatUtil.i("网络不可用");
//                        } else if (errorCode == EMError.USER_ALREADY_EXISTS) {
//                            LogcatUtil.i("用户已存在");
//                            // TODO: 2016/8/6 注册提示已存在的时候，直接登录
//                            loginIMServer(imAccount, userPwd);
//                        } else if (errorCode == EMError.UNAUTHORIZED) {
//                            LogcatUtil.i("无开放注册权限");
//                        } else if (errorCode == EMError.ILLEGAL_USER_NAME) {
//                            LogcatUtil.i("用户名非法");
//                        } else {
//                            LogcatUtil.i("注册失败：" + message);
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onProgress(int i, String s) {
//
//            }
//        });
//    }

//    /**
//     * 登录IM
//     */
//    private void loginIMServer(final String uname, final String pwd) {
//        EMChatManager.getInstance().login(uname, pwd, new EMCallBack() {
//            @Override
//            public void onSuccess() {
//                LogcatUtil.i("IM登录成功 name = " + uname);
//                IMHelper.getInstance().setCurrentUserName(uname);
//                IMHelper.getInstance().setCurrentPassword(pwd);
//                try {
//                    EMChatManager.getInstance().loadAllConversations();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return;
//                }
//                Intent intent = new Intent(getActivity(), IMActivity.class);
//                intent.putExtra("imGroup", currentGroupId);
//                intent.putExtra("imGroupName", currentGroupName);
//                intent.putExtra(IMConstant.EXTRA_USER_ID, currentIMCode);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onProgress(int progress, String status) {
//            }
//
//            @Override
//            public void onError(final int code, final String message) {
//                getActivity().runOnUiThread(new Runnable() {
//                    public void run() {
//                        Toast.makeText(MMBApplication.getInstance(), getResources().getString(R.string.is_contact_customer_failure_seconed) + message, Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//    }


}
