package com.atrio.urprotected.userdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.atrio.urprotected.R;
import com.hbb20.CountryCodePicker;

public class RegistraionActivity extends AppCompatActivity {
    private EditText et_phn;
    private Button btn_nxt;
    private String phn_no,isd_code;
    CountryCodePicker ccp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registraion);
        ccp=(CountryCodePicker) findViewById(R.id.sp_country);
        et_phn=(EditText)findViewById(R.id.et_phone);
        btn_nxt=(Button)findViewById(R.id.btn_next);
        isd_code=ccp.getSelectedCountryCodeWithPlus();
        Log.i("onVerify22","" + isd_code);

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override

            public void onCountrySelected() {
                isd_code=ccp.getSelectedCountryCodeWithPlus();
                Toast.makeText(RegistraionActivity.this, "" + ccp.getSelectedCountryCodeWithPlus(), Toast.LENGTH_SHORT).show();
            }
        });
        btn_nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validatePhoneNumber()) {
                    return;
                }
                phn_no=isd_code+et_phn.getText().toString().trim();
                Intent intent =new Intent(RegistraionActivity.this,VerifyOTP.class);
                intent.putExtra("phn_number",phn_no);
                Log.i("onCodeSent4:" ,""+ phn_no);
                startActivity(intent);
                finish();
            }
        });

    }
    private boolean validatePhoneNumber() {
        String phoneNumber = et_phn.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            et_phn.setError("Invalid phone number.");
            return false;
        }

        return true;
    }
}
