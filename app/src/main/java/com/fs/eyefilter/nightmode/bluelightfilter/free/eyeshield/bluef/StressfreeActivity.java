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

public class StressfreeActivity extends Activity {


    AdView mybottomAddStress;
    private RadioButton btnOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stressfree);

        mybottomAddStress = findViewById(R.id.adView_bottommenuStress);
        mybottomAddStress.loadAd(new AdRequest.Builder().build());

        RadioGroup toggle = findViewById(R.id.toggleStress);
        btnOn = findViewById(R.id.onStress);

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        btnOn.setChecked(sharedPreferences.getBoolean("NameOfThingToSaveSt", false));

        toggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("NameOfThingToSaveSt", btnOn.isChecked());


                editor.apply();
                switch (i) {

                    case R.id.onStress:
                        stopAllServices();
                        Intent normal = new Intent(getApplicationContext(), StressfreeService.class);
                        startService(normal);

                        break;

                    case R.id.offStress:

                        stopService(new Intent(getApplicationContext(), StressfreeService.class));
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
        stopService(new Intent(getApplicationContext(), NormalService.class));


    }


    @Override
    public void onBackPressed() {
        Intent back = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(back);
    }
}
