package com.prismhospitalities.interfaces;

import com.prismhospitalities.models.responses.MenuProductsResponse;

public interface IMenuProductView extends IBaseView {

    void getMenuProductsSuccess(MenuProductsResponse menuProductsResponse);

    void getMenuProductsFailed();

}
