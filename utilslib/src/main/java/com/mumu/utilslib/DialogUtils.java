package com.mumu.utilslib;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 作者　　: 朱林峰
 * 创建时间: 2018/5/17
 * 邮箱　　：mamumuma1001@sina.com
 * <p>
 * 功能介绍：仿照ios的弹窗(圆角)
 */
public class DialogUtils {
    /**
     * 通用创建dialog
     *
     * @param context          上下文
     * @param title            弹框的标题
     * @param message          弹框的内容
     * @param messageImageId   图片的资源id
     * @param messageView      view
     * @param positiveText     确定按钮的文字显示
     * @param negativeText     取消按钮的文字显示
     * @param positiveListener 确定按钮的实例化监听
     * @param negativeListener 取消按钮的实例化监听
     * @param touchOutside     是否支持点击外部取消
     * @param cancelable       是否支持按返回取消
     * @return 返回对话框实例
     */
    private synchronized static AlertDialog showDialogIOS(Context context,
                                                         String title,
                                                         CharSequence message,
                                                         int messageImageId,
                                                         View messageView,
                                                         String positiveText,
                                                         String negativeText,
                                                         DialogInterface.OnClickListener positiveListener,
                                                         DialogInterface.OnClickListener negativeListener,
                                                         boolean touchOutside,
                                                         boolean cancelable) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // 是否包含标题，设置Title
        if (TextUtils.isEmpty(title)) {
            title = "提示";
        }

        // 包含内容的时候，设置Message
        boolean hasMsg = !TextUtils.isEmpty(message) || messageImageId > 0 || null != messageView;
        // 如果没有msg,则默认把title赋值给msg展示
        if (!hasMsg) {
            if (TextUtils.isEmpty(title)) {
                return null;
            } else {
                hasMsg = true;
                message = title;
                title = "提示";
            }
        }

        // 只要标题和内容有一个不是空就显示Dialog
        AlertDialog dialog = alertDialog.create();
        dialog.setCanceledOnTouchOutside(touchOutside);
        dialog.setCancelable(cancelable);

        //region NOTE:加载对话框样式,仿IOS
        View view = View.inflate(context, R.layout.alert_dialog_msg, null);

        //一个button的提示框
        LinearLayout llButtonOneRoot = (LinearLayout) view.findViewById(R.id.ll_alert_dialog_msg_one_btn_root);
        //两个button的提示框
        LinearLayout llButtonTwoRoot = (LinearLayout) view.findViewById(R.id.ll_alert_dialog_msg_two_btn_root);

