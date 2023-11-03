import 'dart:convert';
import 'package:actions/sound.dart';
import 'package:dafluta/dafluta.dart';
import 'package:http/http.dart' as http;
import 'package:http/http.dart';
import 'package:just_audio/just_audio.dart';

class MainState extends BaseState {
  final List<Sound> list = [];

  @override
  Future onLoad() async {
    const String url =
        'https://script.google.com/macros/s/AKfycbxL2Aec3uLWKgDOTVnScUdi_C2rnXHQ0gqYruHeJ-J7j-UIouGydr6V82M1K2nD-896vA/exec';

    final Response response = await http.get(Uri.parse(url));
    final List<dynamic> json = jsonDecode(response.body) as List<dynamic>;

    for (final dynamic entry in json) {
      list.add(Sound(
        name: entry.keys.first,
        url: entry.values.first,
      ));
    }

    notify();
  }

  Future onPlaySound(String url) async {
    try {
      final AudioPlayer player = AudioPlayer(
        audioLoadConfiguration: AudioLoadConfiguration(
          androidLoadControl: AndroidLoadControl(
            prioritizeTimeOverSizeThresholds: true,
          ),
        ),
      );
      await player.setAudioSource(AudioSource.uri(Uri.parse(url)));
      //await player.setUrl(url);
      await player.play();
    } catch (e) {
      print(e);
    }
  }
}
