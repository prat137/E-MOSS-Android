package com.example.keval.e_moss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.keval.e_moss.Utils.CommonUtils;

public class FragmentContactUs extends Fragment implements View.OnClickListener {

    EditText etContactUsName, etContactUsMobile, etContactUsEmail, etContactUsMessage, etContactUsSubject;
    Button btContactSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        etContactUsName = (EditText) view.findViewById(R.id.etContactUsName);
        etContactUsMobile = (EditText) view.findViewById(R.id.etContactUsMobile);
        etContactUsSubject = (EditText) view.findViewById(R.id.etContactUsSubject);
        etContactUsEmail = (EditText) view.findViewById(R.id.etContactUsEmail);
        etContactUsMessage = (EditText) view.findViewById(R.id.etContactUsMessage);
        btContactSubmit = (Button) view.findViewById(R.id.btContactSubmit);

        btContactSubmit.setOnClickListener(this);

        return view;
    }

    public void submitContact() {
        if (TextUtils.isEmpty(etContactUsName.getText().toString()) && TextUtils.isEmpty(etContactUsMobile.getText().toString()) && TextUtils.isEmpty(etContactUsEmail.getText().toString()) && TextUtils.isEmpty(etContactUsEmail.getText().toString()) && TextUtils.isEmpty(etContactUsSubject.getText().toString()) && TextUtils.isEmpty(etContactUsMessage.getText().toString()))
            Toast.makeText(getActivity(), "Enter User Name", Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(etContactUsName.getText().toString()))
            Toast.makeText(getActivity(), "Enter First Name", Toast.LENGTH_SHORT).show();
        else if (etContactUsName.getText().toString().length() < 5 || etContactUsName.getText().toString().length() > 20)
            Toast.makeText(getActivity(), "Contact no should greater than 5", Toast.LENGTH_SHORT).show();
        else if (etContactUsMobile.length() != 10)
            Toast.makeText(getActivity(), "Enter Contact No.", Toast.LENGTH_SHORT).show();
        else if (!CommonUtils.isValidEmail(etContactUsEmail.getText().toString()))
            Toast.makeText(getActivity(), "Enter Email Id", Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(etContactUsSubject.getText().toString()))
            Toast.makeText(getActivity(), "Enter Subject", Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(etContactUsMessage.getText().toString()))
            Toast.makeText(getActivity(), "Enter Message", Toast.LENGTH_SHORT).show();
        else
            startActivity(new Intent(getActivity(), DashBoard.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btContactSubmit:
                submitContact();
                break;
        }
    }
}
