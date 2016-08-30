package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ImageAnchor;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.constants.ButtonSize;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class MessagePanel extends Composite{
	
	@UiField
	HTMLPanel panel;

	private static MessagePanelUiBinder uiBinder = GWT
			.create(MessagePanelUiBinder.class);

	interface MessagePanelUiBinder extends UiBinder<Widget, MessagePanel> {
	}

	public MessagePanel(String message, final String url, final Boolean isHistoryToken, String tagId) {
		initWidget(uiBinder.createAndBindUi(this));
		//final HTMLPanel panel = new HTMLPanel("");
		//panel.setWidth("500px");
		panel.getElement().setId(tagId);
		panel.addStyleName("panel_border");
		panel.getElement().getStyle().setMarginBottom(1.2, Unit.EM);
		
		Row row1 = new Row();
		panel.add(row1);
		row1.addStyleName("col-lg-offset-11");
		ImageAnchor closeAnc = new ImageAnchor();
		closeAnc.setIcon(IconType.CLOSE);
		row1.add(closeAnc);
		//panel.add(new Br());
		
		
		Row row2 = new Row();
		Label messageLabel = new Label(message);
		messageLabel.addStyleName("text-align: left;");
		messageLabel.getElement().getStyle().setFontSize(1.2, Unit.EM);
		messageLabel.getElement().getStyle().setMargin(1.05, Unit.EM);
		row2.add(messageLabel);
		panel.add(row2);
		//panel.add(new Br());
		if(url == null || url.isEmpty()){
			//do nothing
		} else {
			//panel.add(new Hr());
			
			Row row3 = new Row();
			panel.add(row3);
			Button actionButton = new Button("Next");
			actionButton.setStyleName("col-lg-offset-9");
			actionButton.setType(ButtonType.LINK);
			actionButton.setSize(ButtonSize.LARGE);
			row3.add(actionButton);
			actionButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					panel.getParent().removeFromParent();
					if(isHistoryToken){
						History.newItem(url);
					}else {
						Window.open(url, "_blank", "");
					}
				}
			});
		}
		closeAnc.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				//panel.removeFromParent();
				panel.getParent().removeFromParent();
			}
		});
		//panel.add(new Br());
		
	}

}
