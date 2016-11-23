package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.TextArea;
import org.gwtbootstrap3.client.ui.TextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class MedicalInfoPage extends Composite {
	
	@UiField
	Button updateButton, cancelButton;
	@UiField
	TextBox textboxMedicName;
	
	@UiField
	TextArea textareaReason;
	@UiField
	Form medicForm;
	@UiField
	HTMLPanel resultPanel;

	private static MedicalInfoPageUiBinder uiBinder = GWT
			.create(MedicalInfoPageUiBinder.class);

	interface MedicalInfoPageUiBinder extends UiBinder<Widget, MedicalInfoPage> {
	}

	public MedicalInfoPage() {
		initWidget(uiBinder.createAndBindUi(this));
		cancelButton.setVisible(false);
		textboxMedicName.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				// TODO Auto-generated method stub
				cancelButton.setVisible(true);
			}
		});
		textareaReason.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				// TODO Auto-generated method stub
				cancelButton.setVisible(true);
			}
		});
	}

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		medicForm.reset();
	}
	private void resetForm(){
		medicForm.reset();
	}
	
	@UiHandler("cancelButton")
	void cancelMedicationUpdate(ClickEvent event){
		resetForm();
		cancelButton.setVisible(false);
	}

	@UiHandler("updateButton")
	void updateMedication(ClickEvent event){
		if(medicForm.validate()){
			medicForm.reset();
			cancelButton.setVisible(false);
			resultPanel.add(new MessagePanel("Your information has been updated", "", false, "", ""));
			Timer timer = new Timer() {					
				@Override
				public void run() {
					// TODO Auto-generated method stub
					resultPanel.clear();
				}
			};
			timer.schedule(3 * 1000);
		}
	}
	
}
