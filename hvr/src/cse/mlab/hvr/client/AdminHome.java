package cse.mlab.hvr.client;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.LinkedGroupItem;
import org.gwtbootstrap3.client.ui.Panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.shared.study.StudyOverview;

public class AdminHome extends Composite{
	
	
	private ArrayList<LinkedGroupItem> activeStudiesLink;
	private ArrayList<LinkedGroupItem> archiveStudiesLink;
	
	@UiField
	Label testlabel;
	
	@UiField
	Panel activeStudiesPanel, archiveStudiesPanel;
	
	@UiField
	HTMLPanel adminContentPanel;

	private static AdminHomeUiBinder uiBinder = GWT.create(AdminHomeUiBinder.class);

	interface AdminHomeUiBinder extends UiBinder<Widget, AdminHome> {
	}

	public AdminHome() {
		initWidget(uiBinder.createAndBindUi(this));
		
		
		
		//insert some dummy data
		StudyOverview st1 = new StudyOverview("1", "Parkinson Study, Nov 2016", "", "", "", "", "", "", "", "", true, true, "", 3, "1", "1");
		StudyOverview st2 = new StudyOverview("2", "Concussion Study, Aug 2016", "", "", "", "", "", "", "", "", true, true, "", 3, "2", "2");
		
		
		StudyOverview st3 = new StudyOverview("3", "Test Study, Jan 2016", "", "", "", "", "", "", "", "", false, true, "", 3, "3", "3");
		
		ArrayList<StudyOverview> studiesList = new ArrayList<>();
		studiesList.add(st1);
		studiesList.add(st2);
		studiesList.add(st3);
			
		activeStudiesLink = new ArrayList<>();
		archiveStudiesLink = new ArrayList<>();
		
		for(int i=0;i<studiesList.size();i++){
			StudyOverview temp = studiesList.get(i);
			LinkedGroupItem linkedItem = new LinkedGroupItem();
			linkedItem.setText(temp.getName());
			linkedItem.setId(temp.getId());
			linkedItem.setColor("BLUE");
			if(temp.isActive()){
				activeStudiesLink.add(linkedItem);
			} else {
				archiveStudiesLink.add(linkedItem);
			}
		}
		
		
		for(int i=0;i<activeStudiesLink.size();i++){
			activeStudiesLink.get(i).addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					testlabel.setText("id of the test : " + ((LinkedGroupItem) event.getSource()).getId());
					History.newItem("admin/" + ((LinkedGroupItem) event.getSource()).getId());
				}
			});
			activeStudiesPanel.add(activeStudiesLink.get(i));
		}
		
		for(int i=0;i<archiveStudiesLink.size();i++){
			archiveStudiesPanel.add(archiveStudiesLink.get(i));
		}		
	}
	
	public void loadStudyManagement(StudyOverview studyOverview){
		adminContentPanel.clear();
			
		adminContentPanel.add(new StudyAdministration());
	}


}
