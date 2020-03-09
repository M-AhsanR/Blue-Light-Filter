package com.fs.eyefilter.nightmode.bluelightfilter.free.eyeshield.bluef;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

public class SplashActivity extends Activity {
    private com.google.android.gms.ads.InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        try {
            mInterstitialAd = newInterstitialAd();
            loadInterstitial();
            long interval = 1000;
            long startTime = 6 * 1000;

            CountDownTimer countDownTimer = new MyCountDownTimer(startTime, interval);
            countDownTimer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private com.google.android.gms.ads.InterstitialAd newInterstitialAd() {
        com.google.android.gms.ads.InterstitialAd interstitialAd = new com.google.android.gms.ads.InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_after_splash));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

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
        } else {
            goToNextLevel();
        }
    }

    private void loadInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }


    private void goToNextLevel() {
        Intent send = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(send);
        finish();
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
    }


    private class MyCountDownTimer extends CountDownTimer {

        MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            showInterstitial();
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }

    }

}
