package team7.uta.com.findfriends;

import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import android.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<Status> {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private String locationProvider;
    protected GoogleApiClient mGoogleApiClient = null;
    private LatLng latLng;

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onResult(@NonNull Status status) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try{
            mMap.setMyLocationEnabled(true);
            Criteria c = new Criteria();
            locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
            locationProvider = locationManager.getBestProvider(c, true);
            locationManager.requestLocationUpdates(locationProvider, 500, 20, this);

        }catch (SecurityException e){
            //fill
        }


//if (ContextCompat.checkSelfPermission(this, ManifestACCESS_FINE_LOCATIONINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//    mMap.setMyLocationEnabled(true);
//} else {
//    // Show rationale and request permission.
//}
        // Add a marker in Sydney and move the camera
        latLng = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }
    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }
    @Override
    public void onLocationChanged(Location location) {
//        // Remove previous geofence if any
//        if(!mGeofenceList.isEmpty()) {
//            removeGeoFence();
//        }
//        latLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
//
//        // define the parameter
////        postParameters.add(new BasicNameValuePair("user_id",user.getUid()));
////        postParameters.add(new BasicNameValuePair("lat",String.valueOf(location.getLatitude())));
////        postParameters.add(new BasicNameValuePair("lng",String.valueOf(location.getLongitude())));
//
////        HttpWrapper httpWrapper1 = new HttpWrapper();
////        HttpWrapper httpWrapper2 = new HttpWrapper();
////        httpWrapper1.setPostParameters(postParameters);
//
//        //http post
//        try{
//            //Put link to location
////            HttpPost httppost1 = new HttpPost("http://omega.uta.edu/~sxa1001/save_location.php");
////            HttpPost httppost2 = new HttpPost("http://omega.uta.edu/~sxa1001/get_users_location.php");
////            httpWrapper1.setMapActivity(this);
////            httpWrapper2.setMapActivity(this);
////            //httpWrapper1.execute(httppost1);
////            httpWrapper2.execute(httppost2);
//        }
//        catch(Exception e){
////            Log.e(TAG, "Error in http connection " + e.toString());
//        }
//
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        googleMap.animateCamera(CameraUpdateFactory.zoomTo(19));
//        addGeoFence(Constants.GEOFENCE_RADIUS, latLng);
        //this.user.setCurrentLocation(location);
    }
}
