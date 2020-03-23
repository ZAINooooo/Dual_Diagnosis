package com.example.dual_diagnosis;

import android.content.Context;
import android.graphics.Typeface;

public class SmartDialogBuilder2 {

    private Typeface titleTf,subTitleTf;
    private boolean isCancelable;
    private String title,subtitle,okButtonLable,cancelButtonLable;
    private Context context;
    private SmartDialogClickListener2 okListener;
    private SmartDialogClickListener2 cancelListener;
    private boolean isNegativeBtnHide;

    public SmartDialogBuilder2(Context context) {this.context=context;}

    public SmartDialogBuilder2 setTitle(String title){
        this.title=title;
        return  this;
    }

    public SmartDialogBuilder2 setSubTitle(String subTitle){
        this.subtitle=subTitle;
        return this;
    }

    public SmartDialogBuilder2 setTitleFont(Typeface titleFont){
        this.titleTf=titleFont;
        return this;
    }

    public SmartDialogBuilder2 setSubTitleFont(Typeface subTFont){
        this.subTitleTf=subTFont;
        return this;
    }
    public SmartDialogBuilder2 setPositiveButton(String lable, SmartDialogClickListener2 listener){
        this.okListener=listener;
        this.okButtonLable=lable;
        return this;
    }

    public SmartDialogBuilder2 setNegativeButton(String lable, SmartDialogClickListener2 listener){
        this.cancelListener=listener;
        this.cancelButtonLable=lable;
        return this;
    }
    public SmartDialogBuilder2 setCancalable(boolean isCancelable){
        this.isCancelable=isCancelable;
        return this;
    }

    public SmartDialogBuilder2 setNegativeButtonHide(boolean isHide){
        this.isNegativeBtnHide=isHide;
        return this;
    }

    public SmartDialog2 build(){
        SmartDialog2 dialog = new SmartDialog2(context,title,subtitle, titleTf, subTitleTf,isCancelable,isNegativeBtnHide);
        dialog.setNegative(cancelButtonLable,cancelListener);
        dialog.setPositive(okButtonLable,okListener);
        return dialog;
    }
}
