package org.upv.movie.list.netflix.activity;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

/**
 * Created by jvg63 on 10/12/2017.
 */

public class MainActivityApplication extends Application{
    public static String ADMOD_ID_APP="ca-app-pub-5015252111145883~6697388107";
    public static String ADMOD_ID_BANNER="ca-app-pub-5015252111145883/7807946333";
    public static String ADMOD_ID_INTERSTICIAL="ca-app-pub-5015252111145883/8761935357";
    public static String ADMOD_ID_BONIFICADO="ca-app-pub-5015252111145883/6215663066";
    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(this,ADMOD_ID_APP);
    }
}
