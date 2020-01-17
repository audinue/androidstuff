
import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.Typeface;

public class Home extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle b) {
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.addView(createHeader());
		layout.addView(createBody());
		layout.addView(createFooter());
		return layout;
	}

	private View createHeader() {
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setGravity(Gravity.CENTER_VERTICAL);
		int padding = Util.dpToPx(getActivity(), 10);
		layout.setPadding(padding, padding, padding, padding);
		layout.addView(createLogoImage());
		layout.addView(createLogoText());
		return layout;
	}

	private View createBody() {
		FrameLayout layout = new FrameLayout(getActivity());
		layout.setId(2);
		layout.setLayoutParams(new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.MATCH_PARENT,
			1f
		));
		return layout;
	}

	private View createFooter() {
		LinearLayout layout = new LinearLayout(getActivity());
		int padding = Util.dpToPx(getActivity(), 10);
		layout.setPadding(padding, padding, padding, padding);
		layout.setBackgroundColor(0xffeeeeee);
		layout.addView(createButton("Home", "ic_home_black_48dp.png"));
		layout.addView(createButton("Search", "ic_search_black_48dp.png"));
		layout.addView(createButton("Community", "ic_chat_black_48dp.png"));
		layout.addView(createButton("Profile", "ic_person_black_48dp.png"));
		return layout;
	}

	private View createLogoImage() {
		ImageView image = new ImageView(getActivity());
		int size = Util.dpToPx(getActivity(), 30);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
		int margin = Util.dpToPx(getActivity(), 5);
		params.setMargins(0, 0, margin, 0);
		image.setLayoutParams(params);
		Util.loadImage(image, "ic_photo_camera_black_48dp.png");
		return image;
	}

	private View createLogoText() {
		TextView text = new TextView(getActivity());
		text.setText("PhotoSpot");
		text.setTextSize(32);
		text.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
		return text;
	}

	private View createButton(String label, String image) {
		LinearLayout layout = new LinearLayout(getActivity());
		// int padding = Util.dpToPx(getActivity(), 10);
		// layout.setPadding(padding, padding, padding, padding);
		layout.setLayoutParams(new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT,
			1
		));
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER);
		layout.addView(createButtonImage(image));
		layout.addView(createButtonLabel(label));
		return layout;
	}

	private View createButtonImage(String image) {
		ImageView view = new ImageView(getActivity());
		int size = Util.dpToPx(getActivity(), 24);
		view.setLayoutParams(new LinearLayout.LayoutParams(size, size));
		Util.loadImage(view, image);
		return view;
	}

	private View createButtonLabel(String label) {
		TextView text = new TextView(getActivity());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT
		);
		// int margin = Util.dpToPx(getActivity(), 3);
		// params.setMargins(0, margin, 0, 0);
		text.setLayoutParams(params);
		text.setText(label);
		return text;
	}
}
