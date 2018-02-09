package com.mmbao.saas.utils.im;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mmbao.saas.R;
import com.mmbao.saas.activity.base.BaseActivity;


/**
 * Author    LeoCheung
 * Version   V1.0
 * Email     leocheung4ever@gmail.com
 * Date      2016-04-04 13:32
 * Description  常用语界面
 * Date          Author          Version          Description
 * ------------------------------------------------------------------
 * 2016/4/4     LeoCheung       1.0              1.0
 * Why & What is modified:
 */
public class ShortCutMsgActivity extends BaseActivity {

    private ArrayAdapter<String> mAdapter;
    private ListView mListView;
    protected int[] promptItemStrings = {R.string.text_fahuo, R.string.text_weight, R.string.text_color, R.string.text_kuaidi};

    @Override
    public void initContentView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.em_activity_shortcut);
    }

    @Override
    public void initData() {
        mListView = (ListView) findViewById(R.id.list);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
    }


    public void initView() {
        for (int item : promptItemStrings) {
            mAdapter.add(getString(item));
        }
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new ListOnItemClick());
    }


    class ListOnItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String content = parent.getItemAtPosition(position).toString();
            setResult(RESULT_OK, new Intent().putExtra("content", content));
            closeActivity();
        }
    }

    class onBackClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            closeActivity();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeActivity();
    }

    private void closeActivity() {
        finish();
        overridePendingTransition(0, R.anim.activity_close);
    }
}
