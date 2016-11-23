package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.TabPane;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class StudyAdministration extends Composite{
	
	@UiField
	TabPane tabpaneOverview;
	
	@UiField
	HTMLPanel countPanel;
	
	private static StudyAdministrationUiBinder uiBinder = GWT
			.create(StudyAdministrationUiBinder.class);

	interface StudyAdministrationUiBinder extends
			UiBinder<Widget, StudyAdministration> {
	}

	public StudyAdministration() {
		initWidget(uiBinder.createAndBindUi(this));
		
		countPanel.getElement().getStyle().setBackgroundColor("#F5F5F5");
		
		Row row0 = new Row();
		Column col01 = new Column(ColumnSize.MD_4);
		Heading col01Heading = new Heading(HeadingSize.H3, "Total Enrollment");
		col01.add(col01Heading);
		Column col02 = new Column(ColumnSize.MD_4);
		Heading col02Heading = new Heading(HeadingSize.H3, "1234");
		col02.add(col02Heading);
		row0.add(col01);
		row0.add(col02);
		countPanel.add(row0);
		
		Row row1 = new Row();
		Column col1 = new Column(ColumnSize.MD_4);
		row1.add(col1);
		//Window.alert("size:" + col1.getElement().getAbsoluteBottom());
		col1.add(new ComplianceCounterView("Last Day", "5", ""));

		Column col2 = new Column(ColumnSize.MD_4);
		row1.add(col2);
		col2.add(new ComplianceCounterView("Last Week", "30", ""));

		Column col3 = new Column(ColumnSize.MD_4);
		row1.add(col3);
		col3.add(new ComplianceCounterView("Last Month", "478", ""));
		
		
		tabpaneOverview.add(row1);
		
	}
}
