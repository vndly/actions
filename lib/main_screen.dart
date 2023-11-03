import 'package:actions/main_state.dart';
import 'package:dafluta/dafluta.dart';
import 'package:flutter/material.dart';

class MainScreen extends StatelessWidget {
  final MainState state;

  const MainScreen._(this.state);

  factory MainScreen.instance() => MainScreen._(MainState());

  @override
  Widget build(BuildContext context) {
    return DarkStatusBar(
      child: Scaffold(
        body: SafeArea(
          child: Buttons(state),
        ),
      ),
    );
  }
}

class Buttons extends StatelessWidget {
  final MainState state;

  const Buttons(this.state);

  @override
  Widget build(BuildContext context) {
    return const SingleChildScrollView(
      child: Column(
        children: [],
      ),
    );
  }
}
