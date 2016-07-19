package cse.mlab.hvr.client;

import java.util.ArrayList;

import cse.mlab.hvr.shared.speechtest.SpeechTestMetadata;

public class CustomPlayerCreator {
	private static CustomPlayerCreator instance = null;

	private CustomPlayerCreator() {
		// TODO Auto-generated constructor stub
	}

	public static CustomPlayerCreator getInstance() {
		if (instance == null) {
			instance = new CustomPlayerCreator();
		}
		return instance;
	}

	public AudioBasedCustomPlayer[] getAudioBasedCustomPlayer(SpeechTestMetadata metadata) {
		ArrayList<AudioBasedCustomPlayer> customPlayers = new ArrayList<>();
		for(int i=0;i<metadata.getSubTests().size();i++){
			customPlayers.add(new AudioBasedCustomPlayer("Test "+i, SubtestFactory.getInstance().getSubtest(metadata.getSubTests().get(i))));
		}
		return customPlayers.toArray(new AudioBasedCustomPlayer[customPlayers.size()]);
	}
}
