package cse.mlab.hvr.client;

import java.util.ArrayList;

import cse.mlab.hvr.shared.study.SpeechTest;

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

	public AudioBasedCustomPlayerHtml5[] getAudioBasedCustomPlayer(SpeechTest metadata) {
		if(metadata.getSubTests()==null || metadata.getSubTests().size()==0){
			return null;
		} else {
//			ArrayList<AudioBasedCustomPlayer> customPlayers = new ArrayList<>();
//			for(int i=0;i<metadata.getSubTests().size();i++){
//				customPlayers.add(new AudioBasedCustomPlayer("Step "+ (i+1) + "     ( Out of " + metadata.getSubTests().size() +" )",
//						metadata.getSubTests().get(i).getSubtestId(),
//						SubtestFactory.getInstance().getSubtest(metadata.getSubTests().get(i))));
//			}
//			return customPlayers.toArray(new AudioBasedCustomPlayer[customPlayers.size()]);			
			ArrayList<AudioBasedCustomPlayerHtml5> customPlayers = new ArrayList<>();
			for(int i=0;i<metadata.getSubTests().size();i++){
				customPlayers.add(new AudioBasedCustomPlayerHtml5("Step "+ (i+1) + "     ( Out of " + metadata.getSubTests().size() +" )",
						metadata.getSubTests().get(i).getSubtestId(),
						SubtestFactory.getInstance().getSubtest(metadata.getSubTests().get(i))));
			}
			return customPlayers.toArray(new AudioBasedCustomPlayerHtml5[customPlayers.size()]);			
		}
	}
}
