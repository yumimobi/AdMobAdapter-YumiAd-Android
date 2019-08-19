package com.yumiad.admobsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    static final String INTERSTITIAL_ID = "ca-app-pub-5451364651863658/8161509597";
    static final String BANNER_ID = "ca-app-pub-5451364651863658/3431927093";
    static final String VIDEO_ID = "ca-app-pub-5451364651863658/4170293692";
    private static final String APP_ID = "ca-app-pub-5451364651863658~6441233812";

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
