package cse.mlab.hvr.client;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.InlineHelpBlock;
import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.form.error.BasicEditorError;
import org.gwtbootstrap3.client.ui.form.validator.Validator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.shared.Md5Utils;
import cse.mlab.hvr.shared.Response;

public class AccountInfoPage extends Composite {
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	@UiField
	HTMLPanel resultPanel;
	
	@UiField
	Form changePasswordForm;
	
	@UiField
	FormGroup formgroupConfirmPass;
	
	@UiField
	InlineHelpBlock inlineHelpConfirmPass;
	
	@UiField
	Button updateButton, cancelButton;
	
	@UiField
	Input textboxOldPass, textboxNewPass, textboxConfirmPass;
	
	
	private static AccountInfoPageUiBinder uiBinder = GWT
			.create(AccountInfoPageUiBinder.class);

	interface AccountInfoPageUiBinder extends UiBinder<Widget, AccountInfoPage> {
	}

	public AccountInfoPage() {
		initWidget(uiBinder.createAndBindUi(this));
		resultPanel.clear();
		textboxConfirmPass.addValidator(new Validator<String>() {
			@Override
			public List<EditorError> validate(Editor<String> editor,
					String value) {
			    List<EditorError> result = new ArrayList<EditorError>();
			    String valueStr = value == null ? "" : value.toString().trim();
			    String newPass = textboxNewPass.getText();
			    if(newPass.equals(valueStr)){
			    	if(newPass.length()>6){
			    		//pass validation
			    	}else {
			    		result.add(new BasicEditorError(textboxConfirmPass, value, "Password length too short"));
					}
			    }else {
			    	result.add(new BasicEditorError(textboxConfirmPass, value, "Password does not match"));
				}
			    return result;			
			}
			@Override
			public int getPriority() {
				// TODO Auto-generated method stub
				return 0;
			}
		});
		cancelButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				cancelButton.setVisible(false);
				changePasswordForm.reset();
			}
		});
		cancelButton.setVisible(false);
	}
	
	
	
	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		changePasswordForm.reset();
		cancelButton.setVisible(false);
	}
	
	
	@UiHandler("updateButton")
	void updatePassword(ClickEvent event){
		
		if(changePasswordForm.validate()){
				try {
					String oldpass = Md5Utils.getMD5String(textboxOldPass.getText().trim());
					String newpass = Md5Utils.getMD5String(textboxNewPass.getText().trim());
					greetingService.changePassword(MainPage.getLoggedinUser(), oldpass, newpass, new AsyncCallback<Response>() {
						
						@Override
						public void onSuccess(Response result) {
							// TODO Auto-generated method stub
							if(result.getCode()==0){
								resultPanel.add(new MessagePanel("Your password has been changed.", "", false, "", ""));								
							}else {
								resultPanel.add(new MessagePanel(result.getMessage(), "", false, "", ""));
							}
							Timer timer = new Timer() {					
								@Override
								public void run() {
									// TODO Auto-generated method stub
									resultPanel.clear();
								}
							};
							timer.schedule(5 * 1000);
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							resultPanel.add(new MessagePanel("Please try later", "", false, "", ""));
							Timer timer = new Timer() {					
								@Override
								public void run() {
									// TODO Auto-generated method stub
									resultPanel.clear();
								}
							};
							timer.schedule(5 * 1000);
						}
					});
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				changePasswordForm.reset();
				cancelButton.setVisible(false);
				

		}else {
			cancelButton.setVisible(true);
		}
	}
}
