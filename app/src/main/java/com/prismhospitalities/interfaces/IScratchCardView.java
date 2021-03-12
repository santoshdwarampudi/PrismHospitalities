package com.prismhospitalities.interfaces;

import com.prismhospitalities.models.responses.ScratchCardResponse;
import com.prismhospitalities.models.responses.UpdateScratchResponse;

public interface IScratchCardView extends IActivityBaseView {
    void onScratchCardSuccess(ScratchCardResponse scratchCardResponse);

    void onScratchCardFailed();

    void onScratchUpdateSuccess(UpdateScratchResponse updateScratchResponse);

    void onScratchUpdateFailed();


}
