package com.example.keval.e_moss;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    JSONObject mainObject;
    JSONArray arrayFullData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        arrayFullData = new JSONArray();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mainObject = new JSONObject();

        try {
            if (getIntent().hasExtra("object")) {

                String data = getIntent().getStringExtra("object");

                mainObject = new JSONObject(data);


            } else {
                arrayFullData = new JSONArray(getIntent().getStringExtra("array"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        CameraPosition cameraPosition = null;

        if (getIntent().hasExtra("object")) {
            LatLng sydney = new LatLng(mainObject.optDouble("userLat"), mainObject.optDouble("userLon"));

            mMap.addMarker(new MarkerOptions().position(sydney).title(mainObject.optString("userAdd")));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

            cameraPosition = new CameraPosition.Builder()
                    .target(sydney)      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
        } else {
            for (int i = 0; i < arrayFullData.length() - 1; i++) {
                JSONObject objectFullData = arrayFullData.optJSONObject(i);
                LatLng sydney = new LatLng(objectFullData.optDouble("userLat"), objectFullData.optDouble("userLon"));

                mMap.addMarker(new MarkerOptions().position(sydney).title(objectFullData.optString("userAdd")));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

                cameraPosition = new CameraPosition.Builder()
                        .target(sydney)      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();
            }
        }
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }
}
