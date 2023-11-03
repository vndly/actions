import 'package:actions/main_state.dart';
import 'package:actions/sound.dart';
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
          child: Content(state),
        ),
      ),
    );
  }
}

class Content extends StatelessWidget {
  final MainState state;

  const Content(this.state);

  @override
  Widget build(BuildContext context) {
    return StateProvider<MainState>(
      state: state,
      builder: (context, state) {
        if (state.list.isEmpty) {
          return const Loading();
        } else {
          return Body(state);
        }
      },
    );
  }
}

class Loading extends StatelessWidget {
  const Loading();

  @override
  Widget build(BuildContext context) {
    return const Center(
      child: CircularProgressIndicator(),
    );
  }
}

class Body extends StatelessWidget {
  final MainState state;

  const Body(this.state);

  @override
  Widget build(BuildContext context) {
    return GridView.count(
      crossAxisCount: 2,
      mainAxisSpacing: 10,
      crossAxisSpacing: 10,
      childAspectRatio: 2,
      padding: const EdgeInsets.all(10),
      children: [
        for (final Sound sound in state.list)
          Button(
            sound: sound,
            state: state,
          ),
      ],
    );
  }
}

class Button extends StatelessWidget {
  final Sound sound;
  final MainState state;

  const Button({
    required this.sound,
    required this.state,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.blue,
      child: Material(
        color: Colors.transparent,
        child: InkWell(
          onTap: () => state.onPlaySound(sound.url),
          child: Center(
            child: Text(
              sound.name,
              style: const TextStyle(color: Colors.white),
            ),
          ),
        ),
      ),
    );
  }
}
