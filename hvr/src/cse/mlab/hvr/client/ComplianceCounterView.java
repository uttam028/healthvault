package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Heading;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class ComplianceCounterView extends Composite {

	@UiField
	HTMLPanel canvasPanel;
	@UiField
	Heading panelHeading;
	
	private static ComplianceCounterViewUiBinder uiBinder = GWT
			.create(ComplianceCounterViewUiBinder.class);

	interface ComplianceCounterViewUiBinder extends
			UiBinder<Widget, ComplianceCounterView> {
	}

	public ComplianceCounterView(String heading, String centerText, String lowerText) {
		initWidget(uiBinder.createAndBindUi(this));
		
		panelHeading.setText(heading);
		Canvas canvas = Canvas.createIfSupported();
		if(canvas != null){
			canvas.setHeight("100%");
			canvas.setWidth("100%");
			canvasPanel.add(canvas);
			Context2d context = canvas.getContext2d();
			int centerX = context.getCanvas().getWidth()/2;
			int centerY = context.getCanvas().getHeight()/2;

			int radius =(int)( ( canvas.getCoordinateSpaceHeight()/2) * 0.95);
			context.beginPath();
			context.arc(centerX, centerY, radius, 0, 2* Math.PI);
		    context.setFillStyle("green");

			//context.fill();
			context.setTextAlign(TextAlign.CENTER);
			
			int fontLength = Math.max(radius - ((centerText.length()-1) * 10), 20); 
			context.setFont(fontLength + "px Arial");
			context.fillText(centerText, centerX, centerY + (fontLength/2));
			context.setLineWidth(10);
			context.setStrokeStyle("#003300");
			context.stroke();
			//context.closePath();
			
		}else {
			Window.alert("canvas not supported...");
		}
	}


}
