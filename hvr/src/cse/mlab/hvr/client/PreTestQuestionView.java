package cse.mlab.hvr.client;

import java.util.Date;
import java.util.Iterator;

import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.InlineRadio;
import org.gwtbootstrap3.client.ui.ListBox;
import org.gwtbootstrap3.client.ui.Radio;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.StringRadioGroup;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;
import org.gwtbootstrap3.client.ui.html.Br;
import org.gwtbootstrap3.client.ui.html.Hr;
import org.gwtbootstrap3.extras.datepicker.client.ui.DatePicker;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.DateTimePicker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.events.DependentQuestionSelectionEvent;
import cse.mlab.hvr.client.events.DependentQuestionSelectionEventHandler;
import cse.mlab.hvr.shared.study.PreTestQuestion;

public class PreTestQuestionView extends Composite {
	
	@UiField
	HTMLPanel pretestQuestionPanel;
	
	private PreTestQuestion pretestQuestion;
	
	Widget questionWidget;

	private static PreTestQuestionViewUiBinder uiBinder = GWT.create(PreTestQuestionViewUiBinder.class);

	interface PreTestQuestionViewUiBinder extends UiBinder<Widget, PreTestQuestionView> {
	}
	
//	private ColumnPull getColumnPull(int level){
//		if(level == 0){
//			return ColumnPull.LG_0;
//		} else if (level == 1) {
//			return ColumnPull.LG_1;
//		} else if (level == 2) {
//			return ColumnPull.LG_2;
//		} else if (level == 3) {
//			return ColumnPull.LG_3;
//		} else if (level == 4) {
//			return ColumnPull.LG_4;
//		} else if (level == 5) {
//			return ColumnPull.LG_5;
//		} else if (level == 6) {
//			return ColumnPull.LG_6;
//		} else {
//			return ColumnPull.LG_7;
//		}
//	}

	public PreTestQuestionView(PreTestQuestion pretestQuestion) {
		initWidget(uiBinder.createAndBindUi(this));
		this.pretestQuestion = pretestQuestion;
		if(this.pretestQuestion.getDisplayLevel()<=1){
			pretestQuestionPanel.add(new Hr());
		} else {
			pretestQuestionPanel.add(new Br());
			pretestQuestionPanel.setVisible(false);
		}
		
		final String parentId = this.pretestQuestion.getParentId();
		final boolean hasChild = this.pretestQuestion.isHasChild();
		final String dependentAnswer = this.pretestQuestion.getChildVisibleDependentAnswer();
		final String questionId = this.pretestQuestion.getQuestionId();
		final String defaultAnswer = this.pretestQuestion.getDefaultAnswer()==null? "": this.pretestQuestion.getDefaultAnswer();

		
		if(this.pretestQuestion.getParentId() == null || this.pretestQuestion.getParentId().isEmpty()){
			//do nothing
		} else {
			Hvr.eventBus.addHandler(DependentQuestionSelectionEvent.TYPE, new DependentQuestionSelectionEventHandler() {
				
				@Override
				public void actionAfterDependentSelection(DependentQuestionSelectionEvent event) {
					// TODO Auto-generated method stub
					if(parentId.equalsIgnoreCase(event.getQuestionId())){
						if(event.isMakeVisible()){
							pretestQuestionPanel.setVisible(true);
							if(hasChild){
								//in ideal case, hasChild should be true for only radio, therefore other cases are not checked
								if(dependentAnswer.equalsIgnoreCase(getSelectedRadioValue((StringRadioGroup)questionWidget))){
									Hvr.eventBus.fireEvent(new DependentQuestionSelectionEvent(questionId, true));
								}
								
							}
						}else {
							pretestQuestionPanel.setVisible(false);
							if(hasChild){
								//in ideal case, hasChild should be true for only radio, therefore other cases are not checked
								Hvr.eventBus.fireEvent(new DependentQuestionSelectionEvent(questionId, false));
							}
						}
					}
				}
			});
		}
		
		
		Row r = new Row();
		pretestQuestionPanel.add(r);
		
		Column c1 = new Column(ColumnSize.MD_5);
		Column c2 = new Column(ColumnSize.MD_5);
		r.add(c1);
		r.add(c2);
		
		Label qLabel = new Label(this.pretestQuestion.getQuestion());
		//c1.setPull(getColumnPull(this.pretestQuestion.getDisplayLevel()));
		qLabel.addStyleName("col-lg-offset-2");
		c1.add(qLabel);
		
		if(this.pretestQuestion.getType().startsWith("date")){
			if(this.pretestQuestion.getType().contains("time")){
				DateTimePicker dateTimePicker = new DateTimePicker();
				dateTimePicker.setAutoClose(true);
				dateTimePicker.setFormat("yyyy-mm-dd hh:ii:ss");
				questionWidget = dateTimePicker;
			}else {
				DatePicker datePicker = new DatePicker();
				datePicker.setFormat("yyyy-mm-dd");
				datePicker.setAutoClose(true);
				datePicker.setAllowBlank(false);
				if(defaultAnswer ==null || defaultAnswer.isEmpty()){
					//skip
				}else {
					//datePicker.setValue(new js);
					Date value = dateFromString(defaultAnswer, "yyyy-mm-dd");
					if(value!=null){
						datePicker.setValue(value);
					}
				}
				questionWidget = datePicker;
				
			}
			
		}else if (this.pretestQuestion.getType().startsWith("text")) {
			TextBox textBox = new TextBox();
			textBox.setText(defaultAnswer);
			questionWidget = textBox;
			
			
		} else if (this.pretestQuestion.getType().startsWith("radio")) {
			final StringRadioGroup radioGroup = new StringRadioGroup(this.pretestQuestion.getQuestionId()+"-radio"); 
			JsArrayString temp = split(this.pretestQuestion.getPossibleAnswers(), "|");
			for(int i=0;i<temp.length();i++){
				InlineRadio tempRadio = new InlineRadio(this.pretestQuestion.getQuestionId()+"-radio", temp.get(i));
				//tempRadio.setText(temp.get(i));
				
				if(defaultAnswer.equalsIgnoreCase(temp.get(i))){
					tempRadio.setValue(true);
				}
				radioGroup.add(tempRadio);
			}
			
			if(this.pretestQuestion.isHasChild()){
				radioGroup.addValueChangeHandler(new ValueChangeHandler<String>() {
					
					@Override
					public void onValueChange(ValueChangeEvent<String> event) {
						// TODO Auto-generated method stub
						Iterator<Radio> it = ((StringRadioGroup)questionWidget).getRadioChildren().iterator();
						
						while(it.hasNext()){
							Radio radio = it.next();
							if(radio.getValue()){
								if(radio.getText().equalsIgnoreCase(dependentAnswer)){
									//Window.alert("should fire event to visible child");
									Hvr.eventBus.fireEvent(new DependentQuestionSelectionEvent(questionId, true));
								}else {
									Hvr.eventBus.fireEvent(new DependentQuestionSelectionEvent(questionId, false));
								}
							}
						}

					}
				});
				
				if(this.pretestQuestion.getDisplayLevel()==1 && (this.pretestQuestion.getDefaultAnswer().equalsIgnoreCase(this.pretestQuestion.getChildVisibleDependentAnswer()))){
					Timer timer = new Timer() {					
						@Override
						public void run() {
							Hvr.eventBus.fireEvent(new DependentQuestionSelectionEvent(questionId, true));
						}
					};
					timer.schedule(1 * 1000);

				}
			}
			
			questionWidget = radioGroup;
			
			
		} else if (this.pretestQuestion.getType().startsWith("dropdown")) {
			ListBox listBox = new ListBox();
			JsArrayString temp = split(this.pretestQuestion.getPossibleAnswers(), "|");
			boolean indexSelected = false;
			for(int i=0;i<temp.length();i++){
				listBox.addItem(temp.get(i));
				if(defaultAnswer.equalsIgnoreCase(temp.get(i))){
					listBox.setSelectedIndex(i);
					indexSelected = true;
				}
			}
			if(!indexSelected){
				listBox.setSelectedIndex(-1);
			}
			questionWidget = listBox;
		}
		
		c2.add(questionWidget);
		
	}
	
