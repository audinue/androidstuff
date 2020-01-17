
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

public class Hello extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle b) {
		TextView text = new TextView(getActivity());
		text.setText("Now what??");
		return text;
	}
}
