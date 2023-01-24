package com.example.gpstest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    var locationViewModel:LocationViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()).get(LocationViewModel::class.java)
        checkPermissionResult()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            Constants.LOCATION_PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    if (locationViewModel?.checkLocationPermission(this) ?: false){
                        //get location
                        Log.i("permission result" , "granted..")
                        checkPermissionResult()
                    }else{
                        //manual location
                        Log.i("permission result" , "not granted..")
                    }
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the feature requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.

                    //manual location
                    Log.i("permission result" , "not granted..")
                }
                return
            }
            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    fun checkPermissionResult(){
        if(!(locationViewModel?.checkLocationPermission(this) ?: false)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), Constants.LOCATION_PERMISSION_REQUEST_CODE
                )
            }else {
                //Manual location page
                Log.i("permission" , "Not granted..")
            }
        }else{
            Log.i("permission" , "granted..")
            if(locationViewModel?.locationEnabled(this) ?: false){
                Log.i("GPS Status" , "enabled..")
                startActivity(Intent(this,ActivityLocationAceess::class.java))
            }else{
                Log.i("GPS Status" , "disable..")
                startActivity(Intent(this,ActivityLocationAceess::class.java))
            }
        }
    }


}