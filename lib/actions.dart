import 'package:actions/main_screen.dart';
import 'package:flutter/material.dart';

class Actions extends StatelessWidget {
  const Actions();

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Actions',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primaryColor: Colors.blue,
        primarySwatch: Colors.blue,
        scaffoldBackgroundColor: Colors.white,
      ),
      home: MainScreen.instance(),
    );
  }
}
