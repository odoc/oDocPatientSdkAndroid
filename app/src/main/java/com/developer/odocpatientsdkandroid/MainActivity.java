package com.developer.odocpatientsdkandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import life.odoc.patientsdk.OdocPatientSdk;

import static life.odoc.patientsdk.OdocPatientSdk.Mode.DEVELOPMENT;
import static life.odoc.patientsdk.OdocPatientSdk.Param.AUTH_TOKEN;

public class MainActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "TestData";
    public static final String SHARED_PREFERENCES_DEF_VALUE = "";

    private OdocPatientSdk.Callback<Bundle> callback = new OdocPatientSdk.Callback<Bundle>() {
        @Override
        public void onUpdate(@NonNull Bundle response) {
            String token = response.getString(AUTH_TOKEN);

            /*This token can be saved in any data source for next use. For example, SharedPreferences can be used here.
             */
            SharedPreferences.Editor editor = MainActivity.this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString(AUTH_TOKEN, token);
            editor.apply();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OdocPatientSdk.init(this.getApplication());

        String webToken = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString(AUTH_TOKEN, SHARED_PREFERENCES_DEF_VALUE);

        OdocPatientSdk odocPatientSdk = OdocPatientSdk.getInstance();
        odocPatientSdk.attachListener(this.callback);
        odocPatientSdk.setAppId("1.0.0_test_1");
        odocPatientSdk.setAuthToken(webToken);
        odocPatientSdk.setSdkMode(DEVELOPMENT);
        odocPatientSdk.setPhoneNumber("");
        odocPatientSdk.setFirstName("Kasun");
        odocPatientSdk.setLastName("Chinthaka");
        odocPatientSdk.setJsonData("");
        odocPatientSdk.show();

    }
}
