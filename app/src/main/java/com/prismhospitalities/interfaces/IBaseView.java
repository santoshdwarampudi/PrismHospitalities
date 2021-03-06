package com.prismhospitalities.interfaces;

import android.content.Context;

public interface IBaseView {
    void showProgressDialog(String msg);
    void dismissProgress();
    void showToast(String msg);
    Context getContext();
    boolean isUsable();
    boolean isProgressVisible();
}