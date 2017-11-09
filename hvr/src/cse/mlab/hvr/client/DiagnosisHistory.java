package cse.mlab.hvr.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.gwtbootstrap3.client.shared.event.ModalHiddenEvent;
import org.gwtbootstrap3.client.shared.event.ModalShowEvent;
import org.gwtbootstrap3.client.shared.event.ModalShowHandler;
import org.gwtbootstrap3.client.shared.event.ModalShownEvent;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ListBox;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.TextArea;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.ButtonDismiss;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.Toggle;
import org.gwtbootstrap3.client.ui.html.Br;
import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.extras.datepicker.client.ui.DatePicker;
import org.gwtbootstrap3.extras.datepicker.client.ui.base.events.ChangeDateEvent;
import org.gwtbootstrap3.extras.datepicker.client.ui.base.events.ChangeDateHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.DataView;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.Table.Options;
import com.google.gwt.visualization.client.visualizations.Table.Options.Policy;

import cse.mlab.hvr.shared.Diagnosis;
import cse.mlab.hvr.shared.DiagnosisList;
import cse.mlab.hvr.shared.Response;

public class DiagnosisHistory extends Composite {
	
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	

	@UiField
	HTMLPanel diagnosisPanel;
	
	@UiField
	Modal diagnosisModal;
	
	@UiField
	Button buttonAddDiagnosis, buttonEditDiagnosis, buttonDeleteDiagnosis, buttonSubmit;
	
	@UiField
	ListBox conditionList;
	
	@UiField
	TextArea noteArea;

	@UiField
	DatePicker diagnosisDatePicker;
	
	@UiField
	Label conditionLabel, diagnosisDateLabel, noteLabel;
	
	@UiField
	TextBox otherText;
	
	Div diagnosisDiv;
	
	private String diagnosisCondition="";
	private String diagnosisNote="";
	private String diagnosisDate="";
	
	private boolean conditionError = false;
	private boolean noteError = false;
	private boolean dateError = false;

	private String emailId;
	private Boolean tableLoaded = false;

	private DiagnosisList diagnosisList = new DiagnosisList();
	Table diagnosisTable = new Table();
	DataView dataView  = null;

	private boolean modalFromEdit = false;
	private String editDiagnosisId = "";


	
	
	private static DiagnosisHistoryUiBinder uiBinder = GWT
			.create(DiagnosisHistoryUiBinder.class);

	interface DiagnosisHistoryUiBinder extends
			UiBinder<Widget, DiagnosisHistory> {
	}

