package com.diegocastro.aar_import_test

import android.util.Log
//import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
//import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import com.mercadolibre.android.point_integration_sdk.nativesdk.MPManager
import com.mercadolibre.android.point_integration_sdk.nativesdk.configurable.MPConfigBuilder
//import android.content.pm.ApplicationInfo;
//import android.content.pm.PackageManager;
import com.mercadolibre.android.point_integration_sdk.nativesdk.message.utils.doIfError
import com.mercadolibre.android.point_integration_sdk.nativesdk.message.utils.doIfSuccess
//import com.mercadolibre.android.aar_import_test.app.R
// import com.mercadolibre.android.point_mainapp_demo.app.databinding.PointMainappDemoAppActivityPrinterBitmapBinding
// import com.mercadolibre.android.point_mainapp_demo.app.util.gone
// import com.mercadolibre.android.point_mainapp_demo.app.util.toast
// import com.mercadolibre.android.point_mainapp_demo.app.util.visible
import android.graphics.BitmapFactory
import com.diegocastro.aar_import_test.R


class MainActivity: FlutterActivity() {

    private val CHANNEL = "com.example.yourapp/native"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            call, result ->
            when (call.method) {
                "getConfig" -> initializeMPManager()
                "printTest" -> printImageBitmap()
                
                
                "getPlatformVersion" -> {
                    result.success("Android ${android.os.Build.VERSION.RELEASE}")
                }
                else -> {
                    result.notImplemented()
                }
            }
        }
    }

    private fun initializeMPManager() {
        val application = applicationContext as android.app.Application
        val config = MPConfigBuilder(application, "193213529837179")
            .withBluetoothConfig()
            .withBluetoothUIConfig()
            .withBitmapPrinterConfig()
            .build()
        MPManager.initialize(application, config)
    }   

    private fun printImageBitmap() {
        initializeMPManager()
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable
            .point_mainapp_demo_app_ic_datafono)
    
        MPManager.bitmapPrinter.print(bitmap) { response ->
            response
                .doIfSuccess { printResult ->
                    Log.d("MiniAppPlugin", "Bitmap printed successfully: $printResult")
                    // result.success("Bitmap printed successfully: $printResult")
                }
                .doIfError { error ->
                    Log.e("MiniAppPlugin", "Failed to print bitmap: ${error.message}")
                    // result.error("PRINT_ERROR", "Failed to print bitmap: ${error.message}", null)
                }
        }
    }
}
