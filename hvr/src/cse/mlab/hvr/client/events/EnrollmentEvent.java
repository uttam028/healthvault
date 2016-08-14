package cse.mlab.hvr.client.events;

import com.google.gwt.event.shared.GwtEvent;

import cse.mlab.hvr.client.EnrollmentState;

public class EnrollmentEvent extends
		GwtEvent<EnrollmentEventHandler> {
	private EnrollmentState state;

	public EnrollmentEvent(EnrollmentState state) {
		this.state = state;
	}

	public static Type<EnrollmentEventHandler> TYPE = new Type<EnrollmentEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<EnrollmentEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(EnrollmentEventHandler handler) {
		// TODO Auto-generated method stub
		handler.actionForEnrollment(this);
	}

	public EnrollmentState getState() {
		return state;
	}
}
