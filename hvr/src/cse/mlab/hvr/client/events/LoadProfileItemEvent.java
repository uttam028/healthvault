package cse.mlab.hvr.client.events;

import com.google.gwt.event.shared.GwtEvent;

import cse.mlab.hvr.client.ProfilePageItem;

public class LoadProfileItemEvent extends GwtEvent<LoadProfileItemEventHandler> {

	private ProfilePageItem profilePageItem;

	public LoadProfileItemEvent(ProfilePageItem profilePageItem) {
		super();
		this.profilePageItem = profilePageItem;
	}

	public static Type<LoadProfileItemEventHandler> TYPE = new Type<LoadProfileItemEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LoadProfileItemEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(LoadProfileItemEventHandler handler) {
		// TODO Auto-generated method stub
		handler.initiateLoadingProfileItem(this);
	}

	public ProfilePageItem getProfilePageItem() {
		return profilePageItem;
	}

}
