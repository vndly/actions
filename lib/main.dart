import 'dart:async';
import 'package:actions/actions.dart';
import 'package:flutter/material.dart' hide Actions;
import 'package:flutter/services.dart';

Future main() async {
  WidgetsFlutterBinding.ensureInitialized();

  await SystemChrome.setPreferredOrientations([
    DeviceOrientation.portraitUp,
    DeviceOrientation.portraitDown,
  ]);
  runApp(const Actions());
}
