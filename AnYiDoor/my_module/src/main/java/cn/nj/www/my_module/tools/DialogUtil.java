package cn.nj.www.my_module.tools;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Global;
import de.greenrobot.event.EventBus;

/**
 * <弹出框公共类> <功能详细描述>
 *
 * @version [版本号, 2014-3-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DialogUtil {

    public static void showNoTipTwoBnttonDialog(Context context, String title, String left, String right, final String leftTag, final String rightTag) {
        final Dialog dialog = new Dialog(context, R.style.main_dialog);
        dialog.setContentView(R.layout.person_save_barcode_dialog);
        dialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().getAttributes().height = WindowManager.LayoutParams.WRAP_CONTENT;
        TextView tvDialogName = (TextView) dialog.findViewById(R.id.dialogName_tv);
        Button leftBn = (Button) dialog.findViewById(R.id.transfer_ok_bn);
        Button rightBn = (Button) dialog.findViewById(R.id.transfer_cancel_bn);

        tvDialogName.setText(title);
        leftBn.setText(left);
        rightBn.setText(right);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setWindowAnimations(R.style.main_dialog);
        leftBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EventBus.getDefault().post(new NoticeEvent(leftTag));
                dialog.dismiss();
            }
        });
        rightBn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new NoticeEvent(rightTag));
                dialog.dismiss();
            }
        });
    }

    public static void exitAccountDialog(final Context context) {
        final Dialog dialog = new Dialog(context, R.style.main_dialog);
        dialog.setContentView(R.layout.person_save_barcode_dialog);
        dialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().getAttributes().height = WindowManager.LayoutParams.WRAP_CONTENT;
        TextView tvDialogName = (TextView) dialog.findViewById(R.id.dialogName_tv);
        Button leftBn = (Button) dialog.findViewById(R.id.transfer_ok_bn);
        Button rightBn = (Button) dialog.findViewById(R.id.transfer_cancel_bn);

        tvDialogName.setText("确定退出登录");
        leftBn.setText("取消");
        rightBn.setText("确定");
        rightBn.setTextColor(context.getResources().getColor(R.color.app_title_blue1));
        leftBn.setTextColor(context.getResources().getColor(R.color.app_state_red1));
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setWindowAnimations(R.style.main_dialog);
        leftBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        rightBn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Global.loginOut(context);
                dialog.dismiss();
            }
        });
    }





    public static Dialog initDialog(Context context, int layout) {
        Dialog oneButtonDialog = new Dialog(context, R.style.from_bottom_dialog);
        oneButtonDialog.setContentView(layout);
        oneButtonDialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
        oneButtonDialog.getWindow().getAttributes().height = WindowManager.LayoutParams.WRAP_CONTENT;
        oneButtonDialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
        oneButtonDialog.getWindow().setWindowAnimations(R.style.dialog_style);
        return oneButtonDialog;
    }

}
