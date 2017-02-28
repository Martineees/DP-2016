package com.lepko.martin.arquiz.Utils;

/**
 * Created by Martin on 19.2.2017.
 */

public class Services {

    //private static final String SERVER_ADDRESS = "http://192.168.1.101/";
    private static final String SERVER_ADDRESS = "http://192.168.0.105/";
    //private static final String SERVER_ADDRESS = "http://192.168.173.1/";

    private static final String FIND_LOCATION_SERVICE = "find_location.php";
    private static final String LOGIN_SERVICE = "login.php";

    public static final String METHOD_POST = "POST";

   //scanResults=[{%22BSSID%22:%2200:08:30:b6:26:f0%22,%20%22level%22:%22-71%22}]

    public static String LOCATION_URL() {
        return SERVER_ADDRESS + FIND_LOCATION_SERVICE;
    }
    public static String LOGIN_URL() {
        return SERVER_ADDRESS + LOGIN_SERVICE;
    }
}
