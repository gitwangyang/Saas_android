package com.mmbao.saas.activity.login.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mmbao.saas.R;
import com.mmbao.saas.fragment.base.BaseFragment;
import com.mmbao.saas.utils.LogcatUtil;
import com.mmbao.saas.utils.NetworkUtils;
import com.mmbao.saas.utils.custom.ClearEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:com.mmbao.saas.activity.login类
 * Created by Administrator on 2018/2/3.
 * Maxim:There is no smoke without fire
 */
public class FragmentUser extends BaseFragment {

    @Bind(R.id.login_username)
    ClearEditText etLoginUsername;
    @Bind(R.id.login_password)
    EditText etLoginPassword;
    @Bind(R.id.login_psw_show_img)
    ImageView loginPswShowImg;

    private boolean progressShow;

    @Override
    public int setContentView(int layout) {
        layout = R.layout.fragment_user;
        return layout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        etLoginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etLoginPassword.setText(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etLoginPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN ))) {
                    loginFormat(null);
                    return true;
                }
                else{
                    return false;
                }
            }
        });
    }

    private void loginFormat(View view) {
        if (!NetworkUtils.checkNetworkState(getActivity())){
            Toast.makeText(getActivity(), "请检查网络后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        String currentUsername = etLoginUsername.getText().toString().trim();
        String currentPassword = etLoginPassword.getText().toString().trim();
        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(getActivity(), "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        progressShow = true;
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                LogcatUtil.d("成功失败");
                progressShow = false;
            }
        });
//        pd.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialogInterface) {
//                //TODO 登录成功的接口调用
//            }
//        });
        pd.setMessage(getString(R.string.Is_landing));
        pd.show();
    }

    public static Fragment getInstance() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.login_psw_show_img)
    public void onViewClicked() {
    }
}
