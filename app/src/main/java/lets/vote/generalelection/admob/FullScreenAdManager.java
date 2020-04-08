package lets.vote.generalelection.admob;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import static lets.vote.generalelection.BuildConfig.FULL_SCREEN_AD_ID;

public class FullScreenAdManager {

    private InterstitialAd mInterstitialAd;
    private int count = 0;

    AdListener adListener;
    private static FullScreenAdManager instance = null;
    public static FullScreenAdManager getInstance(Context context) {
        if (instance == null) {
            instance = new FullScreenAdManager(context);
        }
        return instance;
    }

    FullScreenAdManager(Context context) {
        if (mInterstitialAd == null) {
            mInterstitialAd = new InterstitialAd(context);
        }
        mInterstitialAd.setAdUnitId(FULL_SCREEN_AD_ID);
    }

    public void initialize(Context context, AdListener adListener) {

        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(adListener);
        this.adListener = adListener;
    }

    public void showAd(Context context) {
        if (mInterstitialAd.isLoaded()) {

            if (count == 0) {
                mInterstitialAd.show();

            }
            count = count == 5 ? 0 : count+1;
        } else {

            if (count == 5) {
                initialize(context, adListener);
                count = 0;
                return;
            }
            count += 1;
            Log.d("FULL_SCREEN_AD", "The interstitial wasn't loaded yet.");
        }
    }
}
