package com.fs.eyefilter.nightmode.bluelightfilter.free.eyeshield.bluef;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class NormalActivity extends Activity {
    RadioButton btnOff;
    RadioButton btnOn;
    RadioGroup toggle;
    AdView mybottomAddNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);


        toggle = findViewById(R.id.toggle);

        btnOff = findViewById(R.id.off);
        btnOn = findViewById(R.id.on);


        mybottomAddNormal = findViewById(R.id.adView_bottommenuNormal);
        mybottomAddNormal.loadAd(new AdRequest.Builder().build());

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        btnOn.setChecked(sharedPreferences.getBoolean("NameOfThingToSave", false));


        toggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {


                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("NameOfThingToSave", btnOn.isChecked());


                editor.apply();


                switch (i) {

                    case R.id.on:
                        stopAllServices();
                        Intent normal = new Intent(getApplicationContext(), NormalService.class);
                        startService(normal);

                        break;

                    case R.id.off:

                        stopService(new Intent(getApplicationContext(), NormalService.class));
                        break;

                }


            }
        });


    }

    private void stopAllServices() {

        stopService(new Intent(getApplicationContext(), IntensityService.class));
        stopService(new Intent(getApplicationContext(), DarkService.class));
        stopService(new Intent(getApplicationContext(), ReadingService.class));
        stopService(new Intent(getApplicationContext(), SleepService.class));
        stopService(new Intent(getApplicationContext(), StressfreeService.class));


    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(back);
    }
}
