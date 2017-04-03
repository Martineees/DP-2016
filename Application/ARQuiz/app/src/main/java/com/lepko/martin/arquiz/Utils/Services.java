package com.lepko.martin.arquiz.Utils;

/**
 * Created by Martin on 19.2.2017.
 */

public class Services {

    private static final String SERVER_ADDRESS = "http://192.168.1.101/"; //home
    //private static final String SERVER_ADDRESS = "http://192.168.0.105/"; //brezno
    //private static final String SERVER_ADDRESS = "http://192.168.173.1/"; //hrachovo
    //private static final String SERVER_ADDRESS = "http://192.168.10.156/"; //work
    //private static final String SERVER_ADDRESS = "http://192.168.0.129/"; //school

    private static final String FIND_LOCATION_SERVICE = "services/find_location.php";
    private static final String LOGIN_SERVICE = "services/login.php";
    private static final String REGISTRATION_SERVICE = "services/registration.php";
    private static final String COMPETITIONS_SERVICE = "services/get_competitions.php";
    private static final String QUESTIONS_SERVICE = "services/get_questions.php";
    private static final String ENTER_COMPETITION_SERVICE = "services/enter_competition.php";
    private static final String LOG_ANSWER_SERVICE = "services/log_answer.php";
    private static final String CHART_SERVICE = "services/get_chart.php";

    private static final String GET_TARGET_IMAGE_SERVICE = "services/get_target_image.php";

    public static final String METHOD_POST = "POST";

   //scanResults=[{%22BSSID%22:%2200:08:30:b6:26:f0%22,%20%22level%22:%22-71%22}]

    public static String LOCATION_URL() {
        return SERVER_ADDRESS + FIND_LOCATION_SERVICE;
    }
    public static String LOGIN_URL() {
        return SERVER_ADDRESS + LOGIN_SERVICE;
    }
    public static String REGISTRATION_URL() {
        return SERVER_ADDRESS + REGISTRATION_SERVICE;
    }
    public static String COMPETITIONS_URL() {
        return SERVER_ADDRESS + COMPETITIONS_SERVICE;
    }
    public static String QUESTIONS_URL() {
        return SERVER_ADDRESS + QUESTIONS_SERVICE;
    }
    public static String ENTER_COMPETITION_URL() { return SERVER_ADDRESS + ENTER_COMPETITION_SERVICE; }
    public static String LOG_ANSWER_URL() { return SERVER_ADDRESS + LOG_ANSWER_SERVICE; }
    public static String CHART_URL() { return SERVER_ADDRESS + CHART_SERVICE; }

    public static String TARGET_IMAGE_URL() {
        return SERVER_ADDRESS + GET_TARGET_IMAGE_SERVICE;
    }
}
