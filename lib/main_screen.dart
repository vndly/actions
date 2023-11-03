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
    return SingleChildScrollView(
      child: Column(
        children: [
          Row(
            children: [
              const HBox(5),
              Button(
                name: 'Button 1',
                url:
                    'https://cdn.freesound.org/previews/90/90137_1430216-lq.mp3',
                state: state,
              ),
              Button(
                name: 'Button 2',
                url:
                    'https://cdn.freesound.org/previews/340/340955_5858296-lq.mp3',
                state: state,
              ),
              const HBox(5),
            ],
          ),
          Row(
            children: [
              const HBox(5),
              Button(
                name: 'Button 3',
                url:
                    'https://cdn.freesound.org/previews/323/323077_5514134-lq.mp3',
                state: state,
              ),
              Button(
                name: 'Button 4',
                url:
                    'https://cdn.freesound.org/previews/395/395822_7509543-lq.mp3',
                state: state,
              ),
              const HBox(5),
            ],
          )
        ],
      ),
    );
  }
}

class Button extends StatelessWidget {
  final String name;
  final String url;
  final MainState state;

  const Button({
    required this.name,
    required this.url,
    required this.state,
  });

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: Container(
        color: Colors.blue,
        margin: const EdgeInsets.all(10),
        height: 60,
        child: Material(
          color: Colors.transparent,
          child: InkWell(
            onTap: () => state.onPlaySound(url),
            child: Center(
              child: Text(
                name,
                style: const TextStyle(color: Colors.white),
              ),
            ),
          ),
        ),
      ),
    );
  }
}