	public DiagnosisHistory(String emailId) {
		initWidget(uiBinder.createAndBindUi(this));
		this.emailId = emailId;
		buttonAddDiagnosis.setIcon(IconType.STETHOSCOPE);
		buttonEditDiagnosis.setIcon(IconType.EDIT);
		buttonDeleteDiagnosis.setIcon(IconType.REMOVE);
		
		conditionList.setSelectedIndex(-1);
		
		diagnosisDiv = new Div();
		diagnosisDiv.setId("diagnosis_div");

		diagnosisModal.addShowHandler(new ModalShowHandler() {
			@Override
			public void onShow(ModalShowEvent evt) {
				// TODO Auto-generated method stub
			}
		});
		
		diagnosisDatePicker.addChangeDateHandler(new ChangeDateHandler() {
			
			@Override
			public void onChangeDate(ChangeDateEvent evt) {
				// TODO Auto-generated method stub
				diagnosisDate = diagnosisDatePicker.getBaseValue();
				String[] dates = diagnosisDate.split("-");
				int year = Integer.parseInt(dates[0]);
				diagnosisDateLabel.setText(Integer.toString(year));
				if ((year < 2000) || (year > 2030)) {
					diagnosisDateLabel.setText("Pick a valid date");
					dateError = true;
				} else {
					diagnosisDateLabel.setText("");
					dateError = false;
				}
				
			}
		});
		conditionList.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
				Window.alert("condition changed....");
				otherText.setText("");
				if(conditionList.getSelectedIndex()==0){
					conditionLabel.setText("Required field");
					otherText.setVisible(false);
				} else {
					conditionLabel.setText("");
					if(conditionList.getSelectedItemText().equalsIgnoreCase("other")){
						otherText.setVisible(true);
					} else {
						otherText.setVisible(false);
					}
				}
			}
		});
		
	}
	
	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		if (!tableLoaded) {
			diagnosisPanel.add(diagnosisDiv);
			diagnosisPanel.add(new Br());
			greetingService.getDiagnosisList(DiagnosisHistory.this.emailId,
					new AsyncCallback<DiagnosisList>() {
						@Override
						public void onSuccess(DiagnosisList diagList) {
							// TODO Auto-generated method stub
							// clickTest(result, medicationDiv.getId());
							// addMedicationsToHashmap(result);
							diagnosisList = diagList;
							diagnosisDiv.add(diagnosisTable);
							diagnosisPanel.add(diagnosisDiv);
							drawDiagnosisTable();;
							tableLoaded = true;
						}

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

						}
					});

		}
	}
	
	boolean validateDateFormat(String dateStr) {
		try {
			//String pattern = "MM/dd/yyyy";
			String pattern = "yyyy-MM-dd";
			DateTimeFormat format = DateTimeFormat.getFormat(pattern);
			format.parseStrict(dateStr);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	Date getDateFromString(String dateStr) {
		try {
			//String pattern = "MM/dd/yyyy";
			String pattern = "yyyy-MM-dd";
			DateTimeFormat format = DateTimeFormat.getFormat(pattern);
			return format.parseStrict(dateStr);
		} catch (Exception e) {
			return null;
		}
	}
	
	boolean dateCheck(String dateString) {
		String[] dates = dateString.split("-");
		int year = Integer.parseInt(dates[0]);
		if ((year < 2000) || (year > 2030)) {
			diagnosisDateLabel.setText("Pick a year between 2000 and 2030.");
			return true;
		} else {
			diagnosisDateLabel.setText("");
			return false;
		}
	}
	
	private int getIndexFromList(ListBox listBox, String item){
		int indexToFind = -1;
		for(int i=0;i<listBox.getItemCount();i++){
			if(listBox.getItemText(i).equals(item)){
				indexToFind = i;
				break;
			}
		}
		return indexToFind;
	}
	
	void resetAllFields() {
		diagnosisDate = "";
		diagnosisCondition = "";
		diagnosisNote = "";
		
		conditionError = false;
		noteError = false;
		dateError = false;
		
		conditionLabel.setText("");
		diagnosisDateLabel.setText("");
		noteLabel.setText("");
		otherText.setText("");
		otherText.setVisible(false);

		conditionList.setSelectedIndex(0);
		noteArea.setText("");
		diagnosisDatePicker.setValue(null);
	}
	
	void populateAllFields(Diagnosis diagnosis) {
		diagnosisDate = "";
		diagnosisCondition = "";
		diagnosisNote = "";
		
		conditionError = false;
		noteError = false;
		dateError = false;
		
		conditionLabel.setText("");
		diagnosisDateLabel.setText("");
		noteLabel.setText("");

		int index = getIndexFromList(conditionList, diagnosis.getCondition());
		if(index == -1){
			if(diagnosis.getCondition()!=null && !diagnosis.getCondition().isEmpty()){
				conditionList.setSelectedIndex(conditionList.getItemCount()-1);
				otherText.setText(diagnosis.getCondition());
				otherText.setVisible(true);
			}else {
				conditionList.setSelectedIndex(0);
			}
		} else {
			conditionList.setSelectedIndex(index);			
		}
		if(diagnosis.getNote()!=null){
			noteArea.setText(diagnosis.getNote());			
		} else {
			noteArea.setText("");
		}
		
		diagnosisDatePicker.setValue(getDateFromString(diagnosis.getDiagnosisDate()));
		
	}
	
	@UiHandler("buttonSubmit")
	void doClickSubmit(ClickEvent event) {
		
		if(conditionList.getSelectedIndex()==0){
			conditionLabel.setText("Required field");
			conditionError = true;
		}else {
			if(conditionList.getSelectedItemText().equalsIgnoreCase("other")){
				diagnosisCondition = otherText.getText().trim();
				if(diagnosisCondition.isEmpty()){
					conditionLabel.setText("Required field");
					conditionError = true;								
				}else {
					conditionLabel.setText("");
					conditionError = false;								
				}
			}else {
				diagnosisCondition = conditionList.getSelectedItemText();
				conditionLabel.setText("");
				conditionError = false;			
			}
		}
		
		if (diagnosisDate.isEmpty()) {
			diagnosisDateLabel.setText("Date is a required field.");
			dateError = true;
		} else {
			if (validateDateFormat(diagnosisDate)) {
				dateError = false;
				diagnosisDateLabel.setText("");
			} else {
				dateError = true;
				diagnosisDateLabel.setText("Date is not valid");
			}
		}
		
		if (conditionError || dateError) {
			//Window.alert("Please enter valid input");
		} else {
			final Diagnosis diagnosis;
			if (modalFromEdit) {
				diagnosis = getDiagnosisObject(editDiagnosisId);
				if (diagnosis == null) {
					return;
				}
			} else {
				diagnosis = new Diagnosis();
			}
			diagnosis.setEmail(emailId);
			diagnosis.setCondition(diagnosisCondition);
			diagnosis.setDiagnosisDate(diagnosisDate);
			diagnosis.setNote(noteArea.getText().trim());
			
			buttonSubmit.setDataDismiss(ButtonDismiss.MODAL);
			
			if(modalFromEdit){
				greetingService.updateDiagnosis(diagnosis, new AsyncCallback<Response>() {
					@Override
					public void onSuccess(Response result) {
						// TODO Auto-generated method stub
						if(result.getCode()==0){
							//success
							resetAllFields();
							drawDiagnosisTable();
						} else {
							//failed to update
							Window.alert("Service not available. Try later.");
						}
					}
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("Service not available. Try later.");
					}
				});
			} else {
				greetingService.saveDiagnosis(diagnosis, new AsyncCallback<Response>() {

							@Override
							public void onSuccess(Response result) {
								// TODO Auto-generated method stub
								if (result.getCode() == 0) {
									diagnosis.setId(Long.parseLong(result.getMessage()));
									diagnosisList.getDiagnosisList().add(0, diagnosis);
									//medicationModal.hi
									resetAllFields();
									drawDiagnosisTable();
								}else {
									Window.alert("Service not available. Try later.");
								}
							}

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								Window.alert("Service not available. Try later.");
							}
						});				
			}		
			
		}

	}

	@UiHandler("diagnosisModal")
	void modalShown(ModalShownEvent event){
		if (modalFromEdit == false) {
			resetAllFields();
		} else {
			populateAllFields(getDiagnosisObject(editDiagnosisId));
		}
	}
	@UiHandler("diagnosisModal")
	void modalHidden(ModalHiddenEvent event){
		modalFromEdit = false;
	}

	@UiHandler("buttonAddDiagnosis")
	public void addDiagnosisModal(ClickEvent event) {
		buttonAddDiagnosis.setDataToggle(Toggle.MODAL);
		buttonAddDiagnosis.setDataTarget("#"+diagnosisModal.getId());
		modalFromEdit = false;
	}
	
	
	@UiHandler("buttonDeleteDiagnosis")
	public void deleteSelectedDiagnosis(ClickEvent event) {
		// for instant let's say a string appended with |
		String list = "";
		// get id's of selected list
		ArrayList<String> checkedDiags = getCheckedDiags();
		if (checkedDiags.size() == 0) {
			Window.alert("Select an item to delete");
		} else {
			for (int i = 0; i < checkedDiags.size(); i++) {
				Diagnosis checkedDiagObject = getDiagnosisObject(checkedDiags.get(i));
				list += (checkedDiagObject.getId() + "|");
				diagnosisList.getDiagnosisList().remove(checkedDiagObject);
			}
			list = list.substring(0, list.length() - 1);
			greetingService.deleteDiagnosis(this.emailId, list, new AsyncCallback<Response>() {
						@Override
						public void onSuccess(Response result) {
							// TODO Auto-generated method stub
							if (result.getCode() == 0) {
								// Window.alert("success to delete");
							} else {
								// Window.alert("failed to delete");
							}
							drawDiagnosisTable();
						}

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							Window.alert("failed to delete:" + caught.getMessage());
						}
					});
		}
	}
	
	
	@UiHandler("buttonEditDiagnosis")
	void editSelectedDiagnosis(ClickEvent event) {
		ArrayList<String> checkedDiags = getCheckedDiags();
		if (checkedDiags.size() == 0) {
			Window.alert("Select an item to edit");
		} else if (checkedDiags.size() > 1) {
			Window.alert("Select one item to edit");
		} else {
			// Window.alert("Checked value :"+ checkedMedics.get(0));
			editDiagnosisId = checkedDiags.get(0);
			buttonEditDiagnosis.setDataToggle(Toggle.MODAL);
			//buttonEditMedic.setDataTargetWidget(medicationModal);
			buttonEditDiagnosis.setDataTarget("#"+diagnosisModal.getId());
			modalFromEdit = true;
		}
	}
	
	
	protected ArrayList<String> getCheckedDiags() {
		NodeList<Element> nodelist = querySelector("[id^=diagcheckbox]");
		ArrayList<String> checkedDiags = new ArrayList<>();
		for (int i = 0; i < nodelist.getLength(); i++) {
			boolean checked = nodelist.getItem(i).getPropertyBoolean("checked");
			if (checked) {
				checkedDiags.add(nodelist.getItem(i).getId().split("-")[1]);
			}
		}
		return checkedDiags;
	}

	protected Diagnosis getDiagnosisObject(String id) {
		List<Diagnosis> list = diagnosisList.getDiagnosisList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == Long.parseLong(id)) {
				return list.get(i);
			}
		}
		return null;
	}

	public void drawDiagnosisTable() {
		if(diagnosisList.getDiagnosisList().size() == 0){
			diagnosisTable.setVisible(false);
			buttonEditDiagnosis.setVisible(false);
			buttonDeleteDiagnosis.setVisible(false);
		} else {
			diagnosisTable.setVisible(true);			
			buttonEditDiagnosis.setVisible(true);
			buttonDeleteDiagnosis.setVisible(true);
			dataView = DataView.create(getDataTableFromList());
			dataView.hideColumns(new int[]{1});
			diagnosisTable.draw(dataView, getTableOptions());			
		}
	}	
	
	protected Options getTableOptions() {
		Options options = Options.create();
		options.setAllowHtml(true);
		options.setSort(Policy.DISABLE);
		options.setPage(Policy.ENABLE);
		options.setPageSize(50);
		options.setStartPage(0);
		options.setWidth("100%");
		options.setHeight("100%");
		return options;
	}

	protected DataTable getDataTableFromList() {
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "");
		dataTable.addColumn(ColumnType.NUMBER, "ID");
		dataTable.addColumn(ColumnType.STRING, "Condition");
		dataTable.addColumn(ColumnType.STRING, "Date Diagnosed");
		dataTable.addColumn(ColumnType.STRING, "Note");

		dataTable.addRows(diagnosisList.getDiagnosisList().size());
		int columnIndex = 0;
		int rowIndex = 0;
		List<Diagnosis> diagList = diagnosisList.getDiagnosisList();
		Iterator<Diagnosis> it = diagList.iterator();
		while (it.hasNext()) {
			Diagnosis diag = (Diagnosis) it.next();
			String checkbox = "<input type=\"checkbox\" text-align=\"center\"id=\"diagcheckbox-"
					+ diag.getId() + "\">";
			dataTable.setValue(rowIndex, columnIndex++, checkbox);
			dataTable.setValue(rowIndex, columnIndex++, diag.getId());
			dataTable.setValue(rowIndex, columnIndex++, diag.getCondition());
			dataTable.setValue(rowIndex, columnIndex++,
					diag.getDiagnosisDate() == null ? "" : diag.getDiagnosisDate());
			dataTable.setValue(rowIndex, columnIndex++, diag.getNote());

			rowIndex += 1;
			columnIndex = 0;
		}
		return dataTable;
	}

	private SelectHandler createSelectHandler(final Table table,final DataTable dataTable) {
		return new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				// TODO Auto-generated method stub
				// Window.alert("selected....");
				// getColumnSelection();
				String message = "";
				JsArray<Selection> selections = table.getSelections();
				for (int i = 0; i < selections.length(); i++) {
					if (selections.get(i).isRow()) {
						String id = dataTable.getFormattedValue(
								selections.get(i).getRow(), 1);
						// Window.alert("row with id " + id +
						// "has been selected:");
						message += (id + " ");
					}

				}
				// Window.alert("message: "+ message);
				/*
				 * if(selections.length() == 1){ Selection selection =
				 * selections.get(0); if(selection.isRow()){ String id =
				 * dataTable.getFormattedValue(selection.getRow(), 1);
				 * //Window.alert("row with id " + id + "has been selected:");
				 * 
				 * 
				 * } }
				 */

			}
		};
	}

	public native void getColumnSelection(String checkboxId)/*-{
															
															}-*/;

	public final native NodeList<Element> querySelector(String selector)/*-{
		return $wnd.document.querySelectorAll(selector);
	}-*/;
}
