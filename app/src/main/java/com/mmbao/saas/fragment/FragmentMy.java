package com.mmbao.saas.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mmbao.saas.R;
import com.mmbao.saas.activity.login.controller.LoginActivity;
import com.mmbao.saas.fragment.base.BaseFragment;
import com.mmbao.saas.utils.custom.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bajieaichirou on 17/9/6.
 * 个人中心
 */
public class FragmentMy extends BaseFragment {
    @Bind(R.id.layout_setting)
    RelativeLayout layoutSetting;
    @Bind(R.id.btn_login_center)
    Button btnLoginCenter;
    @Bind(R.id.btn_register_center)
    Button btnRegisterCenter;
    @Bind(R.id.ll_nologin_center)
    LinearLayout llNologinCenter;
    @Bind(R.id.iv_p_center_headPortrait)
    CircleImageView ivPCenterHeadPortrait;
    @Bind(R.id.tv_p_center_username)
    TextView tvPCenterUsername;
    @Bind(R.id.iv_vip)
    ImageView ivVip;
    @Bind(R.id.tv_p_center_addv)
    TextView tvPCenterAddv;
    @Bind(R.id.tv_noAuth_company)
    TextView tvNoAuthCompany;
    @Bind(R.id.ll_login_center)
    LinearLayout llLoginCenter;
    @Bind(R.id.ll_p_center_my_order)
    LinearLayout llPCenterMyOrder;
    @Bind(R.id.tv_paymentCount)
    TextView tvPaymentCount;
    @Bind(R.id.ll_p_center_my_order_topay)
    LinearLayout llPCenterMyOrderTopay;
    @Bind(R.id.tv_deliveryCount)
    TextView tvDeliveryCount;
    @Bind(R.id.ll_p_center_my_order_todeliver)
    LinearLayout llPCenterMyOrderTodeliver;
    @Bind(R.id.tv_receivedCount)
    TextView tvReceivedCount;
    @Bind(R.id.ll_p_center_my_order_getdeliver)
    LinearLayout llPCenterMyOrderGetdeliver;
    @Bind(R.id.tv_completeCount)
    TextView tvCompleteCount;
    @Bind(R.id.ll_p_center_my_order_complete)
    LinearLayout llPCenterMyOrderComplete;
    @Bind(R.id.ll_p_center_my_order_all)
    LinearLayout llPCenterMyOrderAll;
    @Bind(R.id.my_order_payment)
    LinearLayout myOrderPayment;
    @Bind(R.id.ll_p_center_fight_order)
    LinearLayout llPCenterFightOrder;
    @Bind(R.id.ll_p_center_my_buy)
    LinearLayout llPCenterMyBuy;
    @Bind(R.id.ll2)
    LinearLayout ll2;
    @Bind(R.id.ll_p_center_step)
    LinearLayout llPCenterStep;
    @Bind(R.id.ll_p_center_attention)
    LinearLayout llPCenterAttention;
    @Bind(R.id.ll_customerservice)
    LinearLayout llCustomerservice;
    @Bind(R.id.ll_more_linear_hotline)
    LinearLayout llMoreLinearHotline;
    @Bind(R.id.ll_aboutas)
    LinearLayout llAboutas;
    @Bind(R.id.ll3)
    LinearLayout ll3;

    public FragmentMy() {
    }

    @Override
    public int setContentView(int layout) {
        layout = R.layout.fragment_my;
        return layout;
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

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

    @OnClick({R.id.layout_setting, R.id.btn_login_center, R.id.btn_register_center, R.id.ll_p_center_my_order_topay, R.id.ll_p_center_my_order_todeliver, R.id.ll_p_center_my_order_getdeliver, R.id.ll_p_center_my_order_complete, R.id.ll_p_center_my_order_all, R.id.ll_p_center_fight_order, R.id.ll_p_center_my_buy, R.id.ll_p_center_step, R.id.ll_p_center_attention, R.id.ll_customerservice, R.id.ll_more_linear_hotline, R.id.ll_aboutas})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_setting:
                break;
            case R.id.btn_login_center:
                break;
            case R.id.btn_register_center:
                break;
            case R.id.ll_p_center_my_order_topay:
                break;
            case R.id.ll_p_center_my_order_todeliver:
                break;
            case R.id.ll_p_center_my_order_getdeliver:
                break;
            case R.id.ll_p_center_my_order_complete:
                break;
            case R.id.ll_p_center_my_order_all:
                break;
            case R.id.ll_p_center_fight_order:
                startActivity(LoginActivity.class);
                break;
            case R.id.ll_p_center_my_buy:
                break;
            case R.id.ll_p_center_step:
                break;
            case R.id.ll_p_center_attention:
                break;
            case R.id.ll_customerservice:
                break;
            case R.id.ll_more_linear_hotline:
                break;
            case R.id.ll_aboutas:
                break;
        }
    }
}
