package com.example.livenesskotlinsample

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import nodeflux.sdk.liveness.Liveness
import nodeflux.sdk.liveness.NodefluxLivenessSDKOptions
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val CAMERA_PERMISSION_CODE: Int = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sdkOptions = NodefluxLivenessSDKOptions()

        sdkOptions.setAccessKey(resources.getString(R.string.access_key))
        sdkOptions.setSecretKey(resources.getString(R.string.secret_key))
        sdkOptions.setThreshold(0.7)
        sdkOptions.setActiveLivenessFlag(true)
        sdkOptions.setTimeoutThreshold(30000)
        sdkOptions.setGestureToleranceThreshold(10000)

        val sdkHandler = object : Liveness.LivenessCallback{
            override fun onSuccess(
                isLive: Boolean,
                image: String?,
                score: Double,
                msg: String?,
                additionalInformation: JSONObject?
            ) {
                Toast.makeText(applicationContext, score.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onSuccessWithSubmissionToken(p0: String?, p1: String?) {
                Toast.makeText(applicationContext, "GOT ERROR", Toast.LENGTH_LONG).show()
            }

            override fun onError(p0: String?, p1: JSONObject?) {
                TODO("Not yet implemented")
            }
        }

        Liveness.setUpListener(sdkOptions, sdkHandler)
    }

    private fun isCameraPermissionGranted(): Boolean {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            return false
        }

        return true
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Log.i("PERMISSION", "Permission has been denied by user")
        } else {
            Log.i("PERMISSION", "Permission has been granted by user")
        }
    }

    fun onStartButtonClicked(view: View) {
        if (!isCameraPermissionGranted()) {
            requestCameraPermission()
        } else {
            startActivity(Intent(this, Liveness::class.java))
        }
    }
}