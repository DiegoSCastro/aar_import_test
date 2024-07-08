package com.diegocastro.aar_import_test

import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import com.mercadolibre.android.point_integration_sdk.nativesdk.configurable.MPConfigBuilder

class MainActivity: FlutterActivity() {
    private val CHANNEL = "com.example.yourapp/native"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                call, result ->
            if (call.method == "getConfig") {
                val config = getConfig()
                if (config != null) {
                    result.success(config)
                } else {
                    result.error("UNAVAILABLE", "Config not available.", null)
                }
            } else {
                result.notImplemented()
            }
        }
    }

    private fun getConfig(): String {
        val application = applicationContext as android.app.Application
        val configBuilder = MPConfigBuilder(application, "193213529837179")
        val config = configBuilder
            .withBitmapPrinterConfig()
            .withBluetoothConfig()
            .withCameraScanner()
            .build()
        return config.toString()
    }
}
