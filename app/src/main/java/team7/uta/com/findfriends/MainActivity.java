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


public class MainActivity extends AppCompatActivity {
    String inputTextName;
    String inputTextPass;
    private String TAG = "login_activity";
    public static final String PREFS_NAME = "UserData";
    public ResultSet output = null;
    private HttpWrapper httpWrapper;
    private HttpPost httppost;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        httpWrapper = new HttpWrapper();
        final Button go = (Button) findViewById(R.id.login);
        final EditText editTextName = (EditText) findViewById(R.id.Name);
        editTextName.setSingleLine();
        editTextName.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            public boolean onEditorAction(TextView textview, int i, KeyEvent keyevent){
                boolean handled = false;
                if(i== EditorInfo.IME_ACTION_NEXT){
                    //show toast for input
                    inputTextName = textview.getText().toString();
                    Toast.makeText(MainActivity.this,"Your name is: "+inputTextName,Toast.LENGTH_SHORT).show();
                }
                return handled;
            }
        });

        final EditText editTextPass = (EditText) findViewById(R.id.Password);
        editTextPass.setSingleLine();
        editTextPass.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            public boolean onEditorAction(TextView textview, int i, KeyEvent keyevent){
                boolean handled = false;
                if(i== EditorInfo.IME_ACTION_NEXT){
                    //show toast for input
                    inputTextPass = textview.getText().toString();
                    Toast.makeText(MainActivity.this,"Your name is: "+inputTextPass,Toast.LENGTH_SHORT).show();
                }
                return handled;
            }
        });

        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(register_intent);

            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(editTextName == null || editTextPass == null) {
                    Toast.makeText(getApplicationContext(), "Please enter UserName and password", Toast.LENGTH_LONG).show();
                    return;
                }

                // declare parameters that are passed to PHP script
                ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

                // define the parameter
                postParameters.add(new BasicNameValuePair("username",editTextName.getText().toString()));
                postParameters.add(new BasicNameValuePair("password",editTextPass.getText().toString()));

                httpWrapper.setPostParameters(postParameters);

                //http post
                try{
                    httppost = new HttpPost("https://omega.uta.edu/~hxc0610/gcm_server_files/login.php");
                    httpWrapper.setLoginActivity(MainActivity.this);
                    httpWrapper.execute(httppost);
                }
                catch(Exception e){
                    Log.e("register_activity", "Error in http connection " + e.toString());
                }

            }
        });
        go.setFocusable(true);



    }
    public void loginResult(String result) {
        //Log.e("Main actiity", "Error in loginResult " );
        //Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();
        if(result.contains("Success")) {
            try {
                JSONObject jObject  = new JSONObject(result);
                String name = jObject.getString("username");
                String password = jObject.getString("password");
                String email = jObject.getString("email");
                //String hobbies = jObject.getString("hobbies");
                //Log.i(TAG,"Hobbies: "+hobbies);
                //ArrayList<String> selectedHobbies = new ArrayList<String>(10);

               /* String[] rawHobbies = hobbies.split(",");
                for(int i = 0;i<rawHobbies.length;i++) {
                    selectedHobbies.add(rawHobbies[i]);
                }*/

                user = new User(name,password,email,null,null);//selectedHobbies);
                Intent mapActivity = new Intent(MainActivity.this, MapActivity.class);
                mapActivity.putExtra("user", user);
                startActivity(mapActivity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_SHORT).show();
        }
    }

}
