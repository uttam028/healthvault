package cse.mlab.hvr.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.gwtbootstrap3.client.shared.event.ModalHiddenEvent;
import org.gwtbootstrap3.client.shared.event.ModalShowEvent;
import org.gwtbootstrap3.client.shared.event.ModalShowHandler;
import org.gwtbootstrap3.client.shared.event.ModalShownEvent;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.TextArea;
import org.gwtbootstrap3.client.ui.constants.ButtonDismiss;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.Toggle;
import org.gwtbootstrap3.client.ui.html.Br;
import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.extras.datepicker.client.ui.DatePicker;
import org.gwtbootstrap3.extras.datepicker.client.ui.base.events.ChangeDateEvent;
import org.gwtbootstrap3.extras.datepicker.client.ui.base.events.ChangeDateHandler;
import org.gwtbootstrap3.extras.datepicker.client.ui.base.events.ClearDateEvent;
import org.gwtbootstrap3.extras.datepicker.client.ui.base.events.ClearDateHandler;
import org.gwtbootstrap3.extras.notify.client.constants.NotifyType;
import org.gwtbootstrap3.extras.notify.client.ui.Notify;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.DataView;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.Table.Options;
import com.google.gwt.visualization.client.visualizations.Table.Options.Policy;

import cse.mlab.hvr.shared.Medication;
import cse.mlab.hvr.shared.MedicationList;
import cse.mlab.hvr.shared.Response;

public class Medications extends Composite {

	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

	@UiField
	HTMLPanel medicationPanel, dynamicMedicPanel;
	@UiField
	Modal medicationModal;

	@UiField
	Button buttonAddMedic, buttonSubmit, buttonDeleteMedic, buttonEditMedic, buttonEndMedic, buttonAddAnother;

//	@UiField
//	TextBox nameBox;

	

	@UiField
	TextArea reasonArea;


	@UiField
	DatePicker startDatePicker;

	@UiField
	DatePicker endDatePicker;


//	@UiField
//	Label nameLabel;


	@UiField
	Label startDateLabel;

	@UiField
	Label endDateLabel;


//	@UiField
//	Label reasonLabel;


	Div medicationDiv;

	private String medName = "";
	private String medReason = "";
	private String startDateString = "";
	private String endDateString = "";

	private boolean nameError = false;
//	private boolean reasonError = false;
	private boolean startDateError = false;
	private boolean endDateError = false;

	private String emailId;
	private Boolean tableLoaded = false;

	private MedicationList medicationList = new MedicationList();
	Table medicationTable = new Table();
	DataView dataView  = null;

	private boolean modalFromEdit = false;
	private String editMedicationId = "";

	// public MessageServiceAsync messageService =
	// GWT.create(MessageService.class);

	/*
	 * @UiTemplate is not mandatory but allows multiple XML templates to be used
	 * for the same widget. Default file loaded will be <class-name>.ui.xml
	 */

	@UiTemplate("Medications.ui.xml")
	interface LoginUiBinder extends UiBinder<Widget, Medications> {
	}

