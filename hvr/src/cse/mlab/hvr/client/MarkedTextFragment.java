package cse.mlab.hvr.client;

public class MarkedTextFragment extends Fragment{
	String text;
	String markedText;
	int duration;
	public MarkedTextFragment(String text, String markedText, int duration) {
		// TODO Auto-generated constructor stub
		super(text, duration, "markedtext", 0, "");
		this.text = text;
		this.markedText = markedText;
		this.duration = duration;
	}
	
	public String getMarkedText() {
		return markedText;
	}
}
