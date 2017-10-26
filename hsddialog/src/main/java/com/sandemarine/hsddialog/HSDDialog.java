package com.sandemarine.hsddialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhongsz on 2017/8/9.
 */

public class HSDDialog extends DialogFragment {
    private static final String TAG = "CustomDialog";
    private View inflate;
    private Dialog dialog;
    private Window dialogWindow;
    ImageView imageView;
    TextView textText;
    TextView textTitle;
    private static HSDDialogParams dialogParams;
    public static final int DIALOG_POSITION_TOP = 1;
    public static final int DIALOG_POSITION_BOTTOM = 2;
    public static final int DIALOG_POSITION_CENTER = 3;
    public static final int DIALOG_STYLE_HINT = 1;
    public static final int DIALOG_STYLE_ALARM = 2;
    public static final int DIALOG_STYLE_LIST = 3;
    public static final int DIALOG_STYLE_ALERT = 4;

    public static HSDDialog create(HSDDialogParams params) {
        dialogParams = params;
        return new HSDDialog();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        dialogParams = null;
        if (dialog != null) {
            dialog.cancel();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        switch (dialogParams.dialogStyle) {
            case DIALOG_STYLE_ALARM:
                dialog = new Dialog(getContext(), R.style.DialogStyleDim);
                dialogWindow = dialog.getWindow();
                inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_alarm, null);
                imageView = (ImageView) inflate.findViewById(R.id.dialog_alarm_src);
                textText = (TextView) inflate.findViewById(R.id.dialog_alarm_text);
                //set icon
                if (dialogParams.src != 0) {
                    imageView.setImageResource(dialogParams.src);
                }
                //set text
                if (dialogParams.text != null) {
                    textText.setText(dialogParams.text);
                } else {
                    textText.setVisibility(View.GONE);
                }
                dialog.setContentView(inflate);
                break;
            case DIALOG_STYLE_HINT:
                dialog = new Dialog(getContext(), R.style.DialogStyleNormal);
                dialogWindow = dialog.getWindow();
                dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_hint, null);
                RelativeLayout layout = (RelativeLayout) inflate.findViewById(R.id.dialog_hint);
                if (dialogParams.listener != null) {
                    layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogParams.listener.onClick(-1);
                            dismiss();
                        }
                    });
                }
                imageView = (ImageView) inflate.findViewById(R.id.dialog_hint_icon);
                textTitle = (TextView) inflate.findViewById(R.id.dialog_hint_title);
                textText = (TextView) inflate.findViewById(R.id.dialog_hint_text);
                //set icon
                if (dialogParams.src != 0) {
                    imageView.setImageResource(dialogParams.src);
                }
                //set title
                if (dialogParams.title != null) {
                    textTitle.setText(dialogParams.title);
                } else {
                    throw new IllegalArgumentException("this dialog style needs a title at least");
                }
                //set text
                if (dialogParams.text != null) {
                    textText.setText(dialogParams.text);
                } else {
                    textText.setVisibility(View.GONE);
                }
                dialog.setContentView(inflate);
                break;
            case DIALOG_STYLE_LIST:
                dialog = new Dialog(getContext(), R.style.DialogStyleDim);
                dialogWindow = dialog.getWindow();
                inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_list, null);
                ListView listView = (ListView) inflate.findViewById(R.id.dialog_list);
                Button cancel = (Button) inflate.findViewById(R.id.dialog_list_cancel);
                DialogListAdapter adapter;
                if (dialogParams.list.size() != 0) {
                    adapter = new DialogListAdapter(getContext(), dialogParams.list, new HSDDialogListListener() {
                        @Override
                        public void onClick(int position) {
                            dialogParams.listener.onClick(position);
                            dismiss();
                        }
                    });
                } else {
                    throw new IllegalArgumentException("you must add a item list to the list dialog");
                }
                if (dialogParams.listCancel) {
                    cancel.setVisibility(View.VISIBLE);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dismiss();
                        }
                    });
                }
                listView.setAdapter(adapter);
                dialog.setContentView(inflate);
                break;
            case DIALOG_STYLE_ALERT:
                dialog = new Dialog(getContext(), R.style.DialogStyleDim);
                dialogWindow = dialog.getWindow();
                inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_alert, null);
                textTitle = (TextView) inflate.findViewById(R.id.alert_title);
                textText = (TextView) inflate.findViewById(R.id.alert_text);
                Button positive = (Button) inflate.findViewById(R.id.alert_positive);
                if (dialogParams.positive == null) {
                    positive.setVisibility(View.GONE);
                } else {
                    positive.setText(dialogParams.positive);
                    positive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            if (dialogParams.positiveListener != null)
                                dialogParams.positiveListener.onClick(-1);
                        }
                    });
                }
                Button negative = (Button) inflate.findViewById(R.id.alert_negative);
                if (dialogParams.negative != null) {
                    negative.setText(dialogParams.negative);
                } else {
                    negative.setText("Cancel");
                }
                negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        if (dialogParams.negativeListener != null)
                            dialogParams.negativeListener.onClick(-1);
                    }
                });
                if (dialogParams.title != null) {
                    textTitle.setText(dialogParams.title);
                } else {
                    textTitle.setVisibility(View.GONE);
                }
                if (dialogParams.text != null) {
                    textText.setText(dialogParams.text);
                } else {
                    throw new IllegalArgumentException("you must set the content text while using alert dialog");
                }
                dialog.setContentView(inflate);
                break;
        }
        switch (dialogParams.dialogPosition) {
            case DIALOG_POSITION_TOP:
                dialogWindow.setGravity(Gravity.TOP);
                break;
            case DIALOG_POSITION_BOTTOM:
                dialogWindow.setGravity(Gravity.BOTTOM);
                break;
            case DIALOG_POSITION_CENTER:
                dialogWindow.setGravity(Gravity.CENTER);
                break;
        }
        dialogWindow.setWindowAnimations(dialogParams.animation);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;
        dialogWindow.setAttributes(lp);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }


    public static class Builder {
        HSDDialogParams P;

        public Builder() {
            P = new HSDDialogParams();
            P.src = 0;
            P.text = null;
            P.title = null;
            P.listener = null;
            P.list = new ArrayList<>();
            P.listCancel = false;
            P.positive = null;
            P.negative = null;
        }

        //设置dialog的style
        public Builder setStyle(int dialogStyle) {
            P.dialogStyle = dialogStyle;
            return this;
        }

        //设置dialog出现的位置
        public Builder setPosition(int position) {
            P.dialogPosition = position;
            return this;
        }

        //设置dialog出入场动画style
        public Builder setAnim(int anim) {
            P.animation = anim;
            return this;
        }

        //设置dialog的图标（如果存在）
        public Builder setSrc(int src) {
            P.src = src;
            return this;
        }

        //设置dialog的标题
        public Builder setTitle(String title) {
            P.title = title;
            return this;
        }

        //设置dialog的描述
        public Builder setText(String text) {
            P.text = text;
            return this;
        }

        public Builder setListener(HSDDialogListListener listener) {
            P.listener = listener;
            return this;
        }

        public Builder setItemList(List<String> list) {
            P.list.addAll(list);
            return this;
        }

        public Builder setListCancel(boolean cancel) {
            P.listCancel = cancel;
            return this;
        }

        public Builder setPositiveButtonListener(String s, HSDDialogListListener l) {
            P.positive = s;
            P.positiveListener = l;
            return this;
        }

        public Builder setNegativeButtonListener(String s, HSDDialogListListener l) {
            P.negative = s;
            P.negativeListener = l;
            return this;
        }

        public HSDDialog create() {
            return HSDDialog.create(P);
        }
    }

}
