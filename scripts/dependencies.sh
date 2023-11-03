#!/usr/bin/env bash

set -e

flutter pub upgrade
flutter pub pub run dapublock:dapublock.dart .
flutter pub pub run daunused:daunused.dart . lib/main.dart .dart_tool/flutter_build/dart_plugin_registrant.dart lib/generated_plugin_registrant.dart
flutter pub outdated
flutter pub upgrade --major-versions
dart format lib