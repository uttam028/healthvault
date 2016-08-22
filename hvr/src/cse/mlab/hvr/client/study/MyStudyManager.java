package cse.mlab.hvr.client.study;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.Column;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.shared.study.MyStudyDataModel;

public class MyStudyManager extends Composite {

	@UiField
	Column studiesColumn;
	
	private ArrayList<MyStudyDataModel> models;
	private static MyStudyManagerUiBinder uiBinder = GWT
			.create(MyStudyManagerUiBinder.class);

	interface MyStudyManagerUiBinder extends UiBinder<Widget, MyStudyManager> {
	}

	public MyStudyManager() {
		initWidget(uiBinder.createAndBindUi(this));
		this.models = new ArrayList<>();
	}
	
	
	public MyStudyManager(ArrayList<MyStudyDataModel> models) {
		initWidget(uiBinder.createAndBindUi(this));
		this.models = models;
		
		for(MyStudyDataModel model : models){
			studiesColumn.add(new MyStudy(model));
		}
	}
	
	public void addStudiesToManager(ArrayList<MyStudyDataModel> models){
		for(MyStudyDataModel model : models){
			studiesColumn.add(new MyStudy(model));
			this.models.add(model);
		}		
	}
	
	public void addStudyToDashboard(MyStudyDataModel model){
		this.models.add(0, model);
		studiesColumn.insert(new MyStudy(model), 0);
	}

	public void updateStudyDataToDashBoard(String studyId){
		for(int i=0;i<studiesColumn.getWidgetCount();i++){
			try {
				if(studiesColumn.getWidget(i) instanceof MyStudy){
					MyStudy temp = (MyStudy) studiesColumn.getWidget(i);
					if(temp.getStudyModel().getStudyOverview().getId().equals(studyId)){
						temp.updateDataModel(studyId);
					}
				}				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
