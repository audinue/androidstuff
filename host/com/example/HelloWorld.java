package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.text.TextWatcher;
import android.text.Editable;

import java.net.URL;
import java.util.jar.Manifest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.InputStream;
import dalvik.system.DexClassLoader;
import android.app.FragmentManager;
import android.app.Fragment;

public class HelloWorld extends Activity {

	private String url = "http://192.168.1.6";

	private Runnable enableButton;

	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(createContentView());
	}

	public View createContentView() {
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.addView(createHeader());
		layout.addView(createBody());
		return layout;
	}

	public View createHeader() {
		LinearLayout layout = new LinearLayout(this);
		layout.setGravity(Gravity.CENTER);
		layout.addView(createText());
		layout.addView(createButton());
		return layout;
	}

	public View createBody() {
		FrameLayout layout = new FrameLayout(this);
		layout.setId(1);
		return layout;
	}

	public View createText() {
		EditText text = new EditText(this);
		text.setText(url);
		text.setSelection(url.length());
		text.setLayoutParams(new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT,
			1
		));
		text.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable editable) {}
			
			public void beforeTextChanged(CharSequence string, int start, int count, int after) {}

			public void onTextChanged(CharSequence string, int start, int before, int count) {
				url = String.valueOf(string);
			}
		});
		return text;
	}

	public View createButton() {
		final Button button = new Button(this);
		button.setText("Load");
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				button.setEnabled(false);
				HelloWorld.this.getFragmentManager()
					.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
				new Loader()
					.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
			}
		});
		enableButton = new Runnable() {
			public void run() {
				button.setEnabled(true);
			}
		};
		return button;
	}

	private class Loader extends AsyncTask<Void, Void, Void> {

		public Void doInBackground(Void... arguments) {
			// 1. Download play.mf
			try {
				final String className = new Manifest(new URL(url + "/play.mf").openStream())
					.getMainAttributes()
					.getValue("Fragment");
				if (className == null) {
					throw new IllegalArgumentException("Fragment is undefined.");
				}
				InputStream in = new URL(url + "/classes.dex").openStream();
				final File dexFile = new File(HelloWorld.this.getFilesDir(), "classes.dex");
				OutputStream out = new FileOutputStream(dexFile);
				byte[] buffer = new byte[4096];
				while (true) {
					int read = in.read(buffer);
					if (read == -1) {
						break;
					}
					out.write(buffer, 0, read);
				}
				out.close();
				in.close();
				HelloWorld.this.runOnUiThread(new Runnable() {
					public void run() {
						try {
							String dexPath = dexFile.getAbsolutePath();
							String cachePath = HelloWorld.this.getCacheDir().getAbsolutePath();
							ClassLoader baseLoader = HelloWorld.this.getClassLoader();
							HelloWorld.this.getFragmentManager()
								.beginTransaction()
								.replace(1, (Fragment) new DexClassLoader(dexPath, cachePath, null, baseLoader)
									.loadClass(className)
									.newInstance())
								.commit();
						} catch (Throwable t) {
							Toast.makeText(HelloWorld.this, "ERROR: " + t.getMessage(), Toast.LENGTH_LONG)
								.show();
						}
						enableButton.run();
					}
				});
			} catch (final Exception e) {
				HelloWorld.this.runOnUiThread(new Runnable() {
					public void run() {
						enableButton.run();
						Toast.makeText(HelloWorld.this, "ERROR: " + e.getMessage(), Toast.LENGTH_LONG)
							.show();
					}
				});
			}
			// 2. Read Fragment attribute
			// 3. Download classes.dex to files
			return null;
		}
	}
}
