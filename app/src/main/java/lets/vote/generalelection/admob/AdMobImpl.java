package lets.vote.generalelection.admob;

import android.content.Context;

import com.google.android.gms.ads.AdListener;

public interface AdMobImpl {

    void initialize(Context context, AdListener adListener);
    void showAd(Context context);
}
