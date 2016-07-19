package cse.mlab.hvr.client.fragments;

public class ImageFragment extends Fragment {
	private String imageUrl;
	public ImageFragment(String imageUrl, int duration) {
		// TODO Auto-generated constructor stub
		super(duration, "image");
		this.imageUrl = imageUrl;
	}

	public ImageFragment(String imageUrl, int duration, String instructionText) {
		// TODO Auto-generated constructor stub
		super(duration, "image", instructionText);
		this.imageUrl = imageUrl;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

}
