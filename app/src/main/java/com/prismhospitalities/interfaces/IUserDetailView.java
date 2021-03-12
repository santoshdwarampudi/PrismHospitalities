package com.prismhospitalities.interfaces;

import com.prismhospitalities.models.responses.UserDetailsResponse;

public interface IUserDetailView extends IActivityBaseView {
    void onUserDetailsSuccess(UserDetailsResponse userDetailsResponse);

    void onUserDetailsFailed();
}
