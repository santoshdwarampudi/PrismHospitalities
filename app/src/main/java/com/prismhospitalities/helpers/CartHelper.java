package com.prismhospitalities.helpers;


import android.content.Context;

import java.util.HashMap;


public class CartHelper {
    private static CartHelper cartHelper;
    private Context context;
    private java.util.HashMap<String, String> cartList = new HashMap<String, String>();

    public static CartHelper getInstance() {
        if (cartHelper == null) {
            synchronized (CartHelper.class) {
                cartHelper = new CartHelper();
            }
        }
        return cartHelper;
    }


    public HashMap<String, String> getCartList() {
        return cartList;
    }

    public void setCartList(HashMap<String, String> cartList) {
        this.cartList = cartList;
    }
}
