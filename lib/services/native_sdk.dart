import 'dart:nativewrappers/_internal/vm/lib/developer.dart';

import 'package:flutter/services.dart';

class NativeSdk {
  static const MethodChannel _channel = MethodChannel('flutter_native_sdk');

  static Future<void> withBluetoothConfig() async {
    try {
      await _channel.invokeMethod('withBluetoothConfig');
    } on PlatformException catch (e) {
      log("Failed to call method: ${e.message}");
    }
  }
}
