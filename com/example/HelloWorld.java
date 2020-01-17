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

public class HelloWorld extends Activity {

	private String url = "http://192.168.1.6";

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
		Button button = new Button(this);
		button.setText("Load");
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				new Loader().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
			}
		});
		return button;
	}

	private class Loader extends AsyncTask<Void, Void, Void> {

		public Void doInBackground(Void... arguments) {
			HelloWorld.this.runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(HelloWorld.this, "URL: " + url, Toast.LENGTH_SHORT)
						.show();
				}
			});
			return null;
		}
	}
}
