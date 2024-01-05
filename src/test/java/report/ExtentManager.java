package report;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public ExtentManager() {
    }

    public static ExtentReports createInstance() {
        if (extent == null) {
            extent = new ExtentReports();
            ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("target/test-classes/extent.html");
            extent.attachReporter(new ExtentReporter[]{htmlReporter});
        }

        return extent;
    }
}
