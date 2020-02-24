package com.developer.odocpatientsdkandroid;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import life.odoc.patientsdk.OdocPatientSdk;

import static life.odoc.patientsdk.OdocPatientSdk.Mode.DEVELOPMENT;
import static life.odoc.patientsdk.OdocPatientSdk.Param.AUTH_TOKEN;

/**
 * This is only for testing purposes
 *
 * @author Kasun Chinthaka {@literal <kasun@odoc.life>}
 * @since 1.0.0
 */
public class TestActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "TestData";
    private static final String SHARED_PREFERENCES_DEF_VALUE = "";

    private OdocPatientSdk.Callback<Bundle> callback = new OdocPatientSdk.Callback<Bundle>() {
        @Override
        public void onUpdate(@NonNull Bundle response) {
            String token = response.getString(AUTH_TOKEN);

            /*This token can be saved in any data source for next use. For example, SharedPreferences can be used here.
             */
            SharedPreferences.Editor editor = TestActivity.this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString(AUTH_TOKEN, token);
            editor.apply();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        OdocPatientSdk.init(this.getApplication());

        String webToken = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString(AUTH_TOKEN, SHARED_PREFERENCES_DEF_VALUE);

        OdocPatientSdk odocPatientSdk = OdocPatientSdk.getInstance();

        //Mandatory
        odocPatientSdk.attachListener(this.callback);
        odocPatientSdk.setAppId("1.0.0_test_1"); // 1.0.0_test_1 is only for testing purposes.
        odocPatientSdk.setAuthToken(webToken);
        odocPatientSdk.setSdkMode(DEVELOPMENT);

        //Optional
        odocPatientSdk.setPhoneNumber("");
        odocPatientSdk.setFirstName("Kasun");
        odocPatientSdk.setLastName("Chinthaka");
        odocPatientSdk.setJsonData("");

        odocPatientSdk.show();

    }
}
