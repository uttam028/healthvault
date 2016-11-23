package cse.mlab.hvr.client.fragments.old;

public class SimpleTextFragment extends Fragment{
	private String text;
	public SimpleTextFragment(String text, int duration) {
		// TODO Auto-generated constructor stub
		super(duration, "text");
		this.text = text;
	}
	public SimpleTextFragment(String text, int duration, String instructionText) {
		// TODO Auto-generated constructor stub
		super(duration, "text", instructionText);
		this.text = text;
	}
	public String getText() {
		return text;
	}
}
