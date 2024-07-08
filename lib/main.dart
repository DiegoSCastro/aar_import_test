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
          title: Text('Flutter-Native Communication'),
        ),
        body: Center(
          child: ElevatedButton(
            onPressed:
                () async {
              try {
                final String result = await platform.invokeMethod('getConfig');
                print(result);
              } on PlatformException catch (e) {
                print("Failed to get config: '${e.message}'.");
              }
            },
            child: Text('Get Config from Native'),
          ),
        ),
      ),
    );
  }
}
