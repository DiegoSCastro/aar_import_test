// ignore_for_file: avoid_print

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  // This widget is the root of your application.

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  static const platform = MethodChannel('com.example.yourapp/native');
  String version = '';

  Future<void> getVersion() async {
    try {
      final String result = await platform.invokeMethod('getPlatformVersion');

      setState(() {
        version = result;
      });
      print(result);
    } on PlatformException catch (e) {
      print("Failed to get config: '${e.message}'.");
    }
  }

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
              const SizedBox(height: 8),
              ElevatedButton(
                onPressed: () async {
                  try {
                    final String result =
                        await platform.invokeMethod('printTest');
                    print(result);
                  } on PlatformException catch (e) {
                    print("Failed to get config: '${e.message}'.");
                  }
                },
                child: const Text('Print Test'),
              ),
              const SizedBox(height: 8),
              ElevatedButton(
                onPressed: getVersion,
                child: const Text('Get Platform Version'),
              ),
              Text(version)
            ],
          ),
        ),
      ),
    );
  }
}
