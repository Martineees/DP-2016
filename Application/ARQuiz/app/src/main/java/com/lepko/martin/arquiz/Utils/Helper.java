package com.lepko.martin.arquiz.Utils;

import android.app.ProgressDialog;
import android.net.wifi.ScanResult;
import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.AbstractMap;
import java.util.List;

/**
 * Created by Martin on 18.2.2017.
 */

public class Helper {
    public static String IP_ADDRESS = "10.0.2.2";

    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    public static void showProgressDialog(ProgressDialog pd, String msg) {
        if(pd.isShowing()) {
            pd.setMessage(msg);
        } else {
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage(msg);
            pd.setCancelable(false);
            pd.show();
        }
    }

    public static void dismissProgressDialog(ProgressDialog pd) {
        pd.dismiss();
    }

    public static void showToast(Context cnt, String msg, int duration) {
        Toast.makeText(cnt, msg, duration).show();
    }

    public static String getQuery(List<AbstractMap.SimpleEntry<String, String>> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (AbstractMap.SimpleEntry pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getKey().toString(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue().toString(), "UTF-8"));
        }

        return result.toString();
    }

    public static String scanResults2JSON(List<ScanResult> data) throws JSONException {

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObj;

        for(ScanResult sr : data) {
            jsonObj = new JSONObject();

            jsonObj.put("BSSID", sr.BSSID);
            jsonObj.put("SSID", sr.SSID);
            jsonObj.put("level", sr.level);

            jsonArray.put(jsonObj);
        }

        return jsonArray.toString();
    }

    public static JSONObject string2JSON(String jsonString) throws JSONException {
        return new JSONObject(jsonString);
    }
}
