package cn.com.cunw.diandubiapp.uis.moresource;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.cunw.diandubiapp.R;
import cn.com.cunw.diandubiapp.beans.SourceBean;
import cn.com.cunw.diandubiapp.interfaces.MySourceDialogListener;
import cn.com.cunw.diandubiapp.views.SourceItemView;

/**
 * @author YangBin
 * @time 2019/8/5 12:00
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class MoreSourceAdapter extends RecyclerView.Adapter<MoreSourceAdapter.VH> {

    private Context mContext;
    private List<SourceBean> mList;

    public MoreSourceAdapter() {
        mList = new ArrayList<>();
    }

    public void setList(List<SourceBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_source, viewGroup, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH vh, int position) {
        vh.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vis = vh.view_content.getVisibility();
                int icon = 0;
                int bg = 0;
                if (vis == View.VISIBLE) {
                    vis = View.GONE;
                    icon = R.drawable.ic_ex_down;
                    bg = R.color.white;
                } else {
                    vis = View.VISIBLE;
                    icon = R.drawable.ic_ex_up;
                    bg = R.color.selected_bg;
                }
                vh.tv_name.setBackgroundResource(bg);
                vh.iv_icon.setImageResource(icon);
                vh.view_content.setVisibility(vis);
            }
        });

        SourceBean bean = mList.get(position);
        vh.tv_name.setText(bean.gradeName + " " + bean.termName);
        vh.ll_content.removeAllViews();
        List<SourceBean.ItemBean> list = bean.bookList;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                SourceItemView itemView = new SourceItemView(mContext);
                itemView.setItemBean(list.get(i));
                itemView.setMySourceDialogListener(mMySourceDialogListener);
                vh.ll_content.addView(itemView);
            }
        }
    }

    private MySourceDialogListener mMySourceDialogListener;

    public void setMySourceDialogListener(MySourceDialogListener mySourceDialogListener) {
        mMySourceDialogListener = mySourceDialogListener;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private ImageView iv_icon;
        private View view_content;
        private LinearLayout ll_content;

        public VH(View v) {
            super(v);
            tv_name = v.findViewById(R.id.tv_name);
            iv_icon = v.findViewById(R.id.iv_icon);
            view_content = v.findViewById(R.id.view_content);
            ll_content = v.findViewById(R.id.ll_content);
        }
    }
}
