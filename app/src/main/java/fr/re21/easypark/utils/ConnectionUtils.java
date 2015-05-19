package fr.re21.easypark.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by maxime on 19/05/15.
 */
public class ConnectionUtils {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String dataGet(String url, Context context){
        if(isOnline(context)==true) {
            HttpUriRequest request = new HttpGet(url);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = null;
            //requet
            try {
                response = httpclient.execute(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String result = null;
            //result
            try {
                result = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //if error return null
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                result = null;
            }
            return result;
        } else { return null;}
    }

}