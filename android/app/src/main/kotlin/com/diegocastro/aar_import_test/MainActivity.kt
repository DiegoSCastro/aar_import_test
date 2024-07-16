package com.diegocastro.aar_import_test

import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import com.mercadolibre.android.point_integration_sdk.nativesdk.MPManager
import com.mercadolibre.android.point_integration_sdk.nativesdk.configurable.MPConfigBuilder

class MainActivity: FlutterActivity() {

    private val CHANNEL = "com.example.yourapp/native"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            call, result ->
            when (call.method) {
                "getConfig" -> {
                    val success = getConfig()
                    if (success) {
                        result.success("Config initialized successfully")
                    } else {
                        result.error("UNAVAILABLE", "Config initialization failed.", null)
                    }
                }
                "getPlatformVersion" -> {
                    result.success("Android ${android.os.Build.VERSION.RELEASE}")
                }
                else -> {
                    result.notImplemented()
                }
            }
        }
    }

    companion object {
        private const val DEMO_APP_CLIENT_ID = "193213529837179"
    }

    private fun getConfig(): Boolean {
        return try {
            val application = applicationContext as android.app.Application
            val configBuilder = MPConfigBuilder(application, DEMO_APP_CLIENT_ID)
            val config = configBuilder
                .withBitmapPrinterConfig()
                .withBluetoothConfig()
                .withCameraScanner()
                .build()
            
            // MPManager.initialize(this, config)
            true
        } catch (e: Exception) {
            false
        }
    }
}
