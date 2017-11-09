package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.ImageAnchor;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.html.Br;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class MedicationUnit extends Composite {
	
	@UiField
	HTMLPanel unitPanel;
	
	TextBox nameBox, doseTextBox, frequencyTextBox;
	Label nameRequiredLabel;

	private static MedicationUnitUiBinder uiBinder = GWT.create(MedicationUnitUiBinder.class);

	interface MedicationUnitUiBinder extends UiBinder<Widget, MedicationUnit> {
	}
	
	public MedicationUnit(boolean isClosable, String name, String dosage, String frequency) {
		initWidget(uiBinder.createAndBindUi(this));
		createUI(isClosable, name, dosage, frequency);
	}

	public MedicationUnit(boolean isClosable) {
		initWidget(uiBinder.createAndBindUi(this));
		createUI(isClosable, "", "", "");
	}
	
	private void createUI(boolean isClosable, String name, String dosage, String frequency){
		unitPanel.addStyleName("panel_border");
		
		Row row1 = new Row();
		unitPanel.add(row1);
		Column c1 = new Column(ColumnSize.MD_11);
		Column c2 = new Column(ColumnSize.MD_1);
		row1.add(c1);
		row1.add(c2);
		//row1.addStyleName("col-lg-offset-11");
		
		nameRequiredLabel = new Label("Medication Name Required");
		nameRequiredLabel.setVisible(false);
		nameRequiredLabel.setStyleName("labelError");
		c1.add(nameRequiredLabel);
		if(isClosable){
			ImageAnchor closeAnc = new ImageAnchor();
			closeAnc.setIcon(IconType.CLOSE);
			c2.add(closeAnc);
			
			closeAnc.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					unitPanel.getParent().getParent().removeFromParent();
				}
			});
		}
		row1.add(new Br());
		
		nameBox = new TextBox();
		Column c21 = new Column(ColumnSize.MD_8);
		if(name.isEmpty()){
			nameBox.setPlaceholder("Medication Name");
		}else {
			nameBox.setText(name);
		}
		
		nameBox.setMaxLength(50);
		Row row2 = new Row();
		nameBox.addStyleName("col-lg-offset-1");
		c21.add(nameBox);
		row2.add(c21);
		unitPanel.add(row2);
		unitPanel.add(new Br());
		
		Row row4 = new Row();
		Column c41 = new Column(ColumnSize.MD_6);
		Column c42 = new Column(ColumnSize.MD_5);
		doseTextBox = new TextBox();
		if(dosage.isEmpty()){
			doseTextBox.setPlaceholder("Dosage, ex: 1 tablet");
		}else {
			doseTextBox.setText(dosage);
		}
		
		doseTextBox.setMaxLength(40);
		//doseTextBox.addStyleName("col-lg-offset-1");
		c42.add(doseTextBox);
		frequencyTextBox = new TextBox();
		frequencyTextBox.addStyleName("col-lg-offset-1");
		if(frequency.isEmpty()){
			frequencyTextBox.setPlaceholder("How often taken, ex: 3 times daily");
		}else {
			frequencyTextBox.setText(frequency);
		}
		
		frequencyTextBox.setMaxLength(40);
		c41.add(frequencyTextBox);
		row4.add(c41);
		row4.add(c42);
		
		unitPanel.add(row4);
		unitPanel.add(new Br());
		
	}
	
	public String getName(){
		if(nameBox.getText() == null || nameBox.getText().isEmpty()){
			return "";
		} 
		return nameBox.getText().trim();
	}
	
	public String getFrequency(){
		if(frequencyTextBox.getText() == null || frequencyTextBox.getText().isEmpty()){
			return "";
		} 
		return frequencyTextBox.getText().trim();

	}
	
	public String getDosage(){
		if(doseTextBox.getText() == null || doseTextBox.getText().isEmpty()){
			return "";
		} 
		return doseTextBox.getText().trim();

	}
	
	public boolean validate(){
		if(nameBox.getText() == null || nameBox.getText().isEmpty()){
			nameRequiredLabel.setVisible(true);
			return false;
		} 
		nameRequiredLabel.setVisible(false);
		return true;
	}

}
