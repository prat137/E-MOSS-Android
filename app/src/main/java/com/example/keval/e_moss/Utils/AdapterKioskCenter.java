package com.example.keval.e_moss.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.keval.e_moss.MapsActivity;
import com.example.keval.e_moss.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdapterKioskCenter extends BaseAdapter {
    private JSONArray data;
    private LayoutInflater inflater = null;
    Activity a;

    public AdapterKioskCenter(Activity activity, JSONArray data) {
        this.data = data;
        this.a = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.length();
    }

    public Object getItem(int position) {
        return data.optJSONObject(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if (convertView == null)
            vi = inflater.inflate(R.layout.element_kiosk_center, null);

        final JSONObject jsonObject = data.optJSONObject(position);

        TextView tvKioskCenterName = (TextView) vi.findViewById(R.id.tvKioskCenterName);
        TextView tvKioskCenterAddress = (TextView) vi.findViewById(R.id.tvKioskCenterAddress);
        TextView tvKioskCenterMobile = (TextView) vi.findViewById(R.id.tvKioskCenterMobile);
        TextView tvKioskCenterEmail = (TextView) vi.findViewById(R.id.tvKioskCenterEmail);
        TextView tvKioskCenterCategory = (TextView) vi.findViewById(R.id.tvKioskCenterCategory);
        LinearLayout llElementKioskCenter = (LinearLayout) vi.findViewById(R.id.llElementKioskCenter);

        tvKioskCenterName.setText(jsonObject.optString("userName"));
        tvKioskCenterAddress.setText(jsonObject.optString("userAdd"));
        tvKioskCenterMobile.setText(jsonObject.optString("userNumber"));
        tvKioskCenterEmail.setText(jsonObject.optString("userEmail"));
        tvKioskCenterCategory.setText(jsonObject.optString("userCategory"));

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.startActivity(new Intent(a, MapsActivity.class).putExtra("object", jsonObject.toString()));
            }
        });

        return vi;
    }
}