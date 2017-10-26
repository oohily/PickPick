package com.sandemarine.hsddialog;

import java.util.List;

/**
 * Created by Zhongsz on 2017/8/9.
 */

public class HSDDialogParams {
    public int dialogStyle;
    public int dialogPosition;
    public int animation;
    public String title;
    public String text;
    public int src;
    public HSDDialogListListener listener;
    public HSDDialogListListener positiveListener;
    public HSDDialogListListener negativeListener;
    public String positive;
    public String negative;
    public List<String> list;
    public boolean listCancel;
}