        //提示框message根节点
        RelativeLayout rlMessageViewRoot = (RelativeLayout) view.findViewById(R.id.rl_alert_dialog_msg_root);
        //提示框title
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_alert_dialog_msg_title);
        //提示框message
        TextView tvMsg = (TextView) view.findViewById(R.id.tv_alert_dialog_msg_msg);
        //提示框message图片说明
        ImageView ivMsg = (ImageView) view.findViewById(R.id.iv_alert_dialog_msg_image);
        //一个按钮
        Button btnOneOk = (Button) view.findViewById(R.id.btn_alert_dialog_msg_one_ok);
        //两个按钮
        Button btnTwoOk = (Button) view.findViewById(R.id.btn_alert_dialog_msg_two_ok);
        Button btnTwoCancel = (Button) view.findViewById(R.id.btn_alert_dialog_msg_two_cancel);

        //底部按钮的根容器
        LinearLayout llButtonsRoot = (LinearLayout) view.findViewById(R.id.ll_alert_dialog_msg_btns_root);

        if (title.equals("提示")) {
            tvTitle.setVisibility(View.GONE);
        }
        //设置title和message的内容
        tvTitle.setText(title);

        rlMessageViewRoot.setVisibility(hasMsg ? View.VISIBLE : View.GONE);
        tvMsg.setVisibility(hasMsg ? View.VISIBLE : View.GONE);
        ivMsg.setVisibility(hasMsg ? View.VISIBLE : View.GONE);
        if (hasMsg) {
            tvMsg.setText(TextUtils.isEmpty(message) ? "" : message);
            ivMsg.setImageResource(messageImageId > 0 ? messageImageId : 0);
        }

        //判断是否替换message界面为外部view
        if (null != messageView) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            messageView.setLayoutParams(params);
            rlMessageViewRoot.addView(messageView);
            rlMessageViewRoot.setVisibility(View.VISIBLE);
        }

        if (null == negativeListener) {
            negativeListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            };
        }

        if (null == positiveListener) {
            positiveListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            };
        }

        final AlertDialog dialogFinal = dialog;
        //设置button样式
        if (TextUtils.isEmpty(positiveText) && TextUtils.isEmpty(negativeText)) {
            //隐藏底部按钮区域
            llButtonsRoot.setVisibility(View.GONE);
        } else if (TextUtils.isEmpty(positiveText) || TextUtils.isEmpty(negativeText)) {
            llButtonsRoot.setVisibility(View.VISIBLE);
            llButtonOneRoot.setVisibility(View.VISIBLE);
            llButtonTwoRoot.setVisibility(View.GONE);

            btnOneOk.setText(TextUtils.isEmpty(positiveText) ? negativeText : positiveText);
            final DialogInterface.OnClickListener finalPositiveListener = positiveListener;
            btnOneOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalPositiveListener.onClick(dialogFinal, DialogInterface.BUTTON_POSITIVE);
                    dialogFinal.dismiss();
                }
            });
        } else {
            llButtonsRoot.setVisibility(View.VISIBLE);
            llButtonOneRoot.setVisibility(View.GONE);
            llButtonTwoRoot.setVisibility(View.VISIBLE);

            btnTwoCancel.setText(negativeText);
            btnTwoOk.setText(positiveText);
            final DialogInterface.OnClickListener finalNegativeListener = negativeListener;
            btnTwoCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalNegativeListener.onClick(dialogFinal, DialogInterface.BUTTON_NEGATIVE);
                    dialogFinal.dismiss();
                }
            });
            final DialogInterface.OnClickListener finalPositiveListener1 = positiveListener;
            btnTwoOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalPositiveListener1.onClick(dialogFinal, DialogInterface.BUTTON_POSITIVE);
                    dialogFinal.dismiss();
                }
            });
        }
        //endregion
        //设置背景透明,去四个角
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();
        dialog.setContentView(view);

        return dialog;
    }

    /**
     * 通用创建dialog
     *
     * @param context
     * @param title            弹框的标题
     * @param message          弹框的内容
     * @param messageImageId   弹窗消息，图片格式
     * @param messageView      弹窗消息，直接替换整个view
     * @param positiveListener 确定按钮的实例化监听
     * @param negativeListener 取消按钮的实例化监听
     * @param positiveText     确定按钮的文字显示
     * @param negativeText     取消按钮的文字显示
     * @param touchOutside     是否支持点击外部取消
     * @param cancelable       是否支持按返回取消
     * @return 返回对话框实例
     */
    public synchronized static AlertDialog showDialog(Context context,
                                                      String title,
                                                      CharSequence message,
                                                      int messageImageId,
                                                      View messageView,
                                                      String positiveText,
                                                      String negativeText,
                                                      DialogInterface.OnClickListener positiveListener,
                                                      DialogInterface.OnClickListener negativeListener,
                                                      boolean touchOutside,
                                                      boolean cancelable) {
        return showDialogIOS(context, title, message, messageImageId, messageView, positiveText, negativeText, positiveListener, negativeListener, touchOutside, cancelable);
    }

    /**
     * 通用创建dialog
     *
     * @param context
     * @param title            弹框的标题
     * @param message          弹框的内容
     * @param positiveText     确定按钮的文字显示
     * @param negativeText     取消按钮的文字显示
     * @param positiveListener 确定按钮的实例化监听
     * @param negativeListener 取消按钮的实例化监听
     * @param touchOutside     是否支持点击外部取消
     * @return 返回对话框实例
     */
    public synchronized static AlertDialog showDialog1(Context context, String title, CharSequence message,
                                                       String positiveText, String negativeText,
                                                       DialogInterface.OnClickListener positiveListener,
                                                       DialogInterface.OnClickListener negativeListener,
                                                       boolean touchOutside) {
        return showDialog(context, title, message, 0, null, positiveText, negativeText, positiveListener, negativeListener, touchOutside, true);
    }

    /**
     * 通用创建dialog
     *
     * @param context
     * @param title            弹框的标题
     * @param message          弹框的内容
     * @param positiveListener 确定按钮的实例化监听
     * @param negativeListener 取消按钮的实例化监听
     * @param touchOutside     是否支持点击外部取消
     * @return 返回对话框实例
     */
    public synchronized static AlertDialog showDialog2(Context context, String title, CharSequence message,
                                                       DialogInterface.OnClickListener positiveListener,
                                                       DialogInterface.OnClickListener negativeListener,
                                                       boolean touchOutside) {
        return showDialog1(context, title, message, "确定", "取消", positiveListener, negativeListener, touchOutside);
    }
}
