package kill_app;

import utils.Utils;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class LaunchSettings extends UiAutomatorTestCase {

	public void testDemo() throws UiObjectNotFoundException {
		try {
			getUiDevice().wakeUp();
		} catch (RemoteException e1) { // not a big deal
			e1.printStackTrace();
		}

		String app = getParams().getString("app");
		assertTrue("No argument 'app'", app != null && !app.isEmpty());

		// we can give a # to avoid spaces in app param
		Utils.killApp(this, app.replace('#', ' '));
		getUiDevice().pressHome();
	}
}
