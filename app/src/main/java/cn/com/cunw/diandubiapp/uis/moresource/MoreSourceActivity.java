package cn.com.cunw.diandubiapp.uis.moresource;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.com.cunw.diandubiapp.R;
import cn.com.cunw.diandubiapp.base.mvp.BaseMvpActivity;
import cn.com.cunw.diandubiapp.beans.SourceBean;
import cn.com.cunw.diandubiapp.dialogs.SourceDialog;
import cn.com.cunw.diandubiapp.interfaces.MySourceDialogListener;
import cn.com.cunw.diandubiapp.utils.NetworkUtil;
import cn.com.cunw.diandubiapp.utils.ToastUtis;

/**
 * 更多资源
 */
public class MoreSourceActivity extends BaseMvpActivity<MoreSourcePresenter> implements SwipeRefreshLayout.OnRefreshListener, MoreSourceView {

    @Override
    public int initLayout() {
        return R.layout.activity_more_source;
    }

    public static void startActivity(Context context) {
        if (!NetworkUtil.isNetworkAvailable(context)) {
            ToastUtis.show(R.string.no_net);
            return;
        }
        Intent intent = new Intent(context, MoreSourceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected MoreSourcePresenter createPresenter() {
        return new MoreSourcePresenter(this);
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MoreSourceAdapter mAdapter;
    private SourceDialog mSourceDialog;

    @Override
    public void initViews() {
        super.initViews();
        mSwipeRefreshLayout = findViewById(R.id.srl_source);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = findViewById(R.id.rcv_source);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MoreSourceAdapter();
        mAdapter.setMySourceDialogListener(new MySourceDialogListener() {
            @Override
            public void showDialog() {
                showDiaog();
            }
        });
        recyclerView.setAdapter(mAdapter);

        load();
    }

    private void showDiaog() {
        if (mSourceDialog == null) {
            mSourceDialog = new SourceDialog(this);
        }
        mSourceDialog.show();
    }

    @Override
    public void onRefresh() {
        load();
    }

    private void load() {
        mPresenter.getMoreSourceList();
    }

    @Override
    public void initList(List<SourceBean> list) {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.setList(list);
    }

    @Override
    public void initAutoList(List<SourceBean.ItemBean> list) {

    }
}
