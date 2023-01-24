package com.example.gpstest

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat

import androidx.lifecycle.ViewModel

class LocationViewModel: ViewModel() {

    fun checkLocationPermission(context: Context):Boolean{
        when {
            ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                return true
            }
            ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                return false
            }
            else -> {
                // You can directly ask for the permission.
//                requestPermissions(context,arrayOf(Manifest.permission.REQUESTED_PERMISSION),REQUEST_CODE)
                return false
            }
        }
    }

     fun locationEnabled(context: Context) : Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}