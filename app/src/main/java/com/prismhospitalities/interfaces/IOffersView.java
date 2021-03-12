package com.prismhospitalities.interfaces;

import com.prismhospitalities.models.responses.OffersResponse;

public interface IOffersView extends IBaseView {
    void getOffersResponseSuccess(OffersResponse offersResponse);

    void getOffersResponseFailed();
}
