package jp.ceed.android.mylapslogger.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.OnTokenCanceledListener
import java.io.IOException

class LocationRepository(val context: Context) {

    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)


    fun getLocation(callback: (Result<Location>) -> Unit) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY, LocationCancellationToken())
            .addOnSuccessListener { location: Location? ->
                if (location == null) {
                    callback(Result.failure(IOException("UnKnown")))
                } else {
                    callback(Result.success(location))
                }
            }
    }

    /**
     *
     */
    class LocationCancellationToken() : CancellationToken() {

        override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
            return this
        }

        override fun isCancellationRequested(): Boolean {
            return false
        }

    }

}