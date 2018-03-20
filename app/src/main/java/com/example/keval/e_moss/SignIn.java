package com.example.keval.e_moss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.keval.e_moss.Utils.Constants;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    Button btSignInSignUp, btSignInSignIn;
    EditText etSignInUserName, etSignInPassword;
    JSONArray arrayAllUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btSignInSignUp = (Button) findViewById(R.id.btSignInSignUp);
        btSignInSignIn = (Button) findViewById(R.id.btSignInSignIn);
        etSignInUserName = (EditText) findViewById(R.id.etSignInUserName);
        etSignInPassword = (EditText) findViewById(R.id.etSignInPassword);

        btSignInSignUp.setOnClickListener(this);
        btSignInSignIn.setOnClickListener(this);

        arrayAllUserData = new JSONArray();

        try {
            if (!SharedPreferenceUtil.getString(Constants.USER_DETAILS, "").equalsIgnoreCase(""))
                arrayAllUserData = new JSONArray(SharedPreferenceUtil.getString(Constants.USER_DETAILS, ""));
            else
                arrayAllUserData = new JSONArray();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void login() {

        if (etSignInUserName.getText().toString().trim().equalsIgnoreCase(""))
            Toast.makeText(this, "Enter UserName", Toast.LENGTH_SHORT).show();
        else if (etSignInPassword.getText().toString().trim().equalsIgnoreCase(""))
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        else {

            if (arrayAllUserData.length() != 0)
                for (int i = 0; i <= arrayAllUserData.length() - 1; i++) {
                    JSONObject objectAllUserData = arrayAllUserData.optJSONObject(i);

                    if (!etSignInUserName.getText().toString().trim().equalsIgnoreCase(objectAllUserData.optString("userName"))
                            || !etSignInPassword.getText().toString().trim().equalsIgnoreCase(objectAllUserData.optString("password"))) {
                        Toast.makeText(this, "Wrong Credential", Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        startActivity(new Intent(this, DashBoard.class));
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            else
                Toast.makeText(this, "Wrong Credential", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSignInSignUp:
                startActivity(new Intent(SignIn.this, SignUp.class));
                break;
            case R.id.btSignInSignIn:
                login();
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        CommonUtil.closeKeyboard(this);
        finish();
        super.onBackPressed();
    }
}