	/*
	 * @UiField(provided = true) final LoginResources res;
	 */
	public Medications(String emailId) {
		// this.res = GWT.create(LoginResources.class);
		// res.style().ensureInjected();
		initWidget(uiBinder.createAndBindUi(this));
		this.emailId = emailId;
		medicationDiv = new Div();
		medicationDiv.setId("medication_div");
		
		buttonAddMedic.setIcon(IconType.PLUS);
		buttonEndMedic.setIcon(IconType.UNLINK);
		buttonEditMedic.setIcon(IconType.EDIT);
		buttonDeleteMedic.setIcon(IconType.REMOVE);

		medicationModal.addShowHandler(new ModalShowHandler() {

			@Override
			public void onShow(ModalShowEvent evt) {
				// TODO Auto-generated method stub

			}
		});
		startDatePicker.addChangeDateHandler(new ChangeDateHandler() {

			@Override
			public void onChangeDate(ChangeDateEvent evt) {
				// TODO Auto-generated method stub
				startDateString = startDatePicker.getBaseValue();
				String[] dates = startDateString.split("-");
				int year = Integer.parseInt(dates[0]);
				startDateLabel.setText(Integer.toString(year));
				if ((year < 2000) || (year > 2030)) {
					startDateLabel
							.setText("Pick a valid date");
					startDateError = true;
				} else {
					startDateLabel.setText("");
					startDateError = false;
				}
			}
		});

		startDatePicker.addClearDateHandler(new ClearDateHandler() {

			@Override
			public void onClearDate(ClearDateEvent evt) {
				// TODO Auto-generated method stub
				startDateLabel.setText("You can not leave it empty");
			}
		});

		endDatePicker.addChangeDateHandler(new ChangeDateHandler() {

			@Override
			public void onChangeDate(ChangeDateEvent evt) {
				// TODO Auto-generated method stub
				endDateString = endDatePicker.getBaseValue();
			}
		});

	}

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		if (!tableLoaded) {
			
			dynamicMedicPanel.add(new MedicationUnit(true));

			// TODO Auto-generated method stub
			medicationPanel.add(medicationDiv);
			medicationPanel.add(new Br());
			greetingService.getMedicationsList(Medications.this.emailId,
					new AsyncCallback<MedicationList>() {
						@Override
						public void onSuccess(MedicationList medicList) {
							// TODO Auto-generated method stub
							// clickTest(result, medicationDiv.getId());
							// addMedicationsToHashmap(result);
							medicationList = medicList;
							medicationDiv.add(medicationTable);
							medicationPanel.add(medicationDiv);
							drawMedicationTable();
							tableLoaded = true;
						}

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

						}
					});

		}
	}

	public native void clickTest(String jsonData, String divId)/*-{
		$wnd.sayHello(jsonData, divId);
	}-*/;

	/*
	 * String getDateString(Date date) { try { String pattern = "yyyy-MM-dd";
	 * DateTimeFormat format = DateTimeFormat.getFormat(pattern); String
	 * date_str = format.format(date); return date_str; } catch (Exception ex) {
	 * return ""; } }
	 */

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

	boolean startDateCheck(String startDate) {
		String[] dates = startDate.split("-");
		int year = Integer.parseInt(dates[0]);
		if ((year < 2000) || (year > 2030)) {
			startDateLabel.setText("Pick a valid date");
			return true;
		} else {
			startDateLabel.setText("");
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
		medName = "";
		medReason = "";
		startDateString = "";
		endDateString = "";

		nameError = false;
//		reasonError = false;
		startDateError = false;
		endDateError = false;

//		nameBox.setText("");

		reasonArea.setText("");

		startDatePicker.setValue(null);
		endDatePicker.setValue(null);

		startDateLabel.setText("");
//		nameLabel.setText("");
		endDateLabel.setText("");
//		reasonLabel.setText("");
	}

	void populateAllFields(Medication medication) {
		
		
		dynamicMedicPanel.clear();
		dynamicMedicPanel.add(new MedicationUnit(false, medication.getName(), medication.getDosage(), medication.getFrequency()));
		buttonAddAnother.setVisible(false);

		medName = medication.getName();
		medReason = medication.getReason();
		startDateString = "";
		endDateString = "";

		nameError = false;
//		reasonError = false;
		startDateError = false;
		endDateError = false;

//		nameBox.setText(medication.getName());
		reasonArea.setText(medication.getReason());

		startDatePicker.setValue(getDateFromString(medication.getStartDate()));
		endDatePicker.setValue(getDateFromString(medication.getEndDate()));

		startDateLabel.setText("");
//		nameLabel.setText("");
		endDateLabel.setText("");
//		reasonLabel.setText("");
	}

	@UiHandler("buttonSubmit")
	void doClickSubmit(ClickEvent event) {
		// messageService.getMessage(message1, new MessageCallBack());
		
		nameError = false;
		for(int i=0;i<dynamicMedicPanel.getWidgetCount();i++){
			if(dynamicMedicPanel.getWidget(i) instanceof MedicationUnit){
				boolean temp = ((MedicationUnit) dynamicMedicPanel.getWidget(i)).validate();
				if(temp == false){
					nameError = true;
				}
			}
		}
		
		
//		if (medName.isEmpty()) {
//			nameLabel.setText("Medicine name is a required field.");
//			nameError = true;
//		}
//		if (medReason.isEmpty()) {
//			reasonLabel.setText("Medical reason is a required field.");
//			reasonError = true;
//		}
		if (startDateString.isEmpty()) {
			startDateLabel.setText("Start date is a required field.");
			startDateError = true;
		} else {
			/*
			 * String dateString = getDateString(startDatePicker.getValue());
			 * if(dateString.isEmpty()){ startDateError = true;
			 * startDateLabel.setText("Date is not valid"); } else {
			 * startDateString = dateString; }
			 */
			if (validateDateFormat(startDateString)) {
				startDateError = false;
				startDateLabel.setText("");
			} else {
				startDateError = true;
				startDateLabel.setText("Date is not valid");
			}
		}

		if (endDateString.isEmpty()) {
			endDateError = false;
			endDateLabel.setText("");
		} else {
			if (validateDateFormat(endDateString)) {
				endDateError = false;
				endDateLabel.setText("");
			} else {
				endDateError = true;
				endDateLabel.setText("Date is not valid");
			}
		}


		if (nameError == true || startDateError == true ) {
			
		} else {
			final MedicationList tempMedicationList = new MedicationList();
			final Medication medicationToEdit = getMedicationObject(editMedicationId);
			
			//long medicId = 0;
			
			if (modalFromEdit) {
				
				if (medicationToEdit == null) {
					return;
				}
			} 
			
			
			
			for(int i=0;i<dynamicMedicPanel.getWidgetCount();i++){
				if(dynamicMedicPanel.getWidget(i) instanceof MedicationUnit){
					Medication temp = new Medication();
					if(modalFromEdit){
						temp.setId(medicationToEdit.getId());
					}
					temp.setEmail(emailId);
					temp.setName(((MedicationUnit) dynamicMedicPanel.getWidget(i)).getName());
					temp.setDosage(((MedicationUnit) dynamicMedicPanel.getWidget(i)).getDosage());
					temp.setFrequency(((MedicationUnit) dynamicMedicPanel.getWidget(i)).getFrequency());
					temp.setReason(reasonArea.getText().trim());
					temp.setStartDate(startDateString);
					temp.setEndDate(endDateString);
					
					tempMedicationList.getMedicationList().add(temp);
				}
			}
			
			buttonSubmit.setDataDismiss(ButtonDismiss.MODAL);
			
			
			if(modalFromEdit){
				greetingService.updateMedication(tempMedicationList.getMedicationList().get(0), new AsyncCallback<Response>() {
					@Override
					public void onSuccess(Response result) {
						// TODO Auto-generated method stub
						if(result.getCode()==0){
							//success
							resetAllFields();
							int index = medicationList.getMedicationList().indexOf(medicationToEdit);
							medicationList.getMedicationList().set(index, tempMedicationList.getMedicationList().get(0));
							drawMedicationTable();
						} else {
							//failed to update
							//Window.alert("Service not available. Try later.");
							Notify.notify("Service not available. Try later.", NotifyType.DANGER);
						}
					}
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Notify.notify("Service not available. Try later.", NotifyType.DANGER);
					}
				});
			} else {
				greetingService.saveMedication(tempMedicationList,
						new AsyncCallback<Response>() {

							@Override
							public void onSuccess(Response result) {
								// TODO Auto-generated method stub
								if (result.getCode() == 0) {
									
									try {
										JsArrayString tokens = split(result.getMessage(), "_|_");
										for(int i=0;i<tokens.length();i++){
											tempMedicationList.getMedicationList().get(i).setId(Long.parseLong(tokens.get(i)));
											medicationList.getMedicationList().add(0, tempMedicationList.getMedicationList().get(i));											
										}
										
//										Window.alert("length:"+ tokens.length() + ", total:"+ medicationList.getMedicationList().size());
										
									} catch (Exception e) {
										// TODO: handle exception
									}
									
									//medicationModal.hi
									resetAllFields();
									drawMedicationTable();
								}else {
//									Window.alert("Service not available. Try later.");
									Notify.notify("Service not available. Try later.", NotifyType.DANGER);
								}
							}

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								Notify.notify("Service not available. Try later.", NotifyType.DANGER);
							}
						});				
			}

		}

	}


