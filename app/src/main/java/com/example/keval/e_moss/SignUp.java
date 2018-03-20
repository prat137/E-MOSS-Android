package com.example.keval.e_moss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.keval.e_moss.Utils.CommonUtils;
import com.example.keval.e_moss.Utils.Constants;
import com.mpt.storage.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    Button btSignUpSaveNext;
    ImageView ivSignUpBack;
    EditText etSignUpFirstName, etSignUpLastName, etSignUpContact, etSignUpEmail, etSignUpConfirmPassword,
            etSignUpPassword, etSignUpAlternateEmail, etSignUpUserName;
    JSONArray arrayAllUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btSignUpSaveNext = (Button) findViewById(R.id.btSignUpSaveNext);
        ivSignUpBack = (ImageView) findViewById(R.id.ivSignUpBack);
        etSignUpFirstName = (EditText) findViewById(R.id.etSignUpFirstName);
        etSignUpLastName = (EditText) findViewById(R.id.etSignUpLastName);
        etSignUpContact = (EditText) findViewById(R.id.etSignUpContact);
        etSignUpEmail = (EditText) findViewById(R.id.etSignUpEmail);
        etSignUpConfirmPassword = (EditText) findViewById(R.id.etSignUpConfirmPassword);
        etSignUpPassword = (EditText) findViewById(R.id.etSignUpPassword);
        etSignUpAlternateEmail = (EditText) findViewById(R.id.etSignUpAlternateEmail);
        etSignUpUserName = (EditText) findViewById(R.id.etSignUpUserName);

        arrayAllUserData = new JSONArray();

        try {
            if (!SharedPreferenceUtil.getString(Constants.USER_DETAILS, "").equalsIgnoreCase(""))
                arrayAllUserData = new JSONArray(SharedPreferenceUtil.getString(Constants.USER_DETAILS, ""));
            else
                arrayAllUserData = new JSONArray();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        btSignUpSaveNext.setOnClickListener(this);
        ivSignUpBack.setOnClickListener(this);

    }

    public void submitCondition() {
        if (etSignUpUserName.getText().toString().trim().equalsIgnoreCase(""))
            Toast.makeText(this, "Enter User Name", Toast.LENGTH_SHORT).show();
        else if (etSignUpFirstName.getText().toString().trim().equalsIgnoreCase(""))
            Toast.makeText(this, "Enter First Name", Toast.LENGTH_SHORT).show();
        else if (etSignUpLastName.getText().toString().trim().equalsIgnoreCase(""))
            Toast.makeText(this, "Enter Last Name", Toast.LENGTH_SHORT).show();
        else if (etSignUpContact.getText().toString().trim().equalsIgnoreCase(""))
            Toast.makeText(this, "Enter Contact Number", Toast.LENGTH_SHORT).show();
        else if (etSignUpContact.getText().toString().length() < 10)
            Toast.makeText(this, "Enter Correct Contact Number", Toast.LENGTH_SHORT).show();
        else if (etSignUpEmail.getText().toString().trim().equalsIgnoreCase(""))
            Toast.makeText(this, "Enter Email Id", Toast.LENGTH_SHORT).show();
        else if (!CommonUtils.isValidEmail(etSignUpEmail.getText().toString().trim()))
            Toast.makeText(this, "Enter Correct Email Id", Toast.LENGTH_SHORT).show();
        else if (etSignUpPassword.getText().toString().trim().equalsIgnoreCase(""))
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        else if (!CommonUtils.isLegalPassword(etSignUpPassword.getText().toString().trim()))
            Toast.makeText(this, "Enter Correct Password", Toast.LENGTH_SHORT).show();
        else if (etSignUpConfirmPassword.getText().toString().trim().equalsIgnoreCase(""))
            Toast.makeText(this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
        else if (!etSignUpConfirmPassword.getText().toString().trim().equalsIgnoreCase(etSignUpPassword.getText().toString().trim()))
            Toast.makeText(this, "Confirm Password is not Match", Toast.LENGTH_SHORT).show();
        else if (etSignUpAlternateEmail.getText().toString().trim().equalsIgnoreCase(""))
            Toast.makeText(this, "Enter Alternate Email Id", Toast.LENGTH_SHORT).show();
        else if (!CommonUtils.isValidEmail(etSignUpAlternateEmail.getText().toString().trim()))
            Toast.makeText(this, "Enter Correct Alternate Email Id", Toast.LENGTH_SHORT).show();
        else if (etSignUpAlternateEmail.getText().toString().trim().equalsIgnoreCase(etSignUpEmail.getText().toString().trim()))
            Toast.makeText(this, "Primary and Secondary Email Id can't be same", Toast.LENGTH_SHORT).show();
        else {

            if (arrayAllUserData.length() != 0)
                for (int i = 0; i <= arrayAllUserData.length() - 1; i++) {
                    JSONObject objectAllUserData = arrayAllUserData.optJSONObject(i);

                    if (etSignUpUserName.getText().toString().trim().equalsIgnoreCase(objectAllUserData.optString("userName"))) {
                        Toast.makeText(this, "UserName Already Taken", Toast.LENGTH_SHORT).show();
                        break;
                    } else if (etSignUpEmail.getText().toString().trim().equalsIgnoreCase(objectAllUserData.optString("email")) ||
                            etSignUpEmail.getText().toString().trim().equalsIgnoreCase(objectAllUserData.optString("alternateEmail"))) {
                        Toast.makeText(this, "Email Id Already Taken", Toast.LENGTH_SHORT).show();
                        break;
                    } else if (etSignUpContact.getText().toString().trim().equalsIgnoreCase(objectAllUserData.optString("contact"))) {
                        Toast.makeText(this, "Contact number already taken", Toast.LENGTH_SHORT).show();
                        break;
                    } else if (etSignUpAlternateEmail.getText().toString().trim().equalsIgnoreCase(objectAllUserData.optString("email")) ||
                            etSignUpAlternateEmail.getText().toString().trim().equalsIgnoreCase(objectAllUserData.optString("alternateEmail"))) {
                        Toast.makeText(this, "Alternate Email Id Already Taken", Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        register();
                        Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, DashBoard.class));
                        break;

                    }
                }
            else {
                register();
                Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, DashBoard.class));

            }
        }
    }

    public void register() {
        JSONArray arrayUserData = new JSONArray();
        JSONObject objectUserInfo = new JSONObject();

        try {
            objectUserInfo.put("userName", etSignUpUserName.getText().toString().trim());
            objectUserInfo.put("firstName", etSignUpFirstName.getText().toString().trim());
            objectUserInfo.put("lastName", etSignUpLastName.getText().toString().trim());
            objectUserInfo.put("contactNo", etSignUpContact.getText().toString().trim());
            objectUserInfo.put("email", etSignUpEmail.getText().toString().trim());
            objectUserInfo.put("password", etSignUpPassword.getText().toString().trim());
            objectUserInfo.put("alternateEmail", etSignUpAlternateEmail.getText().toString().trim());

            arrayUserData.put(objectUserInfo);

            SharedPreferenceUtil.putValue(Constants.USER_DETAILS, arrayUserData.toString());
            SharedPreferenceUtil.save();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                CommonUtils.closeKeyboard(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        CommonUtils.closeKeyboard(this);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSignUpSaveNext:
                submitCondition();
                break;
            case R.id.ivSignUpBack:
                finish();
                CommonUtils.closeKeyboard(this);
                break;
        }
    }
}