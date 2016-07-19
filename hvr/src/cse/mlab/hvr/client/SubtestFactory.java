package cse.mlab.hvr.client;

import java.util.ArrayList;

import cse.mlab.hvr.client.fragments.AudioBasedFragment;
import cse.mlab.hvr.client.fragments.CommonInstructionFragment;
import cse.mlab.hvr.client.fragments.FragmentFactory;
import cse.mlab.hvr.shared.speechtest.SubTest;

public class SubtestFactory {
	private static SubtestFactory instance = null;
	
	private SubtestFactory(){
		
	}
	
	public static SubtestFactory getInstance(){
		if(instance == null){
			instance = new SubtestFactory();
		}
		return instance;
	}
	
	public AudioBasedFragment[] getSubtest(SubTest subTest){
		ArrayList<AudioBasedFragment> audioBasedFragments = new ArrayList<>();
		if(isEmpty(subTest.getCommonInstructionText()) && isEmpty(subTest.getCommonInstrcutionAudioFileName())){
			//do nothing
		} else {
			CommonInstructionFragment commonInstructionFragment = new CommonInstructionFragment(subTest.getCommonInstrcutionAudioFileName(), subTest.getCommonInstructionText());
			audioBasedFragments.add(commonInstructionFragment);
		}
		
		if(subTest.getSubtestFragments() != null){
			for(int i=0;i<subTest.getSubtestFragments().size();i++){
				AudioBasedFragment fragment =  FragmentFactory.getInstance().getPlayerFragment(subTest.getSubtestFragments().get(i));
				audioBasedFragments.add(fragment);
			}
		}
		
		return audioBasedFragments.toArray(new AudioBasedFragment[audioBasedFragments.size()]);
	}
	
	private static boolean isEmpty(String text){
		if(text == null || text.isEmpty()){
			return true;
		}
		return false;
	}

}

