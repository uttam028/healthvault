package cse.mlab.hvr.client;

import java.util.Date;

import org.gwtbootstrap3.extras.datetimepicker.client.ui.*;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


public class Medications extends Composite {

   private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

   private String emailId;
   //public MessageServiceAsync messageService = GWT.create(MessageService.class);
   
   

   
   /*
   * @UiTemplate is not mandatory but allows multiple XML templates
   * to be used for the same widget. 
   * Default file loaded will be <class-name>.ui.xml
   */
   
   @UiTemplate("Medications.ui.xml")
   interface LoginUiBinder extends UiBinder<Widget, Medications> {
   }

   /*
   @UiField(provided = true)
   final LoginResources res;
*/
   public Medications(String emailId) {
      //this.res = GWT.create(LoginResources.class);
      //res.style().ensureInjected();
      initWidget(uiBinder.createAndBindUi(this));
      this.emailId = emailId;
   }

 
   @UiField
   TextBox nameBox;

   @UiField
   TextBox strengthBox;
   
   @UiField
   TextBox dosBox;

   @UiField
   Label completionLabel2;
   @UiField
   DateTimePicker startDatePicker;
   
   private String message1;

   /*
   * Method name is not relevant, the binding is done according to the class
   * of the parameter.
   */
 
@UiHandler("buttonSubmit")
   void doClickSubmit(ClickEvent event) {
		//messageService.getMessage(message1, new MessageCallBack()); 
      Date startDate = startDatePicker.getValue();
      Window.alert("startdate:"+ startDate.getTime());
      
   }

   @UiHandler("nameBox")  
   void handleLoginChange(ValueChangeEvent<String> event) {
	   message1 = event.getValue();

   }

   @UiHandler("strengthBox")
   void handleNumChange(ValueChangeEvent<String> event) {
     if (event.getValue().matches("^-?\\d+$")) {
    	 completionLabel2.setText(" ");
	 } else {
	    completionLabel2.setText("Please enter a numerical value.");  
	 }
  }
   
   @UiHandler("dosBox")
   void handleNum2Change(ValueChangeEvent<String> event) {
     if (event.getValue().matches("^-?\\d+$")) {
    	 completionLabel2.setText(" ");
	 } else {
	    completionLabel2.setText("Please enter a numerical value.");  
	 }
  }
}