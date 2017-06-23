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
import org.gwtbootstrap3.extras.datepicker.client.ui.base.events.ClearDateEvent;
import org.gwtbootstrap3.extras.datepicker.client.ui.base.events.ClearDateHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
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
	HTMLPanel medicationPanel;
	@UiField
	Modal medicationModal;

	@UiField
	Button buttonAddMedic, buttonSubmit, buttonDeleteMedic, buttonEditMedic, buttonEndMedic;

	@UiField
	TextBox nameBox;

	
//	@UiField
//	TextBox strengthBox;
//
//	@UiField
//	TextBox dosBox;
//
//	@UiField
//	TextBox freqBox;
//
//	@UiField
//	TextBox prescribedBox;
//
//	@UiField
//	TextBox quantBox;

	@UiField
	TextArea reasonArea;

//	@UiField
//	TextArea InstructArea;
//
//	@UiField
//	TextArea noteArea;
//
//	@UiField
//	ListBox StrengthList;
//
//	@UiField
//	ListBox dosList;
//
//	@UiField
//	ListBox methodList;
//
//	@UiField
//	ListBox prescribList;
//	
	

	@UiField
	DatePicker startDatePicker;

	@UiField
	DatePicker endDatePicker;

//	@UiField
//	DatePicker prescribDatePicker;

	@UiField
	Label nameLabel;

//	@UiField
//	Label dosLabel;

//	@UiField
//	Label strengthLabel;

	@UiField
	Label startDateLabel;

	@UiField
	Label endDateLabel;

//	@UiField
//	Label prescribDateLabel;
//
//	@UiField
//	Label strengthListLabel;

//	@UiField
//	Label dosListLabel;
//
//	@UiField
//	Label methodListLabel;

//	@UiField
//	Label freqLabel;

	@UiField
	Label reasonLabel;

//	@UiField
//	Label prescribLabel;
//
//	@UiField
//	Label prescribedLabel;
//
//	@UiField
//	Label instructLabel;
//
//	@UiField
//	Label quantLabel;
//
//	@UiField
//	Label noteLabel;

	Div medicationDiv;

	private String medName = "";
//	private String medFreq = "";
//	private String medPrescrib = "";
//	private String medQuant = "";
	private String medReason = "";
//	private String medInstruct = "";
//	private String medNote = "";
//	private String medStrengthLabel = "";
//	private String medDosLabel = "";
//	private String medMethod = "";
//	private String medPrescribList = "";
//	private String strenString = "";
//	private String dosString = "";
	private String startDateString = "";
	private String endDateString = "";
//	private String prescribeDateString = "";

//	private int strenVal;
//	private int dosVal;
//	private int strenListVal;
//	private int dosListVal;
//	private int methodListVal;
//	private int prescribListVal;

	private boolean nameError = false;
//	private boolean strengthError = false;
//	private boolean strenListError = false;
//	private boolean dosError = false;
//	private boolean dosListError = false;
//	private boolean methodListError = false;
//	private boolean freqError = false;
	private boolean reasonError = false;
	private boolean startDateError = false;
	private boolean endDateError = false;
//	private boolean prescribeDateError = false;
//	private boolean prescribError = false;
//	private boolean prescribedError = false;
//	private boolean instructError = false;
//	private boolean quantError = false;
//	private boolean noteError = false;

	private String emailId;
	private Boolean tableLoaded = false;

	// private HashMap<Long, Medication> medicationMap = new HashMap<Long,
	// Medication>();
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

