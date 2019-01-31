package com.android.freak.appupdateutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.freak.appupdateutils.event.RxEvent;
import com.freak.mvphttphelper.net.RxBus;

public class RxBusActivity extends Activity {
    public static void startAction(Context context) {
        Intent intent = new Intent(context, RxBusActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);
    }

    public void rxBus(View view) {
        RxBus.getDefault().post(new RxEvent("RxFreak", "Rx123456", 1000));
        finish();
    }
}
