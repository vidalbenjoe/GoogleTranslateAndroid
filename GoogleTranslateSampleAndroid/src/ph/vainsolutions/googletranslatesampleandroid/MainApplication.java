package ph.vainsolutions.googletranslatesampleandroid;

import android.app.Application;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainApplication extends Application {
	   public enum TrackerName {
	        APP_TRACKER, // Tracker used only in this app.
	        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
	        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
	    }
	    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

	    public synchronized Tracker getTracker(TrackerName trackerId) {
	        if (!mTrackers.containsKey(trackerId)) {

	            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
	            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker("UA-3837494-13")
	                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.analytics)
	                    : analytics.newTracker(R.xml.analytics);
	            mTrackers.put(trackerId, t);

	        }
	        return mTrackers.get(trackerId);
	    }
}
