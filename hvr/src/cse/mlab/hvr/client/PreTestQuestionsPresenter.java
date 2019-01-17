package cse.mlab.hvr.client;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.html.Br;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.client.PreTestState.InternalState;
import cse.mlab.hvr.client.events.PreTestInternalEvent;
import cse.mlab.hvr.shared.Response;
import cse.mlab.hvr.shared.study.PreTestAnswers;
import cse.mlab.hvr.shared.study.PreTestQuestion;

public class PreTestQuestionsPresenter extends Composite  {
	
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);


	@UiField
	HTMLPanel pretestPanel, questionPanel;
	
	
	
	@UiField
	Button pretestSubmitButton;
	
	//ArrayList<PreTestQuestion> list = new ArrayList<PreTestQuestion>();
	//ArrayList<PreTestQuestionView> questionViewList = new ArrayList<PreTestQuestionView>();
	private String participationId = "0";

	
	private static PreTestQuestionsPresenterUiBinder uiBinder = GWT
			.create(PreTestQuestionsPresenterUiBinder.class);

	interface PreTestQuestionsPresenterUiBinder extends
			UiBinder<Widget, PreTestQuestionsPresenter> {
	}

	public PreTestQuestionsPresenter(String testId, String participationId) {
		initWidget(uiBinder.createAndBindUi(this));
		this.participationId = participationId;
		
		greetingService.getPreTestQuestions(testId, new AsyncCallback<ArrayList<PreTestQuestion>>() {
			
			@Override
			public void onSuccess(ArrayList<PreTestQuestion> result) {
				/*for(int i=0;i<result.size();i++){
					list.add(result.get(i));
				}*/
				for(int i=0;i<result.size();i++){
					questionPanel.add(new PreTestQuestionView(result.get(i)));
					//pretestPanel.add(new Br());
				}
				
				questionPanel.add(new Br());
				questionPanel.add(new Br());
				questionPanel.add(new Br());

			}
			
			@Override
			public void onFailure(Throwable caught) {
				// failed to load pretest questions, moving to next section
//				Hvr.eventBus.fireEvent(new PreTestSubmitEvent());
				Hvr.getEventBus().fireEvent(new PreTestInternalEvent(new PreTestState(InternalState.QUESTION)));

			}
		});
		
		/*
		PreTestQuestion q1 = new PreTestQuestion("1", "1", "First Question", "date", "", 1, 1, true, true, "", false, "", "");
		PreTestQuestion q2 = new PreTestQuestion("1", "2", "Second Question", "text", "", 1, 2, true, true, "", false, "", "");
		PreTestQuestion q3 = new PreTestQuestion("1", "3", "Third Question", "radio", "red|green|blue", 1, 3, true, true, "", false, "", "red");
		PreTestQuestion q4 = new PreTestQuestion("1", "4", "Fourth Question", "dropdown", "one|two|three", 1, 4, true, true, "", false, "", "one");
		PreTestQuestion q5 = new PreTestQuestion("1", "5", "Do you have any problem with speech now", "radio", "yes|no", 1, 0, true, true, "", true, "yes", "");
		PreTestQuestion q6 = new PreTestQuestion("1", "6", "When did it begin?", "date", "", 2, 0, true, true, "5", false, "", "");
		PreTestQuestion q7 = new PreTestQuestion("1", "7", "Has it changed?", "radio", "yes|no", 2, 0, true, true, "5", true, "yes", "no");
		PreTestQuestion q8 = new PreTestQuestion("1", "8", "How?", "radio", "Better|Worse|Fluctuates", 3, 0, true, true, "7", false, "", "");
		PreTestQuestion q9 = new PreTestQuestion("1", "9", "Describe your speech difficulties:", "dropdown", "Fast|Slow|Loud|Quiet|Effortful", 2, 0, true, true, "5", false, "", "");
		PreTestQuestion q10 = new PreTestQuestion("1", "10", "Do people have trouble understanding you?", "radio", "yes|no", 2, 0, true, true, "5", true, "yes", "no");
		PreTestQuestion q11 = new PreTestQuestion("1", "11", "Range of difficulty", "radio", "1|2|3|4|5", 3, 0, true, true, "10", false, "", "");
		PreTestQuestion q12 = new PreTestQuestion("1", "12", "Do any factors affect your speech?", "radio", "yes|no", 2, 0, true, true, "5", true, "yes", "no");
		PreTestQuestion q13 = new PreTestQuestion("1", "13", "Which ones:", "dropdown", "Stress|Fatigue|Time of Day|Place", 3, 0, true, true, "12", false, "", "");
		PreTestQuestion q14 = new PreTestQuestion("1", "14", "Have you altered your lifestyle because of your speech?", "radio", "yes|no", 2, 0, true, true, "5", false, "", "");
		PreTestQuestion q15 = new PreTestQuestion("1", "15", "How important is your speech problem for you?", "radio", "1|2|3|4|5", 2, 0, true, true, "5", false, "", "");
		
		list.add(q1);
		list.add(q2);
		list.add(q3);
		list.add(q4);
		list.add(q5);
		list.add(q6);
		list.add(q7);
		list.add(q8);
		list.add(q9);
		list.add(q10);
		list.add(q11);
		list.add(q12);
		list.add(q13);
		list.add(q14);
		list.add(q15);
		*/
		
		
	}

	@UiHandler("pretestSubmitButton")
	void pretestAnswersSubmitted(ClickEvent event){
		String answer = "answer:";
		
		ArrayList<String> questions = new ArrayList<>();
		ArrayList<String> answers = new ArrayList<>();
		
		//pretestAnswers.
		for(int i=0;i<questionPanel.getWidgetCount();i++){
			Widget childWidget = questionPanel.getWidget(i);
			if(childWidget instanceof PreTestQuestionView){
				questions.add(((PreTestQuestionView)childWidget).getQuestionId());
				answers.add(((PreTestQuestionView)childWidget).getAnswer());
				answer += ((PreTestQuestionView)childWidget).getAnswer();
			}
			
		}
		PreTestAnswers pretestAnswers = new PreTestAnswers(MainPage.getLoggedinUser(), this.participationId, "", questions, answers);
		//Window.alert(answer);
		
		
		greetingService.submitPreTestAnswer(pretestAnswers, new AsyncCallback<Response>() {
			@Override
			public void onSuccess(Response result) {
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
			
		});
		
//		Hvr.eventBus.fireEvent(new PreTestSubmitEvent());
		Hvr.getEventBus().fireEvent(new PreTestInternalEvent(new PreTestState(InternalState.QUESTION)));

	}
}
