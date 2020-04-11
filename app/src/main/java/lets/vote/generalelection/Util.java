package lets.vote.generalelection;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Util {

    public static void print(String msg) {
        Log.d("### ", msg);
    }
    public static void goNextPageWithAnimation(Fragment fragment, FragmentTransaction transaction) {
        transaction.addToBackStack(null);

        /// 애니메이션 1,2 -> 들어갈때
        /// 애니메이션 3,4 -> 나올때
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                R.anim.enter_from_left, R.anim.exit_to_right);

        transaction.replace(R.id.mainContainer, fragment);
        transaction.commit();
    }
}

