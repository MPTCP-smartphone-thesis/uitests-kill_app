package kill_app;

import utils.Utils;
import android.os.RemoteException;
import android.widget.GridLayout;

import com.android.uiautomator.core.UiObject;
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

		String sim = getParams().getString("sim");
		// Check if "SIM card added" is available. If yes, restart the phone
		if (sim != null && !sim.isEmpty()) {
			// First, check the title, not just 'button1'
			UiObject alertSim = Utils.getObjectWithId("android:id/alertTitle");
			assertTrue("No 'SIM card added' warning", alertSim != null
					&& alertSim.getText().equals("SIM card added"));

			UiObject restart = Utils.getObjectWithId("android:id/button1");
			assertTrue("No 'Restart' button", restart != null
					&& restart.getText().equals("Restart")
					&& restart.getPackageName().equals("com.android.phone"));

			restart.click();
			return;
		}

		String pkg = getParams().getString("package");
		// Force-stop via 'am' command
		if (pkg != null && !pkg.isEmpty()) {
			String[] cmds = { "am force-stop " + pkg };
			// Don't know why, but need to do the command twice (otherwise
			// doesn't work)
			Utils.runAsRoot(cmds);
			Utils.runAsRoot(cmds);
			return;
		}

		String tv = getParams().getString("tvaccept");
		if (tv != null && !tv.isEmpty()) {
			Utils.getObjectWithId("com.teamviewer.quicksupport.market:id/tvDialogInputButtonPositive").click();
			return;
		}

		String app = getParams().getString("app");
		// Force-stop via Settings app
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
