package cse.mlab.hvr.client;

import org.gwtbootstrap3.client.ui.ListBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.Table;

public class CholesterolPage extends Composite {

	@UiField
	ListBox cholesterolUnitList;
	@UiField
	HTMLPanel cholesterolPanel;
	

	Table cholesterolTable = new Table();
	DataTable cholesterolTableData;

	private static CholesterolUiBinder uiBinder = GWT
			.create(CholesterolUiBinder.class);

	interface CholesterolUiBinder extends UiBinder<Widget, CholesterolPage> {
	}

	public CholesterolPage() {
		initWidget(uiBinder.createAndBindUi(this));
		cholesterolUnitList.addItem("mg/dL  ");
		cholesterolUnitList.addItem("mmol/L  ");
		
		cholesterolTableData = DataTable.create();
		cholesterolTableData.addColumn(ColumnType.STRING, "Unit");
//		cholesterolTableData.addRows(1);
//		cholesterolTableData.setValue(0, 0, "kg");
		cholesterolTable.draw(cholesterolTableData);
		cholesterolPanel.add(cholesterolTable);
	}

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
//		Column<Cholesterol, String> cholesterolUnit = new Column<Cholesterol, String>(new TextCell()) {
//			@Override
//			public String getValue(Cholesterol object) {
//				// TODO Auto-generated method stub
//				return object.getUnit();
//			}
//		};
//		cholesterolTable.addColumn(cholesterolUnit, "Unit");
//        myTable.addColumn(ColumnType.STRING, "Trading Code Raw");
//        myTable.addColumn(ColumnType.NUMBER, "LTP");
//        myTable.addColumn(ColumnType.NUMBER, "High");
//        myTable.addColumn(ColumnType.NUMBER, "Low");
//        myTable.addColumn(ColumnType.NUMBER, "Close Price");
//        myTable.addColumn(ColumnType.NUMBER, "YCP");

//		cholesterolTableData.addColumn(ColumnType.NUMBER, "HDL");
//		cholesterolTableData.addColumn(ColumnType.NUMBER, "LDL");
//		cholesterolTableData.addColumn(ColumnType.NUMBER, "TriGlycarides");
//		cholesterolTableData.addColumn(ColumnType.STRING, "Unit");
//		cholesterolTableData.addColumn(ColumnType.DATE, "Date");
		
		super.onLoad();
	}
}
