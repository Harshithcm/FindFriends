package team7.uta.com.findfriends;

/**
 * Created by Harshith on 6/29/2016.
 */
import android.location.Location;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class: User
 *
 * Contains user details
 */
public class User implements Serializable {

    private String name;
    private String pwd;
    private String email;
    private Location currentLocation;
    private String hobbies;

    public User(String name, String pwd, String email,
                Location userLoc, String hobbies) {
        this.setName(name);
        this.setPwd(pwd);
        this.setEmail(email);
        this.setCurrentLocation(userLoc);
        this.setHobbies(hobbies);
    }

    public String getName() {
        return name;
    }

    public void setName(String lname) {
        this.name = lname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }
}
