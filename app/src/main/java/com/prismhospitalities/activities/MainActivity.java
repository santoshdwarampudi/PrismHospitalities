package com.prismhospitalities.activities;


import android.os.Bundle;

import com.prismhospitalities.R;
import com.prismhospitalities.baseui.BaseAppCompactActivity;

public class MainActivity extends BaseAppCompactActivity {

    @Override
    public int getActivityLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
