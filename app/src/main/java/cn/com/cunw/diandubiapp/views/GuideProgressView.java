package cn.com.cunw.diandubiapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.cunw.diandubiapp.R;

/**
 * @author YangBin
 * @time 2019/8/9 10:54
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class GuideProgressView extends RelativeLayout {
    public GuideProgressView(Context context) {
        super(context);
        init(context);
    }

    public GuideProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private TextView tv_progress;
    private View view_pro;
    private ImageView iv_pro_s;

    private static final String LOAD_HINT = "正在加载中%d%%";
    private ViewGroup.LayoutParams mParams;
    private RelativeLayout.LayoutParams mParamsS;
    private int mWidth = 0;
    private int mLeftMargin = 0;
    private int mTopMargin = 0;

    private void init(Context context) {
        mTopMargin = (int) getResources().getDimension(R.dimen.dp_12);
        mLeftMargin = (int) getResources().getDimension(R.dimen.dp_2);

        LayoutInflater.from(context).inflate(R.layout.view_progress, this);
        tv_progress = findViewById(R.id.tv_progress);
        view_pro = findViewById(R.id.view_pro);
        iv_pro_s = findViewById(R.id.iv_pro_s);

        tv_progress.setText(String.format(LOAD_HINT, 0));
        mParams = view_pro.getLayoutParams();
        mWidth = (int) getResources().getDimension(R.dimen.dp_240);
        mParams.width = 0;
        mParamsS = (LayoutParams) iv_pro_s.getLayoutParams();
        mParamsS.setMargins(mLeftMargin, mTopMargin, 0, 0);
    }

    public void updateRate(int rate) {
        if (rate >= 0 && rate <= 100) {
            tv_progress.setText(String.format(LOAD_HINT, rate));
            mParams.width = mLeftMargin * 3 + rate * mWidth / 100;
            int left = rate * mWidth / 100;
            mParamsS.setMargins(left + mLeftMargin, mTopMargin, 0, 0);
        }
    }
}
