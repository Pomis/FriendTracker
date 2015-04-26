package com.tracker.romanismagilov.friendtracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by romanismagilov on 26.04.15.
 */
public class FriendTrackerAPI {
    public void AddUser(long _VKID, String _Name, double _PointX, double _PointY) throws Exception{
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost httpost = new HttpPost("http://friendtracker.esy.es/add_new_user.php"); //создаю запрос
        httpost.addHeader("Content-Type","x-www-form-urlencoded; charset=windows-1251"); //добавляю туда заголовок

        HttpParams prms = new BasicHttpParams()
                .setParameter("VKID", String.valueOf(_VKID))
                .setParameter("Name", String.valueOf(_Name))
                .setParameter("PointX", String.valueOf(_PointX))
                .setParameter("PointY", String.valueOf(_PointY));

        httpost.setParams(prms); // добавляю их в запрос
        HttpResponse response = client.execute(httpost); //отправляю запрос
        HttpEntity entity = response.getEntity(); // видимо, получаю сущность ответа :)
    }
}
