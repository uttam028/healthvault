package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.ImageAnchor;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.constants.ButtonSize;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.html.Br;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
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

	public MessagePanel(String message, final String url, final Boolean isHistoryToken, String tagId, String buttonText) {
		initWidget(uiBinder.createAndBindUi(this));
		//final HTMLPanel panel = new HTMLPanel("");
		//panel.setWidth("500px");
		panel.getElement().setId(tagId);
		panel.addStyleName("panel_border");
		panel.getElement().getStyle().setMarginBottom(1.2, Unit.EM);
		
		Row row1 = new Row();
		panel.add(row1);
		Column c1 = new Column(ColumnSize.LG_11);
		Column c2 = new Column(ColumnSize.LG_1);
		row1.add(c1);
		row1.add(c2);
		//row1.addStyleName("col-lg-offset-11");
		ImageAnchor closeAnc = new ImageAnchor();
		closeAnc.setIcon(IconType.CLOSE);
		c2.add(closeAnc);
		row1.add(new Br());
		
		
		Row row2 = new Row();
		Label messageLabel = new Label(message);
		Column c3 = new Column(ColumnSize.LG_2);
		Column c4 = new Column(ColumnSize.LG_10);
		Image image = new Image("images/notification.png");
		row2.add(c3);
		row2.add(c4);
		c3.add(image);
		messageLabel.addStyleName("text-align: left;");
		messageLabel.getElement().getStyle().setFontSize(1.3, Unit.EM);
		//messageLabel.getElement().getStyle().setMargin(1.05, Unit.EM);
		c4.add(messageLabel);
		panel.add(row2);
		//panel.add(new Br());
		if(url == null || url.isEmpty()){
			//do nothing
			panel.add(new Br());
			panel.add(new Br());
		} else {
			//panel.add(new Hr());
			
			Row row3 = new Row();
			panel.add(row3);
			Button actionButton = new Button("");
			if(buttonText!=null && !buttonText.isEmpty()){
				actionButton.setText(buttonText);
			} else {
				actionButton.setText("Next");
			}
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
