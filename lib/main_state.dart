import 'package:dafluta/dafluta.dart';
import 'package:just_audio/just_audio.dart';

class MainState extends BaseState {
  Future onPlaySound(String url) async {
    final player = AudioPlayer(); // Create a player
    await player.setUrl(url);
    player.play();
  }
}
