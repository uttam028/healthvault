package cse.mlab.hvr.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.TabPane;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.gwtbootstrap3.client.ui.html.Hr;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;

import cse.mlab.hvr.shared.AnnotationChart;
import cse.mlab.hvr.shared.AnnotationChart.Options;
import cse.mlab.hvr.shared.study.DaywiseStudyStat;

public class StudyAdministration extends Composite{
	
	@UiField
	TabPane tabpaneOverview;
	
	@UiField
	HTMLPanel countPanel;
	
	private ArrayList<DaywiseStudyStat> daywiseList = new ArrayList<>();
	AnnotationChart chart;
	
	
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
		
		Heading testCountHeading = new Heading(HeadingSize.H4, "Completed Speech Test");
		tabpaneOverview.add(testCountHeading);
		
		tabpaneOverview.add(row1);
		tabpaneOverview.add(new Hr());
		
		populateData();
		
		drawTimelineChart();
		tabpaneOverview.add(chart);
		
	}
	
	private void redrawChart(){
		chart.draw(getDataTableFromList());
	}
	
	public void drawTimelineChart() {
		try {
			//chart.setPixelSize(900, 300);
			chart = new AnnotationChart(getDataTableFromList(), getTableOptions(), "100%", "100%");
			chart.draw(getDataTableFromList(), getTableOptions());
			
		} catch (Exception e) {
			// TODO: handle exception
			Window.alert("exception :" + e.getMessage());
		}
	}

	protected Options getTableOptions() {
		Options options = Options.create();
		options.setAllowHtml(false);
		options.set("displayAnnotations", false);
		return options;
	}

	protected DataTable getDataTableFromList() {
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.DATE, "Date");
		dataTable.addColumn(ColumnType.NUMBER, "EnrollCount");
		dataTable.addColumn(ColumnType.NUMBER, "ParticipationCount");
		dataTable.addColumn(ColumnType.NUMBER, "OverallEnrollment");
		dataTable.addColumn(ColumnType.NUMBER, "OverallParticipation");

		dataTable.addRows(daywiseList.size());
		int columnIndex = 0;
		int rowIndex = 0;
		Iterator<DaywiseStudyStat> it = daywiseList.iterator();
		while (it.hasNext()) {
			DaywiseStudyStat studyStatDaywise = (DaywiseStudyStat) it.next();
			
			dataTable.setValue(rowIndex, columnIndex++, getDate(studyStatDaywise.getDataDate()));
			dataTable.setValue(rowIndex, columnIndex++, studyStatDaywise.getEnrollCount());
			dataTable.setValue(rowIndex, columnIndex++, studyStatDaywise.getParticipationCount());
			dataTable.setValue(rowIndex, columnIndex++, studyStatDaywise.getOverallEnrollCount());
			dataTable.setValue(rowIndex, columnIndex++, studyStatDaywise.getOverallParticipationCount());

			rowIndex += 1;
			columnIndex = 0;
		}
		return dataTable;
	}
	
	private void populateData(){
		DaywiseStudyStat st1 = new DaywiseStudyStat("2016-11-01", 5, 4, 5, 4);
		DaywiseStudyStat st2 = new DaywiseStudyStat("2016-11-02", 1, 4, 6, 8);
		DaywiseStudyStat st3 = new DaywiseStudyStat("2016-11-03", 2, 0, 8, 8);
		DaywiseStudyStat st4 = new DaywiseStudyStat("2016-11-04", 0, 3, 8, 11);
		DaywiseStudyStat st5 = new DaywiseStudyStat("2016-11-05", 5, 4, 5, 4);
		DaywiseStudyStat st6 = new DaywiseStudyStat("2016-11-06", 1, 4, 6, 8);
		DaywiseStudyStat st7 = new DaywiseStudyStat("2016-11-07", 2, 0, 8, 8);
		DaywiseStudyStat st8 = new DaywiseStudyStat("2016-11-08", 0, 3, 8, 11);
		DaywiseStudyStat st9 = new DaywiseStudyStat("2016-11-09", 5, 4, 5, 4);
		DaywiseStudyStat st10 = new DaywiseStudyStat("2016-11-10", 1, 4, 6, 8);
		DaywiseStudyStat st11 = new DaywiseStudyStat("2016-11-11", 2, 0, 8, 8);
		DaywiseStudyStat st12 = new DaywiseStudyStat("2016-11-12", 0, 3, 8, 11);
		DaywiseStudyStat st13 = new DaywiseStudyStat("2016-11-13", 5, 4, 5, 4);
		DaywiseStudyStat st14 = new DaywiseStudyStat("2016-11-14", 1, 4, 6, 8);
		DaywiseStudyStat st15 = new DaywiseStudyStat("2016-11-15", 2, 0, 8, 8);
		DaywiseStudyStat st16 = new DaywiseStudyStat("2016-11-16", 0, 3, 8, 11);
		DaywiseStudyStat st17 = new DaywiseStudyStat("2016-11-17", 2, 0, 8, 8);
		DaywiseStudyStat st18 = new DaywiseStudyStat("2016-11-18", 0, 3, 8, 11);
		DaywiseStudyStat st19 = new DaywiseStudyStat("2016-11-19", 5, 4, 5, 4);
		DaywiseStudyStat st20 = new DaywiseStudyStat("2016-11-20", 1, 4, 6, 8);
		DaywiseStudyStat st21 = new DaywiseStudyStat("2016-11-21", 2, 0, 8, 8);
		DaywiseStudyStat st22 = new DaywiseStudyStat("2016-11-22", 0, 3, 8, 11);
		DaywiseStudyStat st23 = new DaywiseStudyStat("2016-11-23", 5, 4, 5, 4);
		DaywiseStudyStat st24 = new DaywiseStudyStat("2016-11-24", 1, 4, 6, 8);
		DaywiseStudyStat st25 = new DaywiseStudyStat("2016-11-25", 2, 0, 8, 8);
		DaywiseStudyStat st26 = new DaywiseStudyStat("2016-11-26", 0, 3, 8, 11);
		
		
		daywiseList.add(st1);
		daywiseList.add(st2);
		daywiseList.add(st3);
		daywiseList.add(st4);
		daywiseList.add(st5);
		daywiseList.add(st6);
		daywiseList.add(st7);
		daywiseList.add(st8);
		daywiseList.add(st9);
		daywiseList.add(st10);
		daywiseList.add(st11);
		daywiseList.add(st12);
		daywiseList.add(st13);
		daywiseList.add(st14);
		daywiseList.add(st15);
		daywiseList.add(st16);
		daywiseList.add(st17);
		daywiseList.add(st18);
		daywiseList.add(st19);
		daywiseList.add(st20);
		daywiseList.add(st21);
		daywiseList.add(st22);
		daywiseList.add(st23);
		daywiseList.add(st24);
		daywiseList.add(st25);
		daywiseList.add(st26);
		
	}
	public Date getDate(String dateString) {
	    Date result = null;
	    try {
	        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
	        result = dateTimeFormat.parse(dateString);
	    } catch (Exception e)
	    {
	        // ignore
	    }
	    return result;
	}
	
}
