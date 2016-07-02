package team7.uta.com.findfriends;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/**
 * Created by Harshith on 6/28/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RegisterActivity extends AppCompatActivity {
    public String TAG = "register_activity";
    public static final String PREFS_NAME = "UserData";
    public Object output = null;
    private HttpPost httppost;
    private HttpResponse response;
    private InputStream is;
    private HttpWrapper httpWrapper;
    private User user;
    private CheckBox Running, Soccer, Swimming,Tennis,Hiking,Cricket,Baseball,Guitar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText name = (EditText) findViewById(R.id.username);
       // final EditText last_name= (EditText) findViewById(R.id.last_name);
        final EditText password = (EditText) findViewById(R.id.editTextpassword);
        final EditText confirm_password = (EditText) findViewById(R.id.editTextconpwd);
        final EditText email= (EditText) findViewById(R.id.email);
        final Button sign_up=(Button) findViewById(R.id.register);
        final int duration=Toast.LENGTH_SHORT;
        final SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_APPEND);
        httpWrapper = new HttpWrapper();
        /*ArrayList<String> Hobbies =  new ArrayList<String>();
        Hobbies.add("Running");
        Hobbies.add("Soccer");
        Hobbies.add("Swimming");
        Hobbies.add("Tennis");
        Hobbies.add("Hiking");
        Hobbies.add("Cricket");
        Hobbies.add("Baseball");
        Hobbies.add("Guitar");*/
        final ArrayList<String> selectedHobbies =  new ArrayList<String>();
        Running = (CheckBox) findViewById(R.id.checkBox1);
        Soccer = (CheckBox) findViewById(R.id.checkBox2);
        Swimming = (CheckBox) findViewById(R.id.checkBox3);
        Tennis = (CheckBox) findViewById(R.id.checkBox4);
        Hiking = (CheckBox) findViewById(R.id.checkBox5);
        Cricket = (CheckBox) findViewById(R.id.checkBox6);
        Baseball = (CheckBox) findViewById(R.id.checkBox7);
        Guitar = (CheckBox) findViewById(R.id.checkBox8);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Running.isChecked())
                    selectedHobbies.add("Running");
                if(Soccer.isChecked())
                    selectedHobbies.add("Soccer");
                if(Swimming.isChecked())
                    selectedHobbies.add("Swimming");
                if(Tennis.isChecked())
                    selectedHobbies.add("Tennis");
                if(Hiking.isChecked())
                    selectedHobbies.add("Hiking");
                if(Cricket.isChecked())
                    selectedHobbies.add("Cricket");
                if(Baseball.isChecked())
                    selectedHobbies.add("Baseball");
                if(Guitar.isChecked()) {
                    selectedHobbies.add("Guitar");
                    //Toast.makeText(RegisterActivity.this, "Hobbies: "+"Guitar", Toast.LENGTH_SHORT).show();
                }

                String listString = "";

                for (String s : selectedHobbies)
                {
                    listString += s + ",";
                }
                final String hobby = listString;
                Toast.makeText(RegisterActivity.this, "Hobbies: "+hobby, Toast.LENGTH_SHORT).show();


                try {
                    if ((name.getText().toString().trim().equals("")) ||
                            (password.getText().toString().trim().equals("")) || (confirm_password.getText().toString().trim().equals("")) ||
                            (email.getText().toString().trim().equals(""))) {
                            Toast.makeText(getApplicationContext(), "All Fields are Required.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!(password.getText().toString().equals(confirm_password.getText().toString()))) {
                            Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
                        } else {

                            if (checkEmail(email.getText().toString())) {
                                //Toast.makeText(getApplicationContext(),"Succesfully logged in",Toast.LENGTH_SHORT).show();

                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("Name", name.getText().toString());
                                editor.putString("Password", password.getText().toString());
                                editor.putString("Email", email.getText().toString());
                                editor.commit();

                                String Name = name.getText().toString();
                                String pwd = password.getText().toString();
                                //byte[] hashedPwd = getHash(pwd);
                                String uemail = email.getText().toString();
                                String hobbies = hobby;
                                user = new User(Name, pwd, uemail, null, hobbies);
                                // declare parameters that are passed to PHP script
                                ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                                Toast.makeText(RegisterActivity.this, "Hobbies: "+hobbies, Toast.LENGTH_SHORT).show();
                                // define the parameter
                                //postParameters.add(new BasicNameValuePair("id",user.getUid()));
                                postParameters.add(new BasicNameValuePair("name", Name));
                                //postParameters.add(new BasicNameValuePair("lastname",lname));
                                postParameters.add(new BasicNameValuePair("email", uemail));
                                postParameters.add(new BasicNameValuePair("password", pwd));
                                postParameters.add(new BasicNameValuePair("hobbies", hobbies));

                                httpWrapper.setPostParameters(postParameters);

                                //http post
                                try {
                                    httppost = new HttpPost("http://omega.uta.edu/~hxc0610/gcm_server_files/register.php");
                                    httpWrapper.setRegisterActivity(RegisterActivity.this);
                                    httpWrapper.execute(httppost);

                                } catch (Exception e) {
                                    Log.e(TAG, "Error in http connection " + e.toString());
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Please select at least one", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.i(TAG, e.getMessage());
                }
               }
                public boolean checkEmail(String email) {
                    Pattern pattern = Pattern.compile("[A-Za-z]+\\.[A-Za-z]+@mavs\\.uta\\.edu$");
                    Matcher matcher = pattern.matcher(email);
                    return matcher.matches();
                }
                });
    }
    public void registerResult(String status) {
        if(status.equals("Success")) {
            Log.i(TAG,"Inside registerResult : Success");
            Intent MapActivity = new Intent(RegisterActivity.this, MapActivity.class);
            //user.setUid(getUser_id());
            MapActivity.putExtra("user", user);
            startActivity(MapActivity);
        } else {
            Toast.makeText(getApplicationContext(),"Registration failed",Toast.LENGTH_SHORT).show();
        }
    }
}