//	@UiHandler("nameBox")
//	void handleLoginChange(ValueChangeEvent<String> event) {
//		nameBox.setMaxLength(40);
//		medName = event.getValue();
//		if (medName.length() > 40) {
//			nameError = true;
//			nameLabel.setText("String must be less than 40 characters.");
//
//		} else if (event.getValue().matches("^-?\\d+$")) {
//			nameLabel.setText("Please do not enter a number.");
//			nameError = true;
//		} else {
//			nameLabel.setText("");
//			nameError = false;
//		}
//	}


//	@UiHandler("reasonArea")
//	void handleReasonChange(ValueChangeEvent<String> event) {
//		reasonArea.getElement().setAttribute("maxlength", "100");
//		String reason = reasonArea.getValue();
//		if (reason.length() > 100) {
//			reasonLabel
//					.setText("Reason category cannot exceed 100 characters.");
//			reasonError = true;
//		} else {
//			medReason = reason;
//			reasonLabel.setText("");
//			reasonError = false;
//		}
//	}

	@UiHandler("medicationModal")
	void modalShown(ModalShownEvent event){
		if (modalFromEdit == false) {
			resetAllFields();
		} else {
			populateAllFields(getMedicationObject(editMedicationId));
		}
	}
	@UiHandler("medicationModal")
	void modalHidden(ModalHiddenEvent event){
		modalFromEdit = false;
	}
	
	@UiHandler("buttonAddAnother")
	void addAnotherMedication(ClickEvent event){
		int medicCount = 0;
		for(int i=0;i<dynamicMedicPanel.getWidgetCount();i++){
			if(dynamicMedicPanel.getWidget(i) instanceof MedicationUnit){
				medicCount = medicCount + 1;
			}
		}
		if(medicCount > 0){
			dynamicMedicPanel.add(new MedicationUnit(true));
		} else {
			dynamicMedicPanel.add(new MedicationUnit(false));
		}
	}

	@UiHandler("buttonAddMedic")
	public void addMedicationModal(ClickEvent event) {
		buttonAddAnother.setVisible(true);
		dynamicMedicPanel.clear();
		dynamicMedicPanel.add(new MedicationUnit(false));
		
		buttonAddMedic.setDataToggle(Toggle.MODAL);
		//buttonAddMedic.setDataTargetWidget(medicationModal);
		buttonAddMedic.setDataTarget("#"+medicationModal.getId());
		modalFromEdit = false;

	}

	@UiHandler("buttonDeleteMedic")
	public void deleteSelectedMedications(ClickEvent event) {
		// for instant let's say a string appended with |
		String list = "";
		// get id's of selected list
		ArrayList<String> checkedMedics = getCheckedMedics();
		if (checkedMedics.size() == 0) {
			//Window.alert("Select an item to delete");
			Notify.notify("Select an item to delete", NotifyType.WARNING);
		} else {
			for (int i = 0; i < checkedMedics.size(); i++) {
				Medication checkedMedicObject = getMedicationObject(checkedMedics
						.get(i));
				list += (checkedMedicObject.getId() + "|");
				medicationList.getMedicationList().remove(checkedMedicObject);
			}
			list = list.substring(0, list.length() - 1);
			greetingService.deleteMedications(this.emailId, list,
					new AsyncCallback<Response>() {
						@Override
						public void onSuccess(Response result) {
							// TODO Auto-generated method stub
							if (result.getCode() == 0) {
								// Window.alert("success to delete");
							} else {
								// Window.alert("failed to delete");
							}
							drawMedicationTable();
						}

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							//Window.alert("failed to delete:" + caught.getMessage());
							Notify.notify("Service not available. Try later.", NotifyType.DANGER);
						}
					});
		}
	}

	@UiHandler("buttonEditMedic")
	void editSelectedMedication(ClickEvent event) {
		
		ArrayList<String> checkedMedics = getCheckedMedics();
		if (checkedMedics.size() == 0) {
			//Window.alert("Select an item to edit");
			Notify.notify("Select an item to edit", NotifyType.WARNING);
		} else if (checkedMedics.size() > 1) {
			Notify.notify("Select one item to edit", NotifyType.WARNING);
		} else {
			//Window.alert("Checked value :"+ checkedMedics.get(0));
			editMedicationId = checkedMedics.get(0);
			buttonEditMedic.setDataToggle(Toggle.MODAL);
			//buttonEditMedic.setDataTargetWidget(medicationModal);
			buttonEditMedic.setDataTarget("#"+medicationModal.getId());
						
			modalFromEdit = true;
		}
	}

	@UiHandler("buttonEndMedic")
	void endSelectedMedic(ClickEvent event) {
		ArrayList<String> checkedMedics = getCheckedMedics();
		if (checkedMedics.size() == 0) {
			Notify.notify("Select an item to edit", NotifyType.WARNING);
		} else if (checkedMedics.size() > 1) {
			Notify.notify("Select one item to edit", NotifyType.WARNING);
		} else {
			Date date = new Date();
			DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd");
			Medication checkedMedicObject = getMedicationObject(checkedMedics
					.get(0));
			String endDate = dtf.format(date).toString();
			checkedMedicObject.setEndDate(endDate);
			// Window.alert("Checked value :"+ dtf.format(date).toString() + " "
			// + checkedMedicObject.toString());
			greetingService.stopUsingMedication(checkedMedicObject.getId(),
					endDate, new AsyncCallback<Response>() {
						@Override
						public void onSuccess(Response result) {
							// TODO Auto-generated method stub
							drawMedicationTable();
							if (result.getCode() == 0) {
								// success
							} else {
								// failure;
							}
						}

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							//Window.alert("Service not available");
							Notify.notify("Service not available, please try later.", NotifyType.DANGER);
						}
					});
		}
	}

	protected ArrayList<String> getCheckedMedics() {
		NodeList<Element> nodelist = querySelector("[id^=medcheckbox]");
		ArrayList<String> checkedMedics = new ArrayList<>();
		for (int i = 0; i < nodelist.getLength(); i++) {
			boolean checked = nodelist.getItem(i).getPropertyBoolean("checked");
			if (checked) {
				checkedMedics.add(nodelist.getItem(i).getId().split("-")[1]);
			}
		}
		return checkedMedics;
	}

	protected Medication getMedicationObject(String id) {
		if(id.isEmpty())
			return null;
		
		List<Medication> list = medicationList.getMedicationList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == Long.parseLong(id)) {
				return list.get(i);
			}
		}
		return null;
	}

	public void drawMedicationTable() {
		if(medicationList.getMedicationList().size() == 0){
			medicationTable.setVisible(false);
			buttonEditMedic.setVisible(false);
			buttonDeleteMedic.setVisible(false);
			buttonEndMedic.setVisible(false);
		} else {
			medicationTable.setVisible(true);			
			buttonEditMedic.setVisible(true);
			buttonDeleteMedic.setVisible(true);
			buttonEndMedic.setVisible(true);
			dataView = DataView.create(getDataTableFromList());
			dataView.hideColumns(new int[]{1});
			medicationTable.draw(dataView, getTableOptions());			
		}
		// medicationTable.addSelectHandler(createSelectHandler(medicationTable,
		// dataTable));
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
		dataTable.addColumn(ColumnType.STRING, "Name");
		dataTable.addColumn(ColumnType.STRING, "Frequency");
		dataTable.addColumn(ColumnType.STRING, "Dosage");
		dataTable.addColumn(ColumnType.STRING, "Reason for Taking");
		dataTable.addColumn(ColumnType.STRING, "Date Started");
		dataTable.addColumn(ColumnType.STRING, "Date Ended");
//		dataTable.addColumn(ColumnType.STRING, "Dose");
//		dataTable.addColumn(ColumnType.STRING, "Strength");
//		dataTable.addColumn(ColumnType.STRING, "Notes");

		dataTable.addRows(medicationList.getMedicationList().size());
		int columnIndex = 0;
		int rowIndex = 0;
		List<Medication> medList = medicationList.getMedicationList();
		Iterator<Medication> it = medList.iterator();
		while (it.hasNext()) {
			Medication med = (Medication) it.next();
			String checkbox = "<input type=\"checkbox\" text-align=\"center\"id=\"medcheckbox-"
					+ med.getId() + "\">";
			dataTable.setValue(rowIndex, columnIndex++, checkbox);
			dataTable.setValue(rowIndex, columnIndex++, med.getId());
			dataTable.setValue(rowIndex, columnIndex++, med.getName());
			dataTable.setValue(rowIndex, columnIndex++, med.getFrequency());
			dataTable.setValue(rowIndex, columnIndex++, med.getDosage());
			dataTable.setValue(rowIndex, columnIndex++, med.getReason());
			dataTable.setValue(rowIndex, columnIndex++, med.getStartDate());
			dataTable.setValue(rowIndex, columnIndex++,
					med.getEndDate() == null ? "" : med.getEndDate());

			rowIndex += 1;
			columnIndex = 0;
		}
		return dataTable;
	}

	private SelectHandler createSelectHandler(final Table table,
			final DataTable dataTable) {
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

	public static final native JsArrayString split(String string, String separator) /*-{
		return string.split(separator);
	}-*/;

}
