
import android.app.Activity;
import android.util.TypedValue;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import java.net.URL;

public class Util {

	public static int dpToPx(Activity activity, float dp) {
		return (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_DIP,
			dp,
			activity.getResources().getDisplayMetrics()
		);
	}

	public static void loadImage(final ImageView view, final String name) {
		new AsyncTask<Object, Object, Object>() {
			public Object doInBackground(Object... arguments) {
				try {
					final Bitmap bitmap = BitmapFactory.decodeStream(
						new URL("http://192.168.1.6/" + name)
							.openStream()
					);
					((Activity) view.getContext())
						.runOnUiThread(new Runnable() {
							public void run() {
								view.setImageBitmap(bitmap);
							}
						});
				} catch (Exception e) {
				}
				return null;
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
}
