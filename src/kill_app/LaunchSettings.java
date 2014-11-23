package kill_app;

import utils.Utils;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class LaunchSettings extends UiAutomatorTestCase {

	public void testDemo() throws UiObjectNotFoundException {
		String app = getParams().getString("app");
		assertTrue("No argument 'app'", app != null && !app.isEmpty());

		Utils.killApp(this, app);
		Utils.killTcpdump();
	}
}
