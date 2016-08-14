package cse.mlab.hvr.client;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Panel;
import org.gwtbootstrap3.client.ui.PanelBody;
import org.gwtbootstrap3.client.ui.PanelCollapse;
import org.gwtbootstrap3.client.ui.PanelGroup;
import org.gwtbootstrap3.client.ui.PanelHeader;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.gwtbootstrap3.client.ui.constants.Toggle;
import org.gwtbootstrap3.client.ui.html.Paragraph;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.shared.QA;

public class QuestionAnswerList extends Composite{
	
	@UiField
	PanelGroup qaPanelGroup;
	
	private ArrayList<QA> qaList;
	private static QuestionAnswerUiBinder uiBinder = GWT
			.create(QuestionAnswerUiBinder.class);

	interface QuestionAnswerUiBinder extends UiBinder<Widget, QuestionAnswerList> {
	}

	public QuestionAnswerList(ArrayList<QA> qaList) {
		initWidget(uiBinder.createAndBindUi(this));
		this.qaList = qaList;
		if(qaList != null){
			for(QA qa:qaList){
				Panel qaPanel = new Panel();
				
				PanelCollapse panelCollapse = new PanelCollapse();
				PanelBody panelBody = new PanelBody();
				panelBody.add(new Paragraph(qa.getAnswer()));
				panelCollapse.add(panelBody);
				if(qaPanelGroup.getWidgetCount()==0){
					panelCollapse.setIn(true);
				}
				
				PanelHeader panelHeader = new PanelHeader();
				panelHeader.setDataToggle(Toggle.COLLAPSE);
				panelHeader.setDataTargetWidget(panelCollapse);
				Heading heading = new Heading(HeadingSize.H4, qa.getQuestion());
				panelHeader.add(heading);
				
				qaPanel.add(panelHeader);
				qaPanel.add(panelCollapse);
				
				qaPanelGroup.add(qaPanel);
			}
			
		}
	}


}
