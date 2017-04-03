package com.lepko.martin.arquiz.Utils;

import android.app.ProgressDialog;
import android.net.wifi.ScanResult;
import android.content.Context;
import android.widget.Toast;

import com.lepko.martin.arquiz.Entities.Answer;
import com.lepko.martin.arquiz.Entities.ChartEntity;
import com.lepko.martin.arquiz.Entities.Competition;
import com.lepko.martin.arquiz.Entities.Competitor;
import com.lepko.martin.arquiz.Entities.Location;
import com.lepko.martin.arquiz.Entities.Question;
import com.lepko.martin.arquiz.Entities.User;

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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Martin on 18.2.2017.
 */

public class Helper {

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

    public static List<Competition> parseCompetitionsJSON(JSONArray jsonArray) throws JSONException {
        List<Competition> competitions = new LinkedList<>();

        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Competition competition = new Competition(obj.getInt("id"), obj.getString("name"),
                    obj.getInt("owner"), obj.getString("created"), obj.getString("description"));

            competitions.add(competition);
        }

        return competitions;
    }

    public static List<Question> parseQuestionsJSON(JSONArray jsonArray) throws JSONException {
        List<Question> questions = new LinkedList<>();

        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            int questionId = obj.getInt("id");
            List<Answer> answers = parseAnswersJSON(new JSONArray(obj.getString("answers")));
            Collections.shuffle(answers);

            Location location = json2Location(new JSONObject(obj.getString("location")));

            Question question = new Question(questionId, obj.getString("name"),
                    obj.getString("targetId"), location, obj.getInt("type"),
                    obj.getInt("score"), answers);

            questions.add(question);
        }

        return questions;
    }

    private static List<Location> parseLocationJSON(JSONArray jsonArray) throws JSONException {
        List<Location> locations = new LinkedList<>();

        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Location location = json2Location(obj);

            locations.add(location);
        }

        return locations;
    }

    private static List<Answer> parseAnswersJSON(JSONArray jsonArray) throws JSONException {
        List<Answer> answers = new LinkedList<>();

        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Answer answer = new Answer(obj.getInt("id"), obj.getString("name"),
                    obj.getInt("isCorrect") == 1);

            answers.add(answer);
        }

        return answers;
    }

    public static List<ChartEntity> parseChartJSON(JSONArray jsonArray) throws JSONException {
        List<ChartEntity> entities = new LinkedList<>();

        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            ChartEntity entity = new ChartEntity(obj.getInt("competitor_id"), i + 1, obj.getString("name"), obj.getInt("score"));

            entities.add(entity);
        }

        return entities;
    }

    public static Location json2Location(JSONObject obj) throws JSONException {
        return new Location(obj.getInt("id"), obj.getString("block").charAt(0), obj.getInt("floor"));
    }

    public static JSONObject userToJSON(User user) throws JSONException {

        JSONObject obj = new JSONObject();

        //user = new User(userObj.getString("user"), userObj.getInt("user_id"), userObj.getInt("is_admin") == 1, competitorList);

        obj.put("user", user.getName());
        obj.put("user_id", user.getId());
        obj.put("is_admin", user.isAdmin() ? 1 : 0);

        JSONArray objArray = new JSONArray();
        for(Competitor competitor : user.getCompetitors()) {
            JSONObject subObj = new JSONObject();

            subObj.put("id", competitor.getId());
            subObj.put("competition_id", competitor.getCompetitionId());

            JSONArray subObjArray = new JSONArray();
            for(Integer questionId : competitor.getAnsweredQuestions()) {
                JSONObject subSubObj = new JSONObject();
                subSubObj.put("questionId", questionId);

                subObjArray.put(subSubObj);
            }

            subObj.put("answers_list", subObjArray);

            objArray.put(subObj);
        }

        obj.put("competitors", objArray);

        return obj;
    }
}
