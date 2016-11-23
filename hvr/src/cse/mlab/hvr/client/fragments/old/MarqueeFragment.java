package cse.mlab.hvr.client.fragments.old;

public class MarqueeFragment extends Fragment{
	private String text;
	private int speed = 0;
	public MarqueeFragment(String text, int duration, int speed) {
		// TODO Auto-generated constructor stub
		super(duration, "marquee");
		this.text = text;
		this.speed = speed;
	}
	public MarqueeFragment(String text, int duration, int speed, String instructionText) {
		// TODO Auto-generated constructor stub
		super(duration, "marquee", instructionText);
		this.text = text;
		this.speed = speed;
	}
	public String getText() {
		return text;
	}
	public int getSpeed() {
		return speed;
	}
}
