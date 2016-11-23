package cse.mlab.hvr.client.fragments.old;

public class MarkedTextFragment extends Fragment{
	private String text;
	private String markedText;
	public MarkedTextFragment(String text, String markedText, int duration) {
		// TODO Auto-generated constructor stub
		super(duration, "markedtext");
		this.text = text;
		this.markedText = markedText;
	}

	public MarkedTextFragment(String text, String markedText, int duration, String instructionText) {
		// TODO Auto-generated constructor stub
		super(duration, "markedtext", instructionText);
		this.text = text;
		this.markedText = markedText;
	}
	public String getText() {
		return text;
	}
	public String getMarkedText() {
		return markedText;
	}
}
