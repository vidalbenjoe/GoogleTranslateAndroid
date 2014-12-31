package ph.vainsolutions.googletranslatesampleandroid;

import com.google.android.gms.ads.AdView;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class MainActivity extends ActionBarActivity {
	GoogleTranslateMainActivity translator;
	EditText translateedittext;
	TextView translatabletext;
	Tracker t;
	private AdView adView;
	/* Your ad unit id. Replace with your actual ad unit id. */
	private static final String AD_UNIT_ID = "ca-app-pub-9496331727688071/4042379549";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		adView = new AdView(MainActivity.this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId(AD_UNIT_ID);

		LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
		layout.addView(adView);

		// Create an ad request. Check logcat output for the hashed device ID to
		// get test ads on a physical device.
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(
				AdRequest.DEVICE_ID_EMULATOR)
		// .addTestDevice("DB634F3F277702D4B952D2AD08A66DD6")//acer v730
				.build();

		// Start loading the ad in the background.
		adView.loadAd(adRequest);

		t = ((MainApplication) getApplication())
				.getTracker(MainApplication.TrackerName.APP_TRACKER);
		// Set screen name.
		t.setScreenName(getLocalClassName());

		// Send a screen view.
		t.send(new HitBuilders.AppViewBuilder().build());

		translateedittext = (EditText) findViewById(R.id.translateedittext);
		Button translatebutton = (Button) findViewById(R.id.translatebutton);
		translatebutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new EnglishToTagalog().execute();

			}
		});
	}

	private class EnglishToTagalog extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progress = null;

		protected void onError(Exception ex) {

		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				translator = new GoogleTranslateMainActivity(
						"AIzaSyDWFR8MyCDerdE4ZqEFmfyfA2HJ2EYNgFw");

				Thread.sleep(1000);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPreExecute() {
			// start the progress dialog
			progress = ProgressDialog.show(MainActivity.this, null,
					"Translating...");
			progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progress.setIndeterminate(true);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result) {
			progress.dismiss();

			super.onPostExecute(result);
			translated();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

	}

	public void translated() {

		String translatetotagalog = translateedittext.getText().toString();// get
																			// the
																			// value
																			// of
																			// text
		String text = translator.translte(translatetotagalog, "en", "tl");
		translatabletext = (TextView) findViewById(R.id.translatabletext);
		translatabletext.setText(text);

	}

	/** Called when leaving the activity */
	@Override
	public void onPause() {
		if (adView != null) {
			adView.pause();
		}
		super.onPause();
	}

	/** Called when returning to the activity */
	@Override
	public void onResume() {
		super.onResume();
		if (adView != null) {
			adView.resume();
		}
	}

	/** Called before the activity is destroyed */
	@Override
	public void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}

}
