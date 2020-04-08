package lets.vote.generalelection;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class NetworkChecker {
    static NetworkRequest.Builder networkRequestBuilder;
    static ConnectivityManager manager;

    static public void setup( Context context){
        manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkRequestBuilder= new NetworkRequest.Builder();
        networkRequestBuilder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR);
        networkRequestBuilder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
    }
    static public void checkLiveOn(ConnectivityManager.NetworkCallback callback){
        manager.registerNetworkCallback(networkRequestBuilder.build(), callback);
    }
    static public void checkLiveOff(ConnectivityManager.NetworkCallback callback){
        manager.unregisterNetworkCallback(callback);
    }
    static public boolean checkOn(){
        return (manager.getNetworkInfo(NetworkCapabilities.TRANSPORT_CELLULAR).isConnected()
                || manager.getNetworkInfo(NetworkCapabilities.TRANSPORT_WIFI).isConnected());
    }

    static public AlertDialog.Builder alert(Context context, String buttonString, DialogInterface.OnClickListener listener){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("네트워크 연결 문제").setMessage("Wifi / 셀룰러 연결을 확인해주세요.");
        alertDialog.setNeutralButton(buttonString, listener);
        alertDialog.setCancelable(false);
        return alertDialog;
    }

}
