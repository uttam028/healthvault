package cse.mlab.hvr.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class DependentQuestionSelectionEvent extends GwtEvent<DependentQuestionSelectionEventHandler>{
	
	private String questionId;
	private boolean makeVisible;	
	
	public DependentQuestionSelectionEvent(String questionId, boolean makeVisible) {
		super();
		this.questionId = questionId;
		this.makeVisible = makeVisible;
	}

	public static Type<DependentQuestionSelectionEventHandler> TYPE = new Type<DependentQuestionSelectionEventHandler>();
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DependentQuestionSelectionEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(DependentQuestionSelectionEventHandler handler) {
		// TODO Auto-generated method stub
		handler.actionAfterDependentSelection(this);
	}
	
	public String getQuestionId() {
		return questionId;
	}
	public boolean isMakeVisible() {
		return makeVisible;
	}

}
