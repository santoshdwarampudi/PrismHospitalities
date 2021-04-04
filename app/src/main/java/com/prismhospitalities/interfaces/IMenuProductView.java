package com.prismhospitalities.interfaces;

import com.prismhospitalities.models.responses.AddCartResponse;
import com.prismhospitalities.models.responses.MenuProductsResponse;

public interface IMenuProductView extends IBaseView {

    void getMenuProductsSuccess(MenuProductsResponse menuProductsResponse);

    void getMenuProductsFailed();

    void addToCartSuccess(AddCartResponse addCartResponse, int position);

    void addToCartFailed();

}
