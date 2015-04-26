package com.tracker.romanismagilov.friendtracker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tracker.romanismagilov.friendtracker.api.Api;
import com.tracker.romanismagilov.friendtracker.api.Constants;
import com.tracker.romanismagilov.friendtracker.api.KException;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;


public class LogInActivity extends ActionBarActivity {

    private final int REQUEST_LOGIN = 1;
    private static final int REQUEST_CAMERA = 2;
    private static final int REQUEST_SELECT_PHOTO = 3;
    public static String API_ID="2904017";
    public static Account account = new Account();
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void logIn(View v) throws JSONException, IOException, KException {
        ArrayList<Long> test;
        account.restore(this);

        //Если сессия есть создаём API для обращения к серверу
        if( account.access_token != null ) {
            api = new Api(account.access_token, API_ID);

            postToWall();
            //Данные юзера есть, можно постить на стену
            //api.createWallPost(1L, "test", null, null, false, false, false, null, null, null, 0L, null, null);

        } else {
            Intent intent = new Intent();
            intent.setClass(this, LoginWebActivity.class);
            startActivityForResult(intent, REQUEST_LOGIN);
        }

    }

    ArrayList<Long> arrayMy;
    private void postToWall() {
        //Общение с сервером в отдельном потоке чтобы не блокировать UI поток
        new Thread(){
            @Override
            public void run(){
                try {
                    String text="vhhjhjhjghjhgjhjg";
                    //api.createWallPost(account.user_id, text, null, null, false, false, false, null, null, null, 0L, null, null);
                    //api.sendMessage(account.user_id, 7521472L, text, null, null, null, null, null, null, null, null);
                    arrayMy = api.getOnlineFriends(account.user_id);
                    //Показать сообщение в UI потоке
                    runOnUiThread(successRunnable);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    Runnable successRunnable=new Runnable(){
        @Override
        public void run() {

            Toast.makeText(getApplicationContext(), "Запись успешно добавлена"+arrayMy.get(0), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MapActivity.class);// (MapActivity.class)
            startActivity(intent);
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch( requestCode ) {
            //Получили авторизацию от контакта
            case REQUEST_LOGIN:
                if( resultCode == RESULT_OK ) {
                    //авторизовались успешно
                    account.access_token = data.getStringExtra("token");
                    account.user_id = data.getLongExtra("user_id", 0);
                    account.save(LogInActivity.this);
                    api = new Api(account.access_token, API_ID);

                }
                break;
        }
    }
}
