package com.example.ariel.flows.locationsflow.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import com.example.ariel.R
import com.example.ariel.base.activitie.BaseFragmentActivity
import com.example.ariel.databinding.MapsActivityBinding
import com.example.ariel.flows.locationsflow.model.LocationModel
import com.example.ariel.notifications.NotificationHelper
import com.example.ariel.utils.orZero
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList
import java.util.TimerTask

import java.util.Timer

class MapActivity : BaseFragmentActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    private val myTimer = Timer()

    private lateinit var fusedLocClient: FusedLocationProviderClient

    val listOfLocations: MutableList<LocationModel> = ArrayList()

    private val binding by lazy {
        MapsActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupLocClient()
        bindMapFragment()
        launchTimer()
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun bindMapFragment() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
    }

    private fun setupLocClient() {
        fusedLocClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            requestLocPermissions()
        } else {
            fusedLocClient.lastLocation.addOnCompleteListener {
                val location = it.result
                val database: FirebaseDatabase = FirebaseDatabase.getInstance()
                val ref: DatabaseReference = database.getReference(FIREBASE_DB_NAME)
                if (location != null) {
                    val locationToSave =
                        LocationModel(longitude = location.longitude, latitude = location.latitude)
                    ref.push().setValue(locationToSave)
                    launchNotification()
                    getAllLocations()
                } else {
                    showError(getString(R.string.location_error_message))
                }
            }
        }
    }

    private fun requestLocPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        }
    }

    private fun getAllLocations() {
        val ref = FirebaseDatabase.getInstance().reference.child(FIREBASE_DB_NAME)
        ref.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listOfLocations.clear()
                    for (ds in dataSnapshot.children) {
                        val latitude = ds.child(LATITUDE).getValue(Double::class.java)
                        val longitude = ds.child(LONGITUDE).getValue(Double::class.java)
                        listOfLocations.add(LocationModel(latitude.orZero(), longitude.orZero()))
                    }
                    printAllMarks()
                }

                override fun onCancelled(databaseError: DatabaseError) = Unit
            })
    }

    private fun printAllMarks() {
        listOfLocations.map {
            getMark(it.latitude, it.longitude)
        }
    }

    private fun getMark(latitude: Double, longitude: Double): Marker? {
        val latLng = LatLng(latitude, longitude)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
        return googleMap.addMarker(MarkerOptions().position(latLng))
    }

    private fun launchTimer() {
        myTimer.schedule(object : TimerTask() {
            override fun run() {
                getCurrentLocation()
            }
        }, 0, FIVE_MINUTES)
    }

    override fun onDestroy() {
        super.onDestroy()
        myTimer.cancel()
    }

    private fun launchNotification() {
        NotificationHelper(this, getString(R.string.notification_success_message)).notification()
        val kh = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        kh.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    private companion object {
        const val REQUEST_LOCATION = 1
        const val FIVE_MINUTES = 10000L
        const val FIREBASE_DB_NAME = "location_test"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
    }
}