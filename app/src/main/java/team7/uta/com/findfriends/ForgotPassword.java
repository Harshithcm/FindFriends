package team7.uta.com.findfriends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.StrictMode;

import org.apache.http.client.methods.HttpPost;

import java.sql.ResultSet;
import android.util.Log;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ForgotPassword extends AppCompatActivity {
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

    }
    public void forgotPasswordResult(String result) {
        //Log.e("Main actiity", "Error in loginResult " );
        //Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();
        if(result.contains("Success")) {
            try {
                JSONObject jObject  = new JSONObject(result);
                String email = jObject.getString("email");
                user = new User(null,null,email,null,null);
                Intent loginActivity = new Intent(ForgotPassword.this, MainActivity.class);
                startActivity(loginActivity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(),"Enter Correct E-mail id",Toast.LENGTH_SHORT).show();
        }
    }

    public void doLogin(View view) {
        Intent intentl = new Intent(ForgotPassword.this, MainActivity.class);
        startActivity(intentl);
    }
}