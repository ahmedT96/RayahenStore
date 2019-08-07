package com.rayahen.ryahen;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        View.OnClickListener,
        RoutingListener ,LocationListener{
double total_cost;
    private static final int REQUEST_LOCATION_CODE=1000;

    //Our Map
    private GoogleMap mMap;
    TextView adressinfo, dist, durt,textCost;
    //To store longitude and latitude from map
    private double longitude;
    private double latitude;
    String ditance, duration,cost = null;
    //Buttons
    private ImageButton buttonSave;
    private Button buttonCurrent;
    private ImageButton buttonView;
    public String distinition;
    LatLng latLnglocation;
    LatLng storelocation;
    LatLng newlocation,send_order;
    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent, R.color.colorAccent, R.color.primary_dark_material_light};

    //Google ApiClient
    private GoogleApiClient googleApiClient;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        adressinfo = findViewById(R.id.textAdress);
        dist = findViewById(R.id.dist);
        durt = findViewById(R.id.duration);
        textCost=findViewById(R.id.textCost);
 intent=getIntent();

        //Initializing googleapi client
        storelocation = new LatLng(intent.getExtras().getDouble("lat"),intent.getExtras().getDouble("lang"));
        ;



        polylines = new ArrayList<>();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        //Initializing views and adding onclick listeners
        buttonCurrent =  findViewById(R.id.buttonCurrent);
        buttonCurrent.setOnClickListener(this);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                distinition = place.getName().toString();
                newlocation = place.getLatLng();

                //   T
                //oast.makeText(MapsActivity.this, distinition, Toast.LENGTH_LONG).show();
                adressinfo.setText(distinition);
                onRoutingFail();
               // getRouteToMarker(newlocation);
                onRoutingFail();



            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CheckLocationPermission();
        }

    }

    private Boolean CheckLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);

            }
            return false;
        } else
            return true;

    }
    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    //Getting current location
    private void getCurrentLocation() {
        mMap.clear();
        //Creating a location object
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            //moving the map to location
            moveMap();


        }
    }

    //Function to move the map
    private void moveMap() {
        //String to display current latitude and longitude
        String msg = latitude + ", " + longitude;

        //Creating a LatLng Object to store Coordinates
        latLnglocation = new LatLng(latitude, longitude);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latLnglocation) //setting position
                .draggable(true) //Making the marker draggable
                .title("Current Location")); //Adding a title

        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLnglocation));

        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        //Displaying current coordinates in toast
     //   Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        getRouteToMarker(latLnglocation);



    }

    private void getRouteToMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(storelocation);
        markerOptions.title("Rayahen Store");
        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(storelocation, latLng)
                .build();
        routing.execute();
        Constant.lat= String.valueOf(latLng.latitude);
        Constant.log= String.valueOf(latLng.longitude);
        mMap.addMarker(markerOptions);


    }
public LatLng nearest(LatLng latLng){
        List<LatLng>latLngs=new ArrayList<>();
    List<Double>distance=new ArrayList<>();

    latLngs.add(storelocation);
        for (int i = 0; i<latLngs.size(); i++)
        {
             distance.set(i, SphericalUtil.computeDistanceBetween(latLng, latLngs.get(i)));

        }


    return null;
}
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //Clearing all the markers
        mMap.clear();

        //Adding a new marker to the current pressed position
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        //Getting the coordinates
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

        //Moving the map
        moveMap();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonCurrent) {
            getCurrentLocation();
            moveMap();
        }
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        if (e != null) {
          //  Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } else {
          //  Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
        Location loc1 = new Location("");
        loc1.setLatitude(latitude);
        loc1.setLongitude(longitude);

        Location loc2 = new Location("");
        loc2.setLatitude( intent.getExtras().getDouble("lat"));
        loc2.setLongitude(intent.getExtras().getDouble("lang"));
        float distanceInMeters = loc1.distanceTo(loc2);
        StringBuilder sb = new StringBuilder();
        StringBuilder pp = new StringBuilder();
        StringBuilder cos = new StringBuilder();

        total_cost=(distanceInMeters*0.001)*2;
        sb.append((int)(distanceInMeters*0.001));
        cos.append((int) total_cost);
        ditance = sb.toString();
        duration = pp.toString();

        dist.setText(ditance+" KM ");
        durt.setText(duration+" Min");

        textCost.setText(cos.toString()+" EGP");
    }
    public void onRoutingFail(){
        Location loc1 = new Location("");
        loc1.setLatitude(newlocation.latitude);
        loc1.setLongitude(newlocation.longitude);

        Location loc2 = new Location("");
        loc2.setLatitude( intent.getExtras().getDouble("lat"));
        loc2.setLongitude(intent.getExtras().getDouble("lang"));
        float distanceInMeters = loc1.distanceTo(loc2);
        StringBuilder sb = new StringBuilder();
        StringBuilder pp = new StringBuilder();
        StringBuilder cos = new StringBuilder();

        total_cost=(distanceInMeters*0.001)*2;

        cos.append((int) total_cost);
        ditance = sb.toString();
        duration = pp.toString();

        dist.setText(ditance+" KM ");
        durt.setText(duration+" Min");

        textCost.setText(cos.toString()+" EGP");
    }
    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortridindex) {

        if (polylines.size() > 0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i < route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);

        //    Toast.makeText(getApplicationContext(), "Route " + (i + 1) + ": distance - " + route.get(i).getDistanceValue() + ": duration - " + route.get(i).getDurationValue(), Toast.LENGTH_SHORT).show();
            StringBuilder sb = new StringBuilder();
            StringBuilder pp = new StringBuilder();
            StringBuilder cos = new StringBuilder();


            total_cost=(route.get(i).getDistanceValue()*0.001)*2;

            cos.append((int) total_cost);
            ditance = sb.toString();
            duration = pp.toString();

            dist.setText(ditance+" KM ");
            durt.setText(duration+" Min");

            textCost.setText(cos.toString()+" EGP");
           // textCost.setText();
        }

    }

    @Override
    public void onRoutingCancelled() {

    }


    public void DoneCostMap(View view) {
        Intent intent=new Intent(MapsActivity.this,Cart.class);
       Constant.cost_adress =total_cost;
        //Constant.locatoin = String.valueOf(total_cost);
        if(!(Constant.cost_adress ==0)) {
            if(Constant.cost_adress <=40)
            {
                if(Constant.cost_adress <=6)
                    Constant.cost_adress=6;
                startActivity(intent);
                Constant.f=true;
                finish();
            }
            else
            {
                Toast.makeText(MapsActivity.this, "not suport The Address ", Toast.LENGTH_LONG).show();

            }

        }
        else
        {
            Toast.makeText(MapsActivity.this, "please find th address ", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText( getApplicationContext(),"Gps Disabled  please Enable",Toast.LENGTH_LONG ).show();
    }


    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText( getApplicationContext(),"Gps Enabled",Toast.LENGTH_SHORT).show();
    }

}
