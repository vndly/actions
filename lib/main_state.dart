import 'package:dafluta/dafluta.dart';
import 'package:just_audio/just_audio.dart';

class MainState extends BaseState {
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
