package kill_app;

import utils.Utils;
import android.os.RemoteException;
import android.widget.GridLayout;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class LaunchSettings extends UiAutomatorTestCase {

	public void testDemo() throws UiObjectNotFoundException {
		try {
			getUiDevice().wakeUp();
		} catch (RemoteException e1) { // not a big deal
			e1.printStackTrace();
		}
		
		String pkg = getParams().getString("package");
		if (pkg != null) {
			String[] cmds = { "am force-stop " + pkg };
			// Don't know why, but need to do the command twice (otherwise
			// doesn't work)
			Utils.runAsRoot(cmds);
			Utils.runAsRoot(cmds);
			return;
		}

		String app = getParams().getString("app");
		assertTrue("No argument 'app'", app != null && !app.isEmpty());

		// we can give a # to avoid spaces in app param
		String appName = app.replace('#', ' ');
		assertTrue("Oooops",
				Utils.openApp(this, "Settings", "com.android.settings"));
		// Click on Apps
		Utils.click(Utils.getObjectWithClassName("android.widget.LinearLayout",
				22));

		UiScrollable settingsList = new UiScrollable(
				new UiSelector().scrollable(true));
		Utils.click(settingsList, GridLayout.class.getName(), appName);
		Utils.clickAndWaitForNewWindow(Utils.getObjectWithText("Force stop"));
		Utils.click(Utils.getObjectWithText("OK"));
		getUiDevice().pressHome();
	}
}
