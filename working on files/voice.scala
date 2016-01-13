package com.sun.speech.freetts;



object Freetts_syn {

  def listAllVoices() {
    System.out.println();
    var  i : Int =0
    System.out.println("All voices available:");
    val voiceManager: VoiceManager = VoiceManager.getInstance();
    val voices: Array[Voice] = voiceManager.getVoices();
    for( i <- 0 until voices.length) {
      System.out.println("    " + voices(i).getName() + " ("
        + voices(i).getDomain() + " domain)");
    }
  }

  def syn (args: Array[String]) {
    //System.out.println(System.getenv("user.home"))
    //System.setProperty("mbrola.base", "/home/yasmine/Téléchargements/freetts-1.2_new/mbrola");// voice=vm.getVoice("mbrola_us1");
    val message: String = "Hello world! This is a test program";

    listAllVoices();

    var voiceName: String = "kevin16"
    if (args.length > 0) {
      voiceName = args(0)
    }


    System.out.println();
    System.out.println("Using voice: " + voiceName);

    val voiceManager: VoiceManager = VoiceManager.getInstance();
    val helloVoice: Voice = voiceManager.getVoice(voiceName);

    if (helloVoice == null) {
      System.err.println("Cannot find a voice named " + voiceName
        + ".  Please specify a different voice.");
      System.exit(1);
    }

    val freeTTS: FreeTTS = new FreeTTS(helloVoice);
    freeTTS.setAudioFile("/home/yasmine/Documents/script_voic_.wav");

    freeTTS.startup();

    helloVoice.allocate();
    System.out.println("Using rate: " + helloVoice.getRate());

   val fileLines = io.Source.fromFile("src/main/ressources/script.txt").getLines.toList
    fileLines.foreach(helloVoice.speak)
    
   helloVoice.deallocate();

    freeTTS.shutdown();


  }
}

