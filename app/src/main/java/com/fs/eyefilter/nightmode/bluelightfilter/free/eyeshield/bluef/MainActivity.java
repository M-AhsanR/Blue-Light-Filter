package com.fs.eyefilter.nightmode.bluelightfilter.free.eyeshield.bluef;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static String name;
    private final int FLOAT_WINDOW_REQUEST_CODE = 1;
    ImageButton app;
    ImageButton normalMode;
    ImageButton readMode;
    ImageButton darkMode;
    ImageButton stress;
    ImageButton sleepMode;
    AdView mybottomAdd;
    int counter;
    private ActionBarDrawerToggle t;
    private SeekBar seekBar;
    private Intent intent;
    private boolean floatWindowPermission = false;
    private InterstitialAd mInterstitialAd;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();


        mybottomAdd = findViewById(R.id.adView_bottommenu);
        mybottomAdd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();


        Objects.requireNonNull(getSupportActionBar()).setTitle("Blue Light Filter");


        DrawerLayout dl = findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);


        dl.addDrawerListener(t);
        t.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        NavigationView nv = findViewById(R.id.nv);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {


                    case R.id.stop:

                        if (isAnyServiceRunning()){

                            stopAllServices();
                            Toast.makeText(MainActivity.this, "Filters Disabled", Toast.LENGTH_SHORT).show();
                        }

                        else {

                            Toast.makeText(MainActivity.this, "No Filter Enabled", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.share:
                        shareappNow();
                        break;

                    case R.id.moreapps:
                        Uri urim = Uri.parse("https://play.google.com/store/apps/developer?id=Funzy+Sole");
                        Intent fbackm = new Intent(Intent.ACTION_VIEW, urim);
                        startActivity(fbackm);
                        break;

                    case R.id.feedback:
                        Uri uris = Uri.parse("https://play.google.com/store/apps/details?id=com.fs.eyefilter.nightmode.bluelightfilter.free.eyeshield.bluef");
                        Intent fback = new Intent(Intent.ACTION_VIEW, uris);
                        startActivity(fback);
                        break;

                    case R.id.policy:
                        Intent privacy = new Intent(MainActivity.this, Policy.class);
                        startActivity(privacy);

                        break;

                    case R.id.quit:
                        if (mInterstitialAd.isLoaded()) {
                            counter = 4;
                            showInterstitial();


                        } else {

                            OpenExitDialog();

                        }

                        break;


                }

                return true;

            }
        });


        normalMode = findViewById(R.id.filterOne);
        readMode = findViewById(R.id.filterTwo);
        darkMode = findViewById(R.id.filterThree);
        stress = findViewById(R.id.filterFour);
        sleepMode = findViewById(R.id.filterFive);
        seekBar = findViewById(R.id.sb_change_filter_level);
        SeekBar brightnessBar = findViewById(R.id.sb_change_brightness_level);


        normalMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (mInterstitialAd.isLoaded()) {
                    counter = 1;
                    showInterstitial();


                } else {

                    Intent read = new Intent(getApplicationContext(), NormalActivity.class);
                    startActivity(read);

                }


            }
        });

        readMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent read = new Intent(getApplicationContext(), ReadActivity.class);
                startActivity(read);


            }
        });


        darkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    counter = 2;
                    showInterstitial();


                } else {

                    Intent read = new Intent(getApplicationContext(), DarkActivity.class);
                    startActivity(read);

                }


            }
        });


        stress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    counter = 3;
                    showInterstitial();


                } else {

                    Intent read = new Intent(getApplicationContext(), StressfreeActivity.class);
                    startActivity(read);

                }


            }
        });


        sleepMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sleep = new Intent(getApplicationContext(), SleepActivity.class);
                startActivity(sleep);


            }
        });





        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (floatWindowPermission) {
                    intent.putExtra("level", progress);
                    startService(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        brightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Context context = getApplicationContext();

                boolean canWriteSettings = Settings.System.canWrite(context);

                if (canWriteSettings) {


                    int screenBrightnessValue = i * 255 / 100;

                    Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

                    Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, screenBrightnessValue);
                } else {

                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });





    }


    private void loadInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);

    }


    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // mNextLevelButton.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                //  mNextLevelButton.setEnabled(true);
                goToNextLevel();
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                goToNextLevel();
            }
        });
        return interstitialAd;

    }


    private void showInterstitial() {


        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }


    }

    private void goToNextLevel() {

        if (counter == 1) {

            Intent intent = new Intent(getApplicationContext(), NormalActivity.class);
            startActivity(intent);
            finish();

        } else if (counter == 2) {

            Intent read = new Intent(getApplicationContext(), DarkActivity.class);
            startActivity(read);
            finish();

        } else if (counter == 3) {

            Intent day = new Intent(getApplicationContext(), StressfreeActivity.class);
            startActivity(day);
            finish();
        } else if (counter == 4) {

            OpenExitDialog();

        }


    }





    private void stopAllServices() {

        stopService(new Intent(getApplicationContext(), IntensityService.class));
        stopService(new Intent(getApplicationContext(), DarkService.class));
        stopService(new Intent(getApplicationContext(), NormalService.class));
        stopService(new Intent(getApplicationContext(), ReadingService.class));
        stopService(new Intent(getApplicationContext(), SleepService.class));
        stopService(new Intent(getApplicationContext(), StressfreeService.class));

    }

    private boolean isAnyServiceRunning() {

        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        assert manager != null;
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.fs.eyefilter.nightmode.bluelightfilter.free.eyeshield.bluef.NormalService".equals(service.service.getClassName())) {
                return true;
            } else if ("com.fs.eyefilter.nightmode.bluelightfilter.free.eyeshield.bluef.ReadingService".equals(service.service.getClassName())) {

                return true;

            } else if ("com.fs.eyefilter.nightmode.bluelightfilter.free.eyeshield.bluef.DarkService".equals(service.service.getClassName())) {
                return true;

            } else if ("com.fs.eyefilter.nightmode.bluelightfilter.free.eyeshield.bluef.StressfreeService".equals(service.service.getClassName())) {
                return true;

            } else if ("com.fs.eyefilter.nightmode.bluelightfilter.free.eyeshield.bluef.SleepService".equals(service.service.getClassName())) {
                return true;



            }


        }
        return false;
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(this)) {
                floatWindowPermission = true;
                startIntensityService();

            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, FLOAT_WINDOW_REQUEST_CODE);
            }
        } else {
            floatWindowPermission = true;
            startIntensityService();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FLOAT_WINDOW_REQUEST_CODE: {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (Settings.canDrawOverlays(this)) {
                        floatWindowPermission = true;
                        startIntensityService();
                        Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    } else {
                        floatWindowPermission = false;
                        Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
        }
    }


    private void startIntensityService() {
        intent = new Intent(this, IntensityService.class);
        startService(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void shareappNow() {
        String uri, shareBody;
        uri = "https://play.google.com/store/apps/details?id=com.fs.eyefilter.nightmode.bluelightfilter.free.eyeshield.bluef";
        shareBody = uri;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "BlueLight");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share Using"));
    }



    @Override
    public void onBackPressed() {
        if (mInterstitialAd.isLoaded()) {
            counter = 4;
            showInterstitial();


        } else {

            OpenExitDialog();

        }


    }

    private void OpenExitDialog() {


        android.support.v7.app.AlertDialog.Builder mDialog = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
        mDialog.setCancelable(false);
        mDialog.setTitle("Exit");
        mDialog.setMessage("Thankyou for using our App. Do you really want to quit?");
        mDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
                finish();
            }
        });
        mDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        mDialog.create().show();


    }

}


