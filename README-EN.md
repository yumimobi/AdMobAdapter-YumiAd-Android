- [1 integral YumiAd SDK and AdMob SDK](#1-integral-yumiad-sdk-and-admob-sdk)
  - [1.1 add YumiAd SDK dependency](#11-add-yumiad-sdk-dependency)
  - [1.2 add AdMob SDK dependency](#12-add-admob-sdk-dependency)
  - [1.3 add Adapter into project](#13-add-adapter-into-project)
- [2 add YumiAd source into AdMob platform](#2-add-yumiad-source-into-admob-platform)
- [3 Testing ID](#3-testing-ID)

## 1 integral YumiAd SDK and AdMob SDK
### 1.1 add YumiAd SDK dependency
add dependency into app's build.gradle
```groovy
dependencies {
    implementation 'com.yumimobi.ads:yumiad:1.1.1'
}
```
### 1.2 add AdMob SDK dependency
add dependency into app's build.gradle
```groovy
dependencies {
    implementation 'com.google.android.gms:play-services-ads:17.2.0'
}
```

### 1.3 add Adapter into project

you can import [admobadapter](./admobadapter) module into your project, or just copy those files into your project.

1. [YumiAdBanner.java](./admobadapter/src/main/java/com/yumiad/admobadapter/YumiAdBanner.java) Banner Adapter

2. [YumiAdInterstitial.java](./admobadapter/src/main/java/com/yumiad/admobadapter/YumiAdInterstitial.java) Interstitial Adapter

3. [YumiAdRewardedVideo.java](./admobadapter/src/main/java/com/yumiad/admobadapter/YumiAdRewardedVideo.java) RewardedVideo Adapter

4. [YumiAdUtil.java](./admobadapter/src/main/java/com/yumiad/admobadapter/YumiAdUtil.java) The util class, which contains some common methods, such as parsing the json issued by AdMob, or converting the error code.

## 2 add YumiAd source into [AdMob platform](https://apps.admob.com/v2/home)
The following example is to configure Yumi source on an existing application (AdmobAdapter-YumiAd) with an existing ad slot (Yumi Banner). For reference only, if the actual situation does not match this, please handle it as appropriate.
1. open "[Mediation](https://apps.admob.com/v2/mediation/groups/list)" then click "CREATE MEDIATION GROUP"

![image](imgs/new-mediation-group-01.png)

2. Choose the advertising format and platform you want to use. YumiAd currently supports Banner, Interstitial and Rewarded Video. Here is Bnner as an example. Click "CONTINUE" to go to the next step.

![image](imgs/new-mediation-group-02.png)

3. Enter Name, other settings remain the default, or refer to the settings below. Click "ADD AD UNIT" to select the ad slot you want to add.

![image](imgs/new-mediation-group-03.png)

4. In the Select ad Units dialog, select the ad unit and click "DONE" to save.

![image](imgs/new-mediation-group-04.png)

5. Click "ADD CUSTOM EVENT" to add a custom ad source.

![image](imgs/new-mediation-group-05.png)

6. Enter the name of the third-party ad source, here is Yumi source as an example, you can customize it according to your needs, and set the price of the third-party ad source as needed.

![image](imgs/new-mediation-group-06.png)

7. Configure the Yumi source. Fill in the full adapter class name in the Class Name, and take the adapter class name in the demo as an example.

Banner's adapter is `com.yumiad.admobadapter.YumiAdBanner`

Rewarded Video's adapter is `com.yumiad.admobadapter.YumiAdRewardedVideo`

Interstitial's adapter is `com.yumiad.admobadapter.YumiAdInterstitial`

You need fill in the slotId into the Parameter box, click "DONE" to complete the configuration of YumiAd
```json
{"slotId":"yourSlotId"}
```

In the test phase, you can replace **yourSlotId** with **test id** and change to your own slotId when your app goes online.

![image](imgs/new-mediation-group-07.png)

1. In the Ad source list, you can see the set ad source Yumi source, click "SAVE" to complete the Mediation configuration.

![image](imgs/new-mediation-group-08.png)

## 3 Testing ID

You can use the following ID to test in the test phase, the test ID will not generate revenue, please use your own slotId when the application goes online.

|    ad type     | slot id  |
| :------------: | :------- |
| Rewarded Video | ew9hyvl4 |
|  Interstitial  | 56ubk22h |
|     Banner     | uz852t89 |