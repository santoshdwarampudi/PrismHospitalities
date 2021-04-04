package com.prismhospitalities.presenters;

import com.prismhospitalities.interfaces.ApiInterface;
import com.prismhospitalities.interfaces.IMenuProductView;
import com.prismhospitalities.models.responses.AddCartResponse;
import com.prismhospitalities.models.responses.MenuProductsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuProductPresenter {
    private IMenuProductView iMenuView;
    private ApiInterface apiInterface;

    public MenuProductPresenter(IMenuProductView iMenuView, ApiInterface apiInterface) {
        this.iMenuView = iMenuView;
        this.apiInterface = apiInterface;
    }


    public void getMenuProducts(final boolean showprogress, String menuId) {
        if (showprogress)
            iMenuView.showProgressDialog("Getting Products");
        Call<MenuProductsResponse> menuProductsResponseCall = apiInterface.getMenuProductResponse(menuId);
        menuProductsResponseCall.enqueue(new Callback<MenuProductsResponse>() {
            @Override
            public void onResponse(Call<MenuProductsResponse> call, Response<MenuProductsResponse> response) {
                if (showprogress)
                    iMenuView.dismissProgress();
                iMenuView.getMenuProductsSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MenuProductsResponse> call, Throwable t) {
                if (showprogress)
                    iMenuView.dismissProgress();
                iMenuView.getMenuProductsFailed();
            }
        });
    }

    public void addOrRemoveFromCart(final boolean showprogress,
                                    String productId, String userId, String quantity, int position) {
        if (showprogress)
            iMenuView.showProgressDialog("Updating Cart");
        Call<AddCartResponse> addCartResponseCall = apiInterface.addToCart(productId, userId, quantity);
        addCartResponseCall.enqueue(new Callback<AddCartResponse>() {
            @Override
            public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {
                if (showprogress)
                    iMenuView.dismissProgress();
                iMenuView.addToCartSuccess(response.body(), position);
            }

            @Override
            public void onFailure(Call<AddCartResponse> call, Throwable t) {
                if (showprogress)
                    iMenuView.dismissProgress();
                iMenuView.addToCartFailed();
            }
        });
    }

}
