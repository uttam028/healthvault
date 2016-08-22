package cse.mlab.hvr.client;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.html.Br;
import org.gwtbootstrap3.client.ui.html.Hr;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.shared.QA;

public class SimpleFaqViewer extends Composite {
	@UiField
	HTMLPanel simpleFaqPanel;
	// PanelGroup qaPanelGroup;

	private ArrayList<QA> qaList;

	private static SimpleFaqViewerUiBinder uiBinder = GWT
			.create(SimpleFaqViewerUiBinder.class);

	interface SimpleFaqViewerUiBinder extends UiBinder<Widget, SimpleFaqViewer> {
	}

	public SimpleFaqViewer(ArrayList<QA> qaList, String title) {
		initWidget(uiBinder.createAndBindUi(this));
		this.qaList = qaList;
		if (qaList != null) {
			// for(QA qa:qaList){
			
			for (int i = 0; i < qaList.size(); i++) {
				HTMLPanel panel = new HTMLPanel("");
				panel.addStyleName("panel_border");

				Label qLabel = new Label((i + 1) + ". "
						+ qaList.get(i).getQuestion());
				qLabel.addStyleName("red");
				qLabel.addStyleName("col-lg-offset-1");
				Label ansLabel = new Label("- " + qaList.get(i).getAnswer());
				ansLabel.addStyleName("col-lg-offset-1");
				if(title.isEmpty() && i==0){
					//do nothing
					simpleFaqPanel.add(new Br());
				} else {
					simpleFaqPanel.add(new Hr());
				}
				
				simpleFaqPanel.add(qLabel);
				simpleFaqPanel.add(new Br());
				simpleFaqPanel.add(ansLabel);
				if (i == qaList.size() - 1) {
					simpleFaqPanel.add(new Br());
				}

			}
		}
	}
}
