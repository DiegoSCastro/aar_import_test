// ignore_for_file: avoid_print

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  static const platform = MethodChannel('com.example.yourapp/native');
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter-Native Communication'),
        ),
        body: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              ElevatedButton(
                onPressed: () async {
                  try {
                    final String result =
                        await platform.invokeMethod('getConfig');
                    print(result);
                  } on PlatformException catch (e) {
                    print("Failed to get config: '${e.message}'.");
                  }
                },
                child: const Text('Get Config from Native'),
              ),
              ElevatedButton(
                onPressed: () async {
                  try {
                    final String result =
                        await platform.invokeMethod('getPlatformVersion');
                    print(result);
                  } on PlatformException catch (e) {
                    print("Failed to get config: '${e.message}'.");
                  }
                },
                child: const Text('get Platform Version'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
