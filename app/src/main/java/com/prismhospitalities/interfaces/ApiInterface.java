package com.prismhospitalities.interfaces;

import com.prismhospitalities.models.requests.LoginRequest;
import com.prismhospitalities.models.requests.RegisterRequest;
import com.prismhospitalities.models.responses.LoginResponse;
import com.prismhospitalities.models.responses.MenuItemsResponse;
import com.prismhospitalities.models.responses.MenuProductsResponse;
import com.prismhospitalities.models.responses.MenuTypesResponse;
import com.prismhospitalities.models.responses.OffersResponse;
import com.prismhospitalities.models.responses.RegisterResponse;
import com.prismhospitalities.models.responses.ScratchCardResponse;
import com.prismhospitalities.models.responses.UpdateScratchResponse;
import com.prismhospitalities.models.responses.UserDetailsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET(APIConstants.MENUTYPE)
    Call<MenuTypesResponse> getMenuTypes();

    @GET(APIConstants.MENUITEMS)
    Call<MenuItemsResponse> getMenuItemResponse(@Path("menuId") String menuId);

    @GET(APIConstants.MENUPRODUCTS)
    Call<MenuProductsResponse> getMenuProductResponse(@Path("menuId") String menuId);

    @GET(APIConstants.OFFERS)
    Call<OffersResponse> getOffers();

    @POST(APIConstants.REGISTRATION)
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST(APIConstants.LOGIN)
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @GET(APIConstants.USERDETAILS)
    Call<UserDetailsResponse> getUserDetails(@Path("userId") String userId);

    @GET(APIConstants.SCRATCHCARDS)
    Call<ScratchCardResponse> getScratchCards(@Path("userId") String userId);

    @GET(APIConstants.UPDATESCRATCHSTATUS)
    Call<UpdateScratchResponse> updateScratchCardResponse(@Path("scratchCardId") String scratchCardId);

}
