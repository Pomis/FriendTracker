package com.tracker.romanismagilov.friendtracker;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by romanismagilov on 26.04.15.
 */
public class UpdateTask extends AsyncTask<String, Void, JSONObject> {
    Context context;

    public UpdateTask() {
        super();
        //this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        return loadJSON(urls[0]);
    }

    public JSONObject loadJSON(String url) {

        JSONParser jParser = new JSONParser();
        // здесь параметры необходимые в запрос добавляем
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("my_param", "param_value"));
        // посылаем запрос методом GET
        JSONObject json = jParser.makeHttpRequest(url, "GET", params);

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject jsonData) {
        // если какой-то фейл, проверяем на null
        // фейл может быть по многим причинам: сервер сдох, нет сети на устройстве и т.д.
        if (jsonData != null) {
            super.onPostExecute(jsonData);
            String res = "";
            try {
                // прочитать параметр, который отправил сервер;
                // здесь вместо "result" подставляйте то, что вам надо
                res = jsonData.getString("result");
                // что-то делаем, к примеру вызываем метод главного Activity на обновление GUI
                //((MainActivity) context).updateGUI(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
           // ((MainActivity) context).updateGUI(res);
        }
    }

}
