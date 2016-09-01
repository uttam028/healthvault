package cse.mlab.hvr.client;

import java.util.ArrayList;

import sun.net.www.MeteredStream;
import cse.mlab.hvr.shared.study.SpeechTest;
import cse.mlab.hvr.shared.study.SpeechTestMetadata;

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

	public AudioBasedCustomPlayer[] getAudioBasedCustomPlayer(SpeechTest metadata) {
		if(metadata.getSubTests()==null || metadata.getSubTests().size()==0){
			return null;
		} else {
			ArrayList<AudioBasedCustomPlayer> customPlayers = new ArrayList<>();
			for(int i=0;i<metadata.getSubTests().size();i++){
				customPlayers.add(new AudioBasedCustomPlayer("Test "+ i,
						metadata.getSubTests().get(i).getSubtestId(),
						SubtestFactory.getInstance().getSubtest(metadata.getSubTests().get(i))));
			}
			return customPlayers.toArray(new AudioBasedCustomPlayer[customPlayers.size()]);			
		}
	}
}
