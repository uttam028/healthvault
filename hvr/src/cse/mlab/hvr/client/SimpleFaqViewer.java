package cse.mlab.hvr.client;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.ImageAnchor;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.html.Br;
import org.gwtbootstrap3.client.ui.html.Hr;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import cse.mlab.hvr.shared.QA;

public class SimpleFaqViewer extends Composite {
	@UiField
	HTMLPanel simpleFaqPanel, simpleFaqHeader;
	// PanelGroup qaPanelGroup;

	private ArrayList<QA> qaList;
	private boolean faqVisible = true;
	private String title;
	private ImageAnchor arrowAnchor;
	private static SimpleFaqViewerUiBinder uiBinder = GWT
			.create(SimpleFaqViewerUiBinder.class);

	interface SimpleFaqViewerUiBinder extends UiBinder<Widget, SimpleFaqViewer> {
	}

	public void expandFaqs(){
		simpleFaqPanel.setVisible(true);
		faqVisible = true;
		arrowAnchor.setIcon(IconType.ANGLE_DOUBLE_UP);
	}
	
	public void collapseFaqs(){
		if(this.title == null || this.title.isEmpty()){
			//do nothing
		} else {
			simpleFaqPanel.setVisible(false);
			faqVisible = false;
			arrowAnchor.setIcon(IconType.ANGLE_DOUBLE_DOWN);
		}
	}
	
	public SimpleFaqViewer(ArrayList<QA> qaList, String title, boolean visibleInitially) {
		initWidget(uiBinder.createAndBindUi(this));
		this.qaList = qaList;
		this.title = title;
		if (qaList != null) {
			// for(QA qa:qaList){
			
			for (int i = 0; i < qaList.size(); i++) {
				//HTMLPanel panel = new HTMLPanel("");
				//panel.addStyleName("panel_border");

				Label qLabel = new Label((i + 1) + ". "
						+ qaList.get(i).getQuestion());
				qLabel.addStyleName("faq_ques");
				qLabel.getElement().getStyle().setFontWeight(FontWeight.BOLD);
				qLabel.addStyleName("col-lg-offset-1");
				Label ansLabel = new Label("- " + qaList.get(i).getAnswer());
				ansLabel.addStyleName("col-lg-offset-1");
				
				if(i==0){
					simpleFaqPanel.add(new Br());
				}else {
					simpleFaqPanel.add(new Hr());
				}
				
				simpleFaqPanel.add(qLabel);
				simpleFaqPanel.add(new Br());
				simpleFaqPanel.add(ansLabel);
				if (i == qaList.size() - 1) {
					simpleFaqPanel.add(new Br());
				}

			}
			simpleFaqPanel.addStyleName("panel_border");
		}

		if(title == null || title.isEmpty()){
			//do nothing
			simpleFaqPanel.setVisible(true);
		} else {
			simpleFaqHeader.addStyleName("panel_border");
			simpleFaqHeader.add(new Br());
			Row row1 = new Row();
			Column col1 = new Column("MD_7");
			Column col2 = new Column("MD_5");
			Heading heading = new Heading(HeadingSize.H4, title);
			heading.addStyleName("col-lg-offset-1");
			arrowAnchor = new ImageAnchor();
			if(visibleInitially){
				simpleFaqPanel.setVisible(true);
				faqVisible = true;
				arrowAnchor.setIcon(IconType.ANGLE_DOUBLE_UP);
			}else {
				simpleFaqPanel.setVisible(false);
				faqVisible = false;
				arrowAnchor.setIcon(IconType.ANGLE_DOUBLE_DOWN);
			}
			arrowAnchor.addStyleName("col-lg-offset-9");
			arrowAnchor.setIconSize(IconSize.TIMES2);
			col1.add(heading);
			col2.add(arrowAnchor);
			row1.add(col1);
			row1.add(col2);

			simpleFaqHeader.add(row1);
			simpleFaqHeader.add(new Br());
			simpleFaqHeader.addDomHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					if(faqVisible){
						simpleFaqPanel.setVisible(false);
						faqVisible = false;
						arrowAnchor.setIcon(IconType.ANGLE_DOUBLE_DOWN);
					} else {						
						simpleFaqPanel.setVisible(true);
						faqVisible = true;
						arrowAnchor.setIcon(IconType.ANGLE_DOUBLE_UP);
					}
				}
			}, ClickEvent.getType());
		}
		
	}
}
