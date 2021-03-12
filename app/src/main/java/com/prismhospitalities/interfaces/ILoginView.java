package com.prismhospitalities.interfaces;

import com.prismhospitalities.models.responses.LoginResponse;

public interface ILoginView extends IActivityBaseView {
    void onLoginSuccess(LoginResponse loginResponse);

    void onLoginFailed();
}
