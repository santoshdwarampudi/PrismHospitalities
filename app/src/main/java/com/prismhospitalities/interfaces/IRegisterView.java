package com.prismhospitalities.interfaces;

import com.prismhospitalities.models.responses.RegisterResponse;

public interface IRegisterView extends IActivityBaseView {
    void registerSuccess(RegisterResponse registerResponse);

    void registerFailure();

}
