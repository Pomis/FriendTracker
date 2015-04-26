package com.tracker.romanismagilov.friendtracker;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

public class MapActivity extends Activity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng sydney = new LatLng(-33.867, 151.206);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(sydney));
    }

    public void showMe(View view) {
        Toast.makeText(this, "ГОВНОЖОПА", Toast.LENGTH_LONG);
        //FriendTrackerAPI frapi = new FriendTrackerAPI();
        //frapi.AddUser(0L, null, 0F, 0F);
        ((WebView) findViewById(R.id.secretwv)).loadUrl("http://friendtracker.esy.es/add_new_user.php?VKID="+LogInActivity.account.user_id+"&Name=test&PointX=1234&PointY=431");
        //((WebView) findViewById(R.id.secretwv)).destroy();
    }
}