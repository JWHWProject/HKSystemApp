package cn.nj.www.my_module.tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.constant.NotiTag;
import de.greenrobot.event.EventBus;

/**
 * <弹出框公共类> <功能详细描述>
 *
 * @version [版本号, 2014-3-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DialogUtil
{


    /**
     * <一个按钮的dialog>
     */
    public static void showDialogOneButton(Context context, String title, String sumbit, final String tag)
    {
        final Dialog dialog = new Dialog(context, R.style.main_dialog);
        dialog.setContentView(R.layout.one_button_dialog);
        dialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().getAttributes().height = WindowManager.LayoutParams.WRAP_CONTENT;
        TextView tvContent = (TextView) dialog.findViewById(R.id.dialogName_tv);
        Button btSubmit = (Button) dialog.findViewById(R.id.transfer_ok_bn);
        tvContent.setText(title);
        btSubmit.setText(sumbit);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        btSubmit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                EventBus.getDefault().post(new NoticeEvent(tag));
            }
        });
    }
    public static void showCloseDialogOneButton(final Context context, String title, String sumbit, final String tag)
    {
        final Dialog dialog = new Dialog(context, R.style.main_dialog);
        dialog.setContentView(R.layout.one_button_dialog);
        dialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().getAttributes().height = WindowManager.LayoutParams.WRAP_CONTENT;
        TextView tvContent = (TextView) dialog.findViewById(R.id.dialogName_tv);
        Button btSubmit = (Button) dialog.findViewById(R.id.transfer_ok_bn);
        tvContent.setText(title);
        btSubmit.setText(sumbit);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        btSubmit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                ((Activity)context).finish();
                dialog.dismiss();
                EventBus.getDefault().post(new NoticeEvent(tag));
            }
        });
    }
    public static void startTrainDialog(final Context context, final String tag)
    {
        final Dialog dialog = new Dialog(context, R.style.main_dialog);
        dialog.setContentView(R.layout.start_train_dialog);
        dialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().getAttributes().height = WindowManager.LayoutParams.WRAP_CONTENT;
        final EditText etCard = (EditText) dialog.findViewById(R.id.et_card);
        final EditText etName = (EditText) dialog.findViewById(R.id.et_name);
        Button bnCancel = (Button) dialog.findViewById(R.id.transfer_cancel_bn);
        Button bnOk = (Button) dialog.findViewById(R.id.transfer_ok_bn);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        bnCancel.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        bnOk.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (GeneralUtils.isNotNullOrZeroLenght(etCard.getText().toString()) &&GeneralUtils.isNotNullOrZeroLenght(etName.getText().toString())){
                    EventBus.getDefault().post(new NoticeEvent(tag,etCard.getText().toString(),etName.getText().toString()));
                    dialog.dismiss();
                }else {
                    ToastUtil.makeText(context,"请填写相关信息");
                }
            }
        });
    }

    public static void showNoTipTwoBnttonDialog(Context context, String title, String left, String right, final String leftTag, final String rightTag)
    {
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
        leftBn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                EventBus.getDefault().post(new NoticeEvent(leftTag));
                dialog.dismiss();
            }
        });
        rightBn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                EventBus.getDefault().post(new NoticeEvent(rightTag));
                dialog.dismiss();
            }
        });
    }

    public static void showCloseTwoBnttonDialog(final Context context, String title, String left, String right)
    {
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
        leftBn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                dialog.dismiss();
            }
        });
        rightBn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                ((Activity)context).finish();
                dialog.dismiss();
            }
        });
    }
    public static void exitAccountDialog(final Context context)
    {
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
        leftBn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                dialog.dismiss();
            }
        });
        rightBn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                Global.loginOut(context);
                dialog.dismiss();
                EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_CLOSE_ACTIVITY_FROM_DIALOG));
            }
        });
    }


    public static Dialog initDialog(Context context, int layout)
    {
        Dialog oneButtonDialog = new Dialog(context, R.style.from_bottom_dialog);
        oneButtonDialog.setContentView(layout);
        oneButtonDialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
        oneButtonDialog.getWindow().getAttributes().height = WindowManager.LayoutParams.WRAP_CONTENT;
        oneButtonDialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
        oneButtonDialog.getWindow().setWindowAnimations(R.style.dialog_style);
        return oneButtonDialog;
    }

}
