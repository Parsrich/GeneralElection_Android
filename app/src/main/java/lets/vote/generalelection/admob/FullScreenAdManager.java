package lets.vote.generalelection.admob;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import static lets.vote.generalelection.BuildConfig.FULL_SCREEN_AD_ID;

public class FullScreenAdManager implements AdMobImpl {

    private InterstitialAd mInterstitialAd;

    private static FullScreenAdManager instance = null;
    public static FullScreenAdManager getInstance(Context context) {
        if (instance == null) {
            instance = new FullScreenAdManager(context);
        }
        return instance;
    }

    FullScreenAdManager(Context context) {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(FULL_SCREEN_AD_ID);
    }

    @Override
    public void initialize(Context context, AdListener adListener) {

        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(adListener);
    }

    @Override
    public void showAd(Context context) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("FULL_SCREEN_AD", "The interstitial wasn't loaded yet.");
        }
    }

}