	private Date dateFromString(String dateInString, String format){
		try {
			return DateTimeFormat.getFormat(format).parse(dateInString);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	private String getSelectedRadioValue(StringRadioGroup stringRadioGroup){
		Iterator<Radio> it = stringRadioGroup.getRadioChildren().iterator();
		
		while(it.hasNext()){
			Radio radio = it.next();
			if(radio.getValue()){
				return radio.getText();
			}
		}
		return "";
	}
	
	protected String getQuestionId() {
		return this.pretestQuestion.getQuestionId();
	}
	
	protected String getQuestion() {
		return this.pretestQuestion.getQuestion();
	}
	
	protected String getAnswer() {

		if(pretestQuestionPanel.isVisible()){
			if(questionWidget instanceof DatePicker){
				String answerDate = ((DatePicker) questionWidget).getBaseValue();
				return answerDate;
			}else if(questionWidget instanceof DateTimePicker){
				String answerDate = ((DateTimePicker) questionWidget).getBaseValue();
				return answerDate;
			}else if (questionWidget instanceof TextBox) {
				return ((TextBox) questionWidget).getText();
			}else if (questionWidget instanceof StringRadioGroup) {
				Iterator<Radio> it = ((StringRadioGroup)questionWidget).getRadioChildren().iterator();
				
				while(it.hasNext()){
					Radio radio = it.next();
					if(radio.getValue()){
						return radio.getText();
					}
				}
				
				//return ((StringRadioGroup)questionWidget).ge;
			}else if (questionWidget instanceof ListBox) {
				return ((ListBox)questionWidget).getSelectedValue();
			}
			
		}
		
		return "";
	}
	
	Date getDateFromString(String dateStr) {
		try {
			//String pattern = "MM/dd/yyyy";
			String pattern = "yyyy-MM-dd";
			DateTimeFormat format = DateTimeFormat.getFormat(pattern);
			return format.parseStrict(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	
	public static final native JsArrayString split(String string, String separator) /*-{
    	return string.split(separator);
    }-*/;

}
