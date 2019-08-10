package cn.com.cunw.diandubiapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import cn.com.cunw.diandubiapp.R;

/**
 * @author YangBin
 * @time 2019/8/5 20:10
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class SourceDialog extends Dialog {
    public SourceDialog(Context context) {
        super(context, R.style.CustomProgressDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_source);
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
