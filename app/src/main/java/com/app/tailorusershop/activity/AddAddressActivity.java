package com.app.tailorusershop.activity;

import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.tailorusershop.R;
import com.app.tailorusershop.databinding.ActivityAddAddressBinding;
import com.app.tailorusershop.databinding.DeleteDialogLayoutBinding;
import com.app.tailorusershop.databinding.RequestPermissionDialogLayoutBinding;
import com.app.tailorusershop.responses.UpdateProfileResponse;
import com.app.tailorusershop.retrofit.CallWebService;
import com.app.tailorusershop.retrofit.ResponseHandler;
import com.app.tailorusershop.retrofit.WebServiceConstants;
import com.app.tailorusershop.utlis.Util;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.CurrentLocationRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AddAddressActivity extends AppCompatActivity {
    private ActivityAddAddressBinding binding;
    private Context context = AddAddressActivity.this;
    private final String TAG = "AddAddressActivity";
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationManager locationManager;
    boolean firstTime = false;
    private Location currentLocation, gpsLocation, networkLocation;
    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Add Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20 * 1000);
        locationRequest.setFastestInterval(5 * 1000);

        if (hasGps())
        {
            if (checkPermission())
            {
                getCurrentLocation();
            }

        } else {
            enableLoc(this);
        }
        if (!checkPermission()) {
            gpsDialog();
        }


        binding.btnSaveAddress.setOnClickListener(view -> {
            if (validation()) {
                callAddAddressApi(binding.etAddress1.getText().toString(), binding.etAddress2.getText().toString(), binding.etPostalCode.getText().toString(), binding.etCity.getText().toString(), binding.etState.getText().toString());

            }
        });
    }

    private boolean validation() {
        if (binding.etAddress1.getText().toString().equals("")) {
            Toast.makeText(context, "Enter Address", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etAddress2.getText().toString().equals("")) {
            Toast.makeText(context, "Enter Address 2", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etCity.getText().toString().equals("")) {
            Toast.makeText(context, "Enter city", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etPostalCode.getText().equals("")) {
            Toast.makeText(context, "Enter PostalCode", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etPostalCode.getText().length() < 6) {
            Toast.makeText(context, "Enter valid PostalCode ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.etState.getText().equals("")) {
            Toast.makeText(context, "Enter state", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void callAddAddressApi(String address1, String address2, String pincode, String city, String state) {

        JSONObject object = new JSONObject();
        try {
            //object.put(WebServiceConstants.WebServiceParams.ID, PrefManager.getInstance(context).getUserDetails(PrefManager.USER_ID));
            object.put(WebServiceConstants.WebServiceParams.USER_ID, "52");
            object.put(WebServiceConstants.WebServiceParams.NAME, "Arun");
            object.put(WebServiceConstants.WebServiceParams.MOBILE, "9873820091");
            object.put(WebServiceConstants.WebServiceParams.ADDRESS1, address1);
            object.put(WebServiceConstants.WebServiceParams.ADDRESS2, address2);
            object.put(WebServiceConstants.WebServiceParams.CITY, city);
            object.put(WebServiceConstants.WebServiceParams.PINCODE, pincode);
            object.put(WebServiceConstants.WebServiceParams.STATE, state);
            object.put("default_addr", "1");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), object.toString());

        new CallWebService(this, WebServiceConstants.BASE_URL + WebServiceConstants.ADD_ADDRESS, requestBody, new HashMap<String, String>(), UpdateProfileResponse.class, CallWebService.APIType.POST_WITH_BODY_NO_HEADER, null, null, true, new ResponseHandler() {
            @Override
            public void onSuccess(Object response) {
                UpdateProfileResponse profileResponse = (UpdateProfileResponse) response;
                Util.showSnack(profileResponse.getMessage(), binding.getRoot());
            }

            @Override
            public void onFailure(String message) {
                Util.showSnack(message, binding.getRoot());
            }

            @Override
            public void onError(String error) {
                Util.showSnack(error, binding.getRoot());
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean hasGps() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private boolean hasNetwork() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void gpsDialog() {
        RequestPermissionDialogLayoutBinding binding = RequestPermissionDialogLayoutBinding.inflate(getLayoutInflater());
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setView(binding.getRoot());
        binding.txtTitle.setText("Required Location Permission");
        binding.txtDelMsg.setText("This app needs the Location permission,please accept to use location functionality");
        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCompat.requestPermissions(AddAddressActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

               /* startActivity(new Intent(ACTION_LOCATION_SOURCE_SETTINGS));
                b = true*/
                ;
                dialogInterface.dismiss();
            }
        });
        dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.create();
        dialog.show();
    }


   /* private void getCurrentLocation() {
        Util.showDialog(context);
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            Log.d(TAG, "getCurrentLocation:===>");
        }
        fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null).addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Util.dismissDialog();
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        Log.d(TAG, "onComplete:" + addresses.get(0).getPostalCode());
                        binding.etPostalCode.setText(addresses.get(0).getPostalCode());
                        binding.etCity.setText(addresses.get(0).getLocality());
                        binding.etState.setText(addresses.get(0).getAdminArea());
                        String[] address=addresses.get(0).getAddressLine(0).split(",");
                        binding.etAddress1.setText(address[0]+","+address[1]+","+address[2]);
                        binding.etAddress2.setText(address[3]+","+address[4]);
                        Log.d(TAG, "onComplete:A"+addresses.get(0).getAddressLine(0));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "onComplete:====>fail");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Util.dismissDialog();
                Log.d(TAG, "onFailure:"+e.toString());
            }
        });

       */

    private void getCurrentLocation() {
        Util.showDialog(context);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Util.dismissDialog();
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        Log.d(TAG, "onComplete:" + addresses.get(0).getPostalCode());
                        binding.etPostalCode.setText(addresses.get(0).getPostalCode());
                        binding.etCity.setText(addresses.get(0).getLocality());
                        binding.etState.setText(addresses.get(0).getAdminArea());
                        String[] address = addresses.get(0).getAddressLine(0).split(",");
                        binding.etAddress1.setText(address[0] + "," + address[1] + "," + address[2]);
                        binding.etAddress2.setText(address[3] + "," + address[4]);
                        Log.d(TAG, "onComplete:A" + addresses.get(0).getAddressLine(0));
                        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    Util.showDialog(context);
                    firstTime = true;
                    Log.d(TAG, "onComplete:====>fail");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Util.dismissDialog();
            }
        });
    }

    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(AddAddressActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
    }

    public static GoogleApiClient googleApiClient;

    public void enableLoc(Activity context) {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                            Log.i("test", "-->");
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })

                    .addOnConnectionFailedListener(connectionResult -> Log.d("Location error", "Location error " + connectionResult.getErrorCode())).build();
            googleApiClient.connect();
        }


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(result1 -> {
            final Status status = result1.getStatus();
            if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {// Show the dialog by calling startResolutionForResult(),
                try {
                    status.startResolutionForResult(context, 210);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
        }

        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                if (location != null) {
                    Log.d(TAG, "onLocationResult:" + location.getLatitude());
                    Log.d(TAG, "onLocationResult:=>Long" + location.getLongitude());
                    if (firstTime) {
                        Util.dismissDialog();
                        getCurrentLocation();
                        firstTime = false;
                    }
                }

            }
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==101)
        {
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                getCurrentLocation();
            }
            else
            {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 210) {
            if (resultCode == RESULT_OK) {

                Log.d(TAG, "onActivityResult:===>");
                if (checkPermission())
                {
                    getCurrentLocation();
                }
                //fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
            }
        }
    }



    /*    @Override
    protected void onResume() {
        super.onResume();
        if (hasGps()) {
            Log.d(TAG, "onResume: ==>");
            getCurrentLocation();
           b=false;
        }
    }*/
}