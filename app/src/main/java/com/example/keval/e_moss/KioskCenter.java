package com.example.keval.e_moss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.keval.e_moss.Utils.AdapterKioskCenter;
import com.example.keval.e_moss.Utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class KioskCenter extends AppCompatActivity {

    ListView lvKioskCenter;
    AdapterKioskCenter adapterKioskCenter;
    JSONArray arrayKioskMain = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiosk_center);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        JSONObject objectKioskElement = new JSONObject();
        JSONObject objectKioskElement1 = new JSONObject();
        JSONObject objectKioskElement2 = new JSONObject();
        JSONObject objectKioskElement3 = new JSONObject();
        JSONObject objectKioskElement4 = new JSONObject();
        JSONObject objectKioskElement5 = new JSONObject();
        JSONObject objectKioskElement6 = new JSONObject();
        JSONObject objectKioskElement7 = new JSONObject();

        try {
            objectKioskElement.put("userName", "Rajesh Bhai");
            objectKioskElement.put("userAdd", "9,458,jaipur");
            objectKioskElement.put("userNumber", "+91 1236547890");
            objectKioskElement.put("userEmail", "rajesh@gmail.com");
            objectKioskElement.put("userCategory", "N.A.");
            objectKioskElement.put("userLat", "26.812615");
            objectKioskElement.put("userLon", "75.542911");

            objectKioskElement1.put("userName", "Hari Bhai");
            objectKioskElement1.put("userAdd", "hshu,czjk,jaipur");
            objectKioskElement1.put("userNumber", "+91 7412589630");
            objectKioskElement1.put("userEmail", "hari@gmail.com");
            objectKioskElement1.put("userCategory", "N.A.");
            objectKioskElement1.put("userLat", "26.850240");
            objectKioskElement1.put("userLon", "75.635481");

            objectKioskElement2.put("userName", "hello Bhai");
            objectKioskElement2.put("userAdd", "9bcbhbd,vfjhvhfb,jaipur");
            objectKioskElement2.put("userNumber", "+91 4521479632");
            objectKioskElement2.put("userEmail", "hello@gmail.com");
            objectKioskElement2.put("userCategory", "N.A.");
            objectKioskElement2.put("userLat", "26.807279");
            objectKioskElement2.put("userLon", "75.806288");

            objectKioskElement3.put("userName", "KIOLO Bhai");
            objectKioskElement3.put("userAdd", "hcdhcjz,zczdckjzc,jaipur");
            objectKioskElement3.put("userNumber", "+91 1214587412");
            objectKioskElement3.put("userEmail", "kiolo@gmail.com");
            objectKioskElement3.put("userCategory", "N.A.");
            objectKioskElement3.put("userLat", "26.886130");
            objectKioskElement3.put("userLon", "75.746367");

            objectKioskElement4.put("userName", "kujiji Bhai");
            objectKioskElement4.put("userAdd", "kvjxx,hdvdx,jaipur");
            objectKioskElement4.put("userNumber", "+91 45214785");
            objectKioskElement4.put("userEmail", "kevgjk@gmail.com");
            objectKioskElement4.put("userCategory", "N.A.");
            objectKioskElement4.put("userLat", "26.886519");
            objectKioskElement4.put("userLon", "75.834123");

            objectKioskElement5.put("userName", "LOPOLO Bhai");
            objectKioskElement5.put("userAdd", "cjjhkdj,dhkdhk,jaipur");
            objectKioskElement5.put("userNumber", "+91 4125896325");
            objectKioskElement5.put("userEmail", "hfhfshdfk@gmail.com");
            objectKioskElement5.put("userCategory", "N.A.");
            objectKioskElement5.put("userLat", "26.870782");
            objectKioskElement5.put("userLon", "75.692110");

            objectKioskElement6.put("userName", "dhsjfhkf Bhai");
            objectKioskElement6.put("userAdd", "fsfhfkjs,fdsfjkh,jaipur");
            objectKioskElement6.put("userNumber", "+91 4521478965");
            objectKioskElement6.put("userEmail", "oijuhyu@gmail.com");
            objectKioskElement6.put("userCategory", "N.A.");
            objectKioskElement6.put("userLat", "26.895705");
            objectKioskElement6.put("userLon", "75.783491");


            objectKioskElement7.put("userName", "Ramesh Bhai");
            objectKioskElement7.put("userAdd", "hvjhkjd,fjdkhfdx,jaipur");
            objectKioskElement7.put("userNumber", "+91 5565656556");
            objectKioskElement7.put("userEmail", "ramesh@gmail.com");
            objectKioskElement7.put("userCategory", "N.A.");
            objectKioskElement7.put("userLat", "26.822683");
            objectKioskElement7.put("userLon", "75.543727");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        arrayKioskMain.put(objectKioskElement);
        arrayKioskMain.put(objectKioskElement1);
        arrayKioskMain.put(objectKioskElement2);
        arrayKioskMain.put(objectKioskElement3);
        arrayKioskMain.put(objectKioskElement4);
        arrayKioskMain.put(objectKioskElement5);
        arrayKioskMain.put(objectKioskElement6);
        arrayKioskMain.put(objectKioskElement7);

        lvKioskCenter = (ListView) findViewById(R.id.lvKioskCenter);
        adapterKioskCenter = new AdapterKioskCenter(this, arrayKioskMain);
        lvKioskCenter.setAdapter(adapterKioskCenter);

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kiosk_center, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                CommonUtils.closeKeyboard(this);
                break;
            case R.id.menuKioskCenterMap:
                startActivity(new Intent(this, MapsActivity.class).putExtra("array", arrayKioskMain.toString()));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}