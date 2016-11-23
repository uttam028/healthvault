package cse.mlab.hvr.client;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.html.Br;
import org.gwtbootstrap3.client.ui.html.Hr;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.shared.QA;

public class QuestionAnswerList extends Composite{
	
	@UiField
	HTMLPanel faqPanel;
	//PanelGroup qaPanelGroup;
	
	private ArrayList<QA> qaList;
	private static QuestionAnswerUiBinder uiBinder = GWT
			.create(QuestionAnswerUiBinder.class);

	interface QuestionAnswerUiBinder extends UiBinder<Widget, QuestionAnswerList> {
	}

	public QuestionAnswerList(ArrayList<QA> qaList) {
		initWidget(uiBinder.createAndBindUi(this));
		this.qaList = qaList;
		if(qaList != null){
			//for(QA qa:qaList){
			for(int i=0;i<qaList.size();i++){
				
				/*
				Panel qaPanel = new Panel();
				
				PanelCollapse panelCollapse = new PanelCollapse();
				PanelBody panelBody = new PanelBody();
				panelBody.add(new Paragraph(qa.getAnswer()));
				panelCollapse.add(panelBody);
				if(qaPanelGroup.getWidgetCount()==0){
					panelCollapse.setIn(true);
				}
				
				PanelHeader panelHeader = new PanelHeader();
				panelHeader.setStyleName("background-color: white;");;
				panelHeader.setDataToggle(Toggle.COLLAPSE);
				panelHeader.setDataTargetWidget(panelCollapse);
				Heading heading = new Heading(HeadingSize.H4, qa.getQuestion());
				panelHeader.add(heading);
				
				qaPanel.add(panelHeader);
				qaPanel.add(panelCollapse);
				
				qaPanelGroup.add(qaPanel);
				*/
				
				DisclosurePanel dis = new DisclosurePanel( qaList.get(i).getQuestion());
				dis.addStyleName("{style.important}");
				dis.setAnimationEnabled(true);
				if(i==0){
					dis.setOpen(true);
				}
				HTMLPanel ansPanel = new HTMLPanel("- "+qaList.get(i).getAnswer());
				dis.setContent(ansPanel);
				faqPanel.add(new Hr());
				faqPanel.add(dis);
				if(i==qaList.size()-1){
					faqPanel.add(new Br());
				}

			}
			
		}
	}


}
