package com.prismhospitalities.interfaces;

import com.prismhospitalities.models.responses.MenuItemsResponse;
import com.prismhospitalities.models.responses.MenuTypesResponse;

public interface IMenuView extends IBaseView {
    void getMenuTypesSuccess(MenuTypesResponse menuTypesResponse);

    void getMenuTypesFailed();

    void getMenuItemsSuccess(MenuItemsResponse menuItemsResponse);

    void getMenuItemsFailed();
}
