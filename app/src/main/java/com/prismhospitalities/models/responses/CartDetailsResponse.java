package com.prismhospitalities.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CartDetailsResponse implements Serializable {
    @SerializedName("cartdetails")
    @Expose
    private List<MenuProductsResponseData> cartdetails = null;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<MenuProductsResponseData> getCartdetails() {
        return cartdetails;
    }

    public void setCartdetails(List<MenuProductsResponseData> cartdetails) {
        this.cartdetails = cartdetails;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

}