//		prescribDatePicker.addChangeDateHandler(new ChangeDateHandler() {
//
//			@Override
//			public void onChangeDate(ChangeDateEvent evt) {
//				// TODO Auto-generated method stub
//				prescribeDateString = prescribDatePicker.getBaseValue();
//			}
//		});

	}

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		if (!tableLoaded) {

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
//		medFreq = "";
//		medPrescrib = "";
//		medQuant = "";
		medReason = "";
//		medInstruct = "";
//		medNote = "";
//		medStrengthLabel = "";
//		medDosLabel = "";
//		medMethod = "";
//		medPrescribList = "";
//		strenString = "";
//		dosString = "";
		startDateString = "";
		endDateString = "";
//		prescribeDateString = "";

//		strenVal = 0;
//		dosVal = 0;
//		strenListVal = 0;
//		dosListVal = 0;
//		methodListVal = 0;
//		prescribListVal = 0;

		nameError = false;
//		strengthError = false;
//		strenListError = false;
//		dosError = false;
//		dosListError = false;
//		methodListError = false;
//		freqError = false;
		reasonError = false;
		startDateError = false;
		endDateError = false;
//		prescribeDateError = false;
//		prescribError = false;
//		prescribedError = false;
//		instructError = false;
//		quantError = false;
//		noteError = false;

		nameBox.setText("");
//		strengthBox.setText("");
//		dosBox.setText("");
//		freqBox.setText("");
//		prescribedBox.setText("");
//		quantBox.setText("");

		reasonArea.setText("");
//		InstructArea.setText("");
//		noteArea.setText("");
//
//		StrengthList.setSelectedIndex(0);
//		dosList.setSelectedIndex(0);
//		methodList.setSelectedIndex(0);
//		prescribList.setSelectedIndex(0);

		startDatePicker.setValue(null);
		endDatePicker.setValue(null);
//		prescribDatePicker.setValue(null);

		startDateLabel.setText("");
//		dosLabel.setText("");
		nameLabel.setText("");
//		strengthLabel.setText("");
		endDateLabel.setText("");
//		prescribDateLabel.setText("");
//		strengthListLabel.setText("");
//		dosListLabel.setText("");
//		methodListLabel.setText("");
//		freqLabel.setText("");
		reasonLabel.setText("");
//		prescribLabel.setText("");
//		prescribedLabel.setText("");
//		instructLabel.setText("");
//		quantLabel.setText("");
//		noteLabel.setText("");

	}

	void populateAllFields(Medication medication) {
		medName = medication.getName();
//		medFreq = medication.getConsumeFrequency();
//		medPrescrib = medication.getPrescribedBy();
//		medQuant = medication.getPrescribedQuantity();
		medReason = medication.getReason();
//		medInstruct = medication.getInstruction();
//		medNote = medication.getNote();
//		medStrengthLabel = medication.getStrengthUnit();
//		medDosLabel = medication.getDosageUnit();
//		medMethod = medication.getConsumeProcess();
//		medPrescribList = medication.getIsPrescribed();
//		strenString = String.valueOf(medication.getStrength());
//		dosString = String.valueOf(medication.getDosageUnit());
		startDateString = "";
		endDateString = "";
//		prescribeDateString = "";

//		strenVal = medication.getStrength();
//		dosVal = medication.getDosage();
//		strenListVal = getIndexFromList(StrengthList, medication.getStrengthUnit());
//		dosListVal = getIndexFromList(dosList, medication.getDosageUnit());
//		methodListVal = getIndexFromList(methodList, medication.getConsumeProcess());
//		prescribListVal = getIndexFromList(prescribList, medication.getIsPrescribed());

		nameError = false;
//		strengthError = false;
//		strenListError = false;
//		dosError = false;
//		dosListError = false;
//		methodListError = false;
//		freqError = false;
		reasonError = false;
		startDateError = false;
		endDateError = false;
//		prescribeDateError = false;
//		prescribError = false;
//		prescribedError = false;
//		instructError = false;
//		quantError = false;
//		noteError = false;

		nameBox.setText(medication.getName());
//		strengthBox.setText(String.valueOf(medication.getStrength()));
//		dosBox.setText(String.valueOf(medication.getDosage()));
//		freqBox.setText(medication.getConsumeFrequency());
//		prescribedBox.setText(medication.getPrescribedBy());
//		quantBox.setText(medication.getPrescribedQuantity());

		reasonArea.setText(medication.getReason());
//		InstructArea.setText(medication.getInstruction());
//		noteArea.setText(medication.getNote());

		
//		StrengthList.setSelectedIndex(strenListVal);
//		dosList.setSelectedIndex(dosListVal);
//		methodList.setSelectedIndex(methodListVal);
//		prescribList.setSelectedIndex(prescribListVal);

		startDatePicker.setValue(getDateFromString(medication.getStartDate()));
		endDatePicker.setValue(getDateFromString(medication.getEndDate()));
//		prescribDatePicker.setValue(getDateFromString(medication.getPrescribedDate()));

		startDateLabel.setText("");
//		dosLabel.setText("");
		nameLabel.setText("");
//		strengthLabel.setText("");
		endDateLabel.setText("");
//		prescribDateLabel.setText("");
//		strengthListLabel.setText("");
//		dosListLabel.setText("");
//		methodListLabel.setText("");
//		freqLabel.setText("");
		reasonLabel.setText("");
//		prescribLabel.setText("");
//		prescribedLabel.setText("");
//		instructLabel.setText("");
//		quantLabel.setText("");
//		noteLabel.setText("");

	}

	@UiHandler("buttonSubmit")
	void doClickSubmit(ClickEvent event) {
		// messageService.getMessage(message1, new MessageCallBack());
		if (medName.isEmpty()) {
			nameLabel.setText("Medicine name is a required field.");
			nameError = true;
		}
//		if (strenString.isEmpty()) {
//			strengthLabel.setText("Strength category is a required field.");
//			strengthError = true;
//		}
//		if (medStrengthLabel.isEmpty()) {
//			strengthListLabel
//					.setText("Strength unit category is a required field.");
//			strenListError = true;
//		}
//
//		if (dosString.isEmpty()) {
//			dosLabel.setText("Dosage amount category is a required field.");
//			dosError = true;
//		}
//		if (medDosLabel.isEmpty()) {
//			dosListLabel.setText("Dosage unit category is a required field.");
//			dosListError = true;
//		}
//		if (medMethod.isEmpty()) {
//			methodListLabel
//					.setText("Method of consumption is a required field.");
//			methodListError = true;
//		}
		if (medReason.isEmpty()) {
			reasonLabel.setText("Medical reason is a required field.");
			reasonError = true;
		}
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

//		if (prescribeDateString.isEmpty()) {
//			prescribeDateError = false;
//			prescribDateLabel.setText("");
//		} else {
//			if (validateDateFormat(prescribeDateString)) {
//				prescribeDateError = false;
//				prescribDateLabel.setText("");
//			} else {
//				prescribeDateError = true;
//				prescribDateLabel.setText("Date is not valid");
//			}
//		}
//		if (medPrescribList.isEmpty()) {
//			prescribLabel
//					.setText("Method of prescription is a required field.");
//			prescribError = true;
//		}

		if (nameError == true /*|| strengthError == true || dosError == true
				|| dosListError == true || methodListError == true
				|| freqError == true*/ || reasonError == true
				|| startDateError == true /*|| prescribError == true
				|| prescribedError == true || instructError == true
				|| quantError == true || noteError == true*/) {
			/*
			 * Window.alert("Name:" + nameError + ", streng:" + strengthError +
			 * "dos:" + dosError + ", dos_list:" + dosListError + "method:" +
			 * methodListError + "freq:" + freqError + ", reason:" + reasonError
			 * + "start:" + startDateError + ", prescrib:" + prescribError +
			 * ", prescribed:" + prescribedError + ", instruct:" + instructError
			 * + ", note:" + noteError + "quant:" + quantError);
			 */
			//Window.alert("Please enter valid input");
		} else {
			final Medication medication;
			if (modalFromEdit) {
				medication = getMedicationObject(editMedicationId);
				if (medication == null) {
					return;
				}
			} else {
				medication = new Medication();
			}
			medication.setEmail(emailId);
			medication.setName(medName);
//			medication.setStrength(strenVal);
//			medication.setStrengthUnit(medStrengthLabel);
//			medication.setDosage(dosVal);
//			medication.setDosageUnit(medDosLabel);
//			medication.setConsumeProcess(medMethod);
//			medication.setConsumeFrequency(medFreq);
			medication.setReason(medReason);
//			medication.setIsPrescribed(medPrescribList);
//			medication.setPrescribedBy(medPrescrib);
//			medication.setInstruction(medInstruct);
//			medication.setPrescribedQuantity(medQuant);
//			medication.setNote(medNote);

			// Date endDate = endDatePicker.getValue();
			// getEndDateString(endDate);
			// Date prescribDate = perscribDatePicker.getValue();

			medication.setStartDate(startDateString);
			medication.setEndDate(endDateString);
//			medication.setPrescribedDate(prescribeDateString);
			
			buttonSubmit.setDataDismiss(ButtonDismiss.MODAL);
			// resetAllFields();

			// medication.setEndDate(end_date_str);
			// medication.setPrescribedDate(prescrib_date_str);
			
			
			if(modalFromEdit){
				greetingService.updateMedication(medication, new AsyncCallback<Response>() {
					@Override
					public void onSuccess(Response result) {
						// TODO Auto-generated method stub
						if(result.getCode()==0){
							//success
							resetAllFields();
							drawMedicationTable();
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
				greetingService.saveMedication(medication,
						new AsyncCallback<Response>() {

							@Override
							public void onSuccess(Response result) {
								// TODO Auto-generated method stub
								if (result.getCode() == 0) {
									medication.setId(Long.parseLong(result.getMessage()));
									medicationList.getMedicationList().add(0, medication);
									//medicationModal.hi
									resetAllFields();
									drawMedicationTable();
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

	/*
	 * void getPrescribDateString(Date date){ String pattern = "yyyy-MM-dd";
	 * DateTimeFormat format = DateTimeFormat.getFormat(pattern); String
	 * date_str = format.format(date); String[] dates = date_str.split("-"); int
	 * year = Integer.parseInt(dates[0]);
	 * 
	 * if((year < 1920) || (year > 2016)){ prescrib_warning =
	 * "Invalid prescibed year\n";
	 * prescribDateLabel.setText("Pick a year between 1920 and 2016."); }else{
	 * prescribDateLabel.setText(" "); prescrib_date_str = date_str; } }
	 */

	/*
	 * void getEndDateString(Date date) { String pattern = "yyyy-MM-dd";
	 * DateTimeFormat format = DateTimeFormat.getFormat(pattern); String
	 * date_str = format.format(date); String[] dates = date_str.split("-"); int
	 * year = Integer.parseInt(dates[0]);
	 * 
	 * if ((year < 2016) || (year > 2100)) { String end_warning =
	 * "Invalid ending year\n";
	 * endDateLabel.setText("Pick a year between 2016 and 2100."); } else {
	 * endDateLabel.setText(" "); end_date_str = date_str; } }
	 */

	@UiHandler("nameBox")
	void handleLoginChange(ValueChangeEvent<String> event) {
		nameBox.setMaxLength(40);
		medName = event.getValue();
		if (medName.length() > 40) {
			nameError = true;
			nameLabel.setText("String must be less than 40 characters.");

		} else if (event.getValue().matches("^-?\\d+$")) {
			nameLabel.setText("Please do not enter a number.");
			nameError = true;
		} else {
			nameLabel.setText("");
			nameError = false;
		}
	}

//	@UiHandler("strengthBox")
//	void handleNumChange(ValueChangeEvent<String> event) {
//		int medStren;
//		strenString = event.getValue();
//		try {
//			medStren = Integer.parseInt(strenString);
//			if (strenString.length() > 5) {
//				strengthLabel.setText("Please enter a value less than 99,999.");
//				strengthError = true;
//			} else if (medStren == 0) {
//				strengthLabel.setText("Please enter a number that is not 0.");
//				strengthError = true;
//			} else {
//				strenVal = medStren;
//				strengthLabel.setText("");
//				strengthError = false;
//			}
//		} catch (NumberFormatException e) {
//			strengthLabel.setText("Please enter a numerical value.");
//			strengthError = true;
//		}
//	}

//	@UiHandler("dosBox")
//	void handleNum2Change(ValueChangeEvent<String> event) {
//		int medDos;
//		dosString = event.getValue();
//		try {
//			medDos = Integer.parseInt(dosString);
//			if (dosString.length() > 5) {
//				dosLabel.setText("Please enter a value less than 99,999.");
//				dosError = true;
//			} else if (medDos == 0) {
//				dosLabel.setText("Please enter a number that is not 0.");
//				dosError = true;
//			} else {
//				dosVal = medDos;
//				dosLabel.setText("");
//				dosError = false;
//			}
//		} catch (NumberFormatException e) {
//			dosLabel.setText("Please enter a numerical value.");
//			dosError = true;
//		}
//	}
//
//	@UiHandler("freqBox")
//	void handleFreqChange(ValueChangeEvent<String> event) {
//		freqBox.setMaxLength(40);
//		String freq = event.getValue();
//		if (freq.length() > 40) {
//			freqLabel.setText("The answer must be under 40 characters long.");
//			freqError = true;
//		} else {
//			medFreq = freq;
//			freqLabel.setText("");
//			freqError = false;
//		}
//	}
//
//	@UiHandler("prescribedBox")
//	void handlePerscribChange(ValueChangeEvent<String> event) {
//		prescribedBox.setMaxLength(40);
//		String prescrib = event.getValue();
//		if (prescrib.length() > 40) {
//			prescribedLabel
//					.setText("The answer must be under 40 characters long.");
//			prescribedError = true;
//		} else {
//			medPrescrib = prescrib;
//			prescribedLabel.setText("");
//			prescribedError = false;
//		}
//	}
//
//	@UiHandler("quantBox")
//	void handleQuantChange(ValueChangeEvent<String> event) {
//		quantBox.setMaxLength(100);
//		String quant = event.getValue();
//		if (quant.length() > 100) {
//			quantLabel.setText("Quantity must not exceed 100 characters.");
//			quantError = true;
//		} else {
//			medQuant = quant;
//			quantLabel.setText("");
//			quantError = false;
//		}
//	}

	@UiHandler("reasonArea")
	void handleReasonChange(ValueChangeEvent<String> event) {
		reasonArea.getElement().setAttribute("maxlength", "100");
		String reason = reasonArea.getValue();
		if (reason.length() > 100) {
			reasonLabel
					.setText("Reason category cannot exceed 100 characters.");
			reasonError = true;
		} else {
			medReason = reason;
			reasonLabel.setText("");
			reasonError = false;
		}
	}

//	@UiHandler("InstructArea")
//	void handleInstructChange(ValueChangeEvent<String> event) {
//		InstructArea.getElement().setAttribute("maxlength", "100");
//		String instruct = InstructArea.getValue();
//		if (instruct.length() > 100) {
//			instructLabel
//					.setText("Intruction category cannot exceed 100 characters.");
//			instructError = true;
//		} else {
//			medInstruct = instruct;
//			instructLabel.setText("");
//			instructError = false;
//		}
//	}
//
//	@UiHandler("noteArea")
//	void handleNotesChange(ValueChangeEvent<String> event) {
//		noteArea.getElement().setAttribute("maxlength", "100");
//		String note = noteArea.getValue();
//		if (note.length() > 100) {
//			noteLabel.setText("Note category cannot exceed 100 characters.");
//			noteError = true;
//		} else {
//			medNote = note;
//			noteLabel.setText("");
//			noteError = false;
//		}
//	}
//
//	@UiHandler("StrengthList")
//	void handleStrenListChange(ChangeEvent event) {
//		strenListVal = StrengthList.getSelectedIndex();
//		medStrengthLabel = StrengthList.getItemText(strenListVal);
//		if (medStrengthLabel.matches("Select...")) {
//			strengthListLabel
//					.setText("Strength unit category is a required field.");
//			strenListError = true;
//		} else {
//			strengthListLabel.setText("");
//			strenListError = false;
//		}
//	}
//
//	@UiHandler("dosList")
//	void handleDosListChange(ChangeEvent event) {
//		dosListVal = dosList.getSelectedIndex();
//		medDosLabel = dosList.getItemText(dosListVal);
//		if (medDosLabel.matches("Select...")) {
//			dosListLabel.setText("Dosage unit category is a required field.");
//			dosListError = true;
//		} else {
//			dosListLabel.setText("");
//			dosListError = false;
//		}
//	}
//
//	@UiHandler("methodList")
//	void handleMethodListChange(ChangeEvent event) {
//		methodListVal = methodList.getSelectedIndex();
//		medMethod = methodList.getItemText(methodListVal);
//		if (medMethod.matches("Select...")) {
//			methodListLabel
//					.setText("Method of comsumption is a required field.");
//			methodListError = true;
//		} else {
//			methodListLabel.setText("");
//			methodListError = false;
//		}
//	}
//
//	@UiHandler("prescribList")
//	void handlePrescribListChange(ChangeEvent event) {
//		prescribListVal = prescribList.getSelectedIndex();
//		medPrescribList = prescribList.getItemText(prescribListVal);
//		if (medPrescribList.matches("Select...")) {
//			prescribLabel
//					.setText("Method of prescription is a required field.");
//			prescribError = true;
//		} else {
//			prescribLabel.setText("");
//			prescribError = false;
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

	@UiHandler("buttonAddMedic")
	public void addMedicationModal(ClickEvent event) {
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
			Window.alert("Select an item to delete");
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
							Window.alert("failed to delete:" + caught.getMessage());
						}
					});
		}
	}

	@UiHandler("buttonEditMedic")
	void editSelectedMedication(ClickEvent event) {
		ArrayList<String> checkedMedics = getCheckedMedics();
		if (checkedMedics.size() == 0) {
			Window.alert("Select an item to edit");
		} else if (checkedMedics.size() > 1) {
			Window.alert("Select one item to edit");
		} else {
			// Window.alert("Checked value :"+ checkedMedics.get(0));
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
			Window.alert("Select an item to edit");
		} else if (checkedMedics.size() > 1) {
			Window.alert("Select one item to edit");
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
							Window.alert("Service not available");
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
			dataTable.setValue(rowIndex, columnIndex++, med.getReason());
			dataTable.setValue(rowIndex, columnIndex++, med.getStartDate());
			dataTable.setValue(rowIndex, columnIndex++,
					med.getEndDate() == null ? "" : med.getEndDate());
//			dataTable.setValue(
//					rowIndex,
//					columnIndex++,
//					String.valueOf(med.getDosage()) + "  "
//							+ med.getDosageUnit());
//			dataTable.setValue(
//					rowIndex,
//					columnIndex++,
//					String.valueOf(med.getStrength()) + "  "
//							+ med.getStrengthUnit());
//			dataTable.setValue(rowIndex, columnIndex++, med.getNote());

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

}
