package logic.audio

import com.sun.speech.freetts.{FreeTTS, Voice, VoiceManager}

object Freetts_syn {

  def syn(text: String, path: String) {

    val voiceName: String = "kevin16"

    val voiceManager: VoiceManager = VoiceManager.getInstance();
    val helloVoice: Voice = voiceManager.getVoice(voiceName);

    if (helloVoice == null) {
      System.err.println("Cannot find a voice named " + voiceName
        + ".  Please specify a different voice.");
      System.exit(1);
    }

    val freeTTS: FreeTTS = new FreeTTS(helloVoice);
    freeTTS.setAudioFile(path);

    freeTTS.startup();

    helloVoice.allocate();

    helloVoice.speak(text)

    helloVoice.deallocate();

    freeTTS.shutdown();


  }
}

