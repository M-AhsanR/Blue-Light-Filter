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

public class ReadActivity extends Activity {

    AdView mybottomAddRead;
    private RadioButton btnOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        mybottomAddRead = findViewById(R.id.adView_bottommenuRead);
        mybottomAddRead.loadAd(new AdRequest.Builder().build());

        RadioGroup toggle = findViewById(R.id.toggleRead);
        btnOn = findViewById(R.id.onRead);

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        btnOn.setChecked(sharedPreferences.getBoolean("NameOfThingToSaveR", false));

        toggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("NameOfThingToSaveR", btnOn.isChecked());

                editor.apply();

                switch (i) {

                    case R.id.onRead:
                        stopAllServices();
                        Intent normal = new Intent(getApplicationContext(), ReadingService.class);
                        startService(normal);

                        break;

                    case R.id.offRead:

                        stopService(new Intent(getApplicationContext(), ReadingService.class));
                        break;

                }


            }
        });

    }

    private void stopAllServices() {

        stopService(new Intent(getApplicationContext(), IntensityService.class));
        stopService(new Intent(getApplicationContext(), DarkService.class));
        stopService(new Intent(getApplicationContext(), NormalService.class));
        stopService(new Intent(getApplicationContext(), SleepService.class));
        stopService(new Intent(getApplicationContext(), StressfreeService.class));


    }


}
