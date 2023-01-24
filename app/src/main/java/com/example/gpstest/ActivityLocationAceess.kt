package com.example.gpstest

import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener


class ActivityLocationAceess : AppCompatActivity()  {

    var mLocationManager:LocationManager? = null
    private var fusedLocationProvider: FusedLocationProviderClient? = null
    var locationViewModel:LocationViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_access)
        locationViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(LocationViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        if(mLocationManager == null){
            mLocationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        }
        if (!(mLocationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false)) {
            showAlertLocation()
        }
        setLocationListner()
    }

    private fun setLocationListner() {
        if (locationViewModel?.checkLocationPermission(this) ?: false) {
            fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)

            // for getting the current location update after every 2 seconds with high accuracy
            val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000)
                .setWaitForAccurateLocation(true)
                .setMinUpdateIntervalMillis(2000)
                .setMaxUpdateDelayMillis(2000)
                .build()

            fusedLocationProvider?.requestLocationUpdates(
                locationRequest,
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)
                        for (location in locationResult.locations) {
                            Log.i("Location found" , "lat: ".plus(location.latitude.toString()) .plus(" lng : ").plus(location.longitude.toString()).plus(" accuracy:  ").plus(location.accuracy))
//                            fusedLocationProvider?.removeLocationUpdates(this)
                        }
                        // Things don't end here
                        // You may also update the location on your web app
                    }
                },
                Looper.myLooper()
            )
        }
    }


    private fun showAlertLocation() {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("Your location settings is set to Off, Please enable location to use this application")
        dialog.setPositiveButton("Settings") { _, _ ->
            val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(myIntent)
        }
        dialog.setNegativeButton("Cancel") { _, _ ->
            //finish()
            //Move to next screen
        }
        dialog.setCancelable(false)
        dialog.show()
    }
}