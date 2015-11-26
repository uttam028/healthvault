package cse.mlab.hvr.client;

public class Fragment {

	String text;
	int duration;
	String type;
	int speed = 0;
	String imageUrl;
	
	
	public Fragment(String text, int duration, String type, int speed, String imageUrl) {
		// TODO Auto-generated constructor stub
		this.text = text;
		this.duration = duration;
		this.type = type;
		this.speed = speed;
		this.imageUrl = imageUrl;
	}
	public int getDuration() {
		return duration;
	}
	public String getText() {
		return text;
	}
	public String getType() {
		return type;
	}
	public int getSpeed() {
		return speed;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
}
