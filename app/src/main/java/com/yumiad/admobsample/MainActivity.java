package com.yumiad.admobsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    static final String INTERSTITIAL_ID = "ca-app-pub-1050908092086969/1253465593";
    static final String BANNER_ID = "ca-app-pub-1050908092086969/5413391823";
    static final String VIDEO_ID = "ca-app-pub-1050908092086969/2473718931";
    private static final String APP_ID = "ca-app-pub-1050908092086969~3113342178";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, APP_ID);
    }

    public void launchBanner(View view) {
        Intent i = new Intent(this, BannerActivity.class);
        startActivity(i);
    }

    public void launchInterstitial(View view) {
        Intent i = new Intent(this, InterstitialActivity.class);
        startActivity(i);
    }

    public void launchRewardedVideo(View view) {
        Intent i = new Intent(this, RewardedVideoActivity.class);
        startActivity(i);
    }
}
