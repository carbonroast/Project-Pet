package ece.capstoneprojectpet;

import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import android.os.Handler;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.TileOverlayOptions;

import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Map extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    // googlemap, api client, location request and UI settings are initiated
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;

    // UNUSED: boolean determines if heatmap must be updated
    // private boolean updateHeatMap = true;

    // list of heatmap latitude and longitude locations
    List<LatLng> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setUpMapIfNeeded();
        buildGoogleApiClient();

        // turn on UI controls
        mMap.setMyLocationEnabled(true);
        // compass will only show if rotated not north
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // location request is created to check every 10 seconds, 1second fastest
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(1000);

        // UNUSED
        // run timer task to update heatmap every 5 minutes
        // timerTask();
    }
    /* UNUSED: PROGRAM WILL UPDATE EVERY LOCATION EVERY LOCATION UDPATE
    //set the updateHeatMap to true every 5 minutes
    Handler handler = new Handler();
    public final void timerTask(){
        handler.postDelayed(new Runnable() {
            public void run() {
                updateHeatMap = true;
                handler.postDelayed(this, 1000*60*5);
            }
        }, 1000*60*5);
    }*/

    /* google api client object */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected() == true) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
    // update device's own location marker
    private void handleNewLocation(Location location) {
        mMap.clear();
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng l = new LatLng(currentLatitude, currentLongitude);
        MarkerOptions options = new MarkerOptions().position(l).title("My Location || API Key: AIzaSyDPtH3Os3mbQxucRGzabrkbnr1UdDIbxA4");
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(l));
        setMarkers(location);
        addHeatMap(l);
    }

    // add heat map to the map every time a new location is updated
    private void addHeatMap(LatLng l) {
        // UNUSED: updateHeatMap would be used to add to heat map
        // add location to file, locations split by :
        writeFile(l.latitude + ":" + l.longitude + ":");
        // read file with all the locations
        String s = readFile();
        String[] parts = s.split(":");
        for(int n = 0; n < parts.length; n = n + 2) {
            double lat = Double.parseDouble(parts[n]);
            double lng = Double.parseDouble(parts[n+1]);
            LatLng loc = new LatLng(lat, lng);
            list.add(loc);
        }
        list.add(l);
        Toast.makeText(getApplicationContext(), "HeatMap Updated - Number of Recorded Locations: " + list.size(), Toast.LENGTH_SHORT).show();
        // Create a heat map tile provider
        HeatmapTileProvider provider = new HeatmapTileProvider.Builder().data(list).build();
        // Add a tile overlay to the map, using the heat map tile provider.
        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
    }

    private void setMarkers(Location location) {
        Location loc = new Location("location");
        // set BCC location
        loc.setLatitude(40.523128);
        loc.setLongitude(-74.458797);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(loc.getLatitude(), loc.getLongitude()))
                .title("Busch Campus Center || Distance: " + location.distanceTo(loc) + " meters"));
        // set highpoint stadium location
        loc.setLatitude(40.513817);
        loc.setLongitude(-74.464844);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(loc.getLatitude(), loc.getLongitude()))
                .title("HighPoint Solutions Stadium || Distance: " + location.distanceTo(loc) + " meters"));
        // set EE building
        loc.setLatitude(40.521663);
        loc.setLongitude(-74.460665);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(loc.getLatitude(), loc.getLongitude()))
                .title("Electrical Engineering Building || Distance: " + location.distanceTo(loc) + " meters"));
        // set RSC building
        loc.setLatitude(40.502661);
        loc.setLongitude(-74.451771);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(loc.getLatitude(), loc.getLongitude()))
                .title("Rutgers Student Center || Distance: " + location.distanceTo(loc) + " meters"));
        // set Old Queens
        loc.setLatitude(40.498720);
        loc.setLongitude(-74.446229);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(loc.getLatitude(), loc.getLongitude()))
                .title("Old Queens || Distance: " + location.distanceTo(loc) + " meters"));

    }

    // when location changes
    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }
    // when connected to google
    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
        }
        else {
            handleNewLocation(location);
        }
    }
    // when connection is suspended
    @Override
    public void onConnectionSuspended(int i) {    }
    // when connection is lost/failed
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Google play will resolve errors
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, 10000);
            } catch (IntentSender.SendIntentException e) {
            }
        }
    }

    // write log to file
    private void writeFile(String log) {
        try {
            FileOutputStream file = openFileOutput("PETlocations.txt", MODE_APPEND);
            OutputStreamWriter outputWriter = new OutputStreamWriter(file);
            outputWriter.write(log);
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // read log from file
    private String readFile() {
        try {
            char[] buffer= new char[100];
            String log = "";
            FileInputStream file = openFileInput("PETlocations.txt");
            InputStreamReader InputRead = new InputStreamReader(file);
            int read;
            while ((read = InputRead.read(buffer)) > 0) {
                // char to string
                String readstring=String.copyValueOf(buffer,0,read);
                log = log + readstring;
            }
            InputRead.close();
            return log;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}