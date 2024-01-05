package report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportListener implements ITestListener {
    private ExtentReports extent = ExtentManager.createInstance();
    private ThreadLocal<ExtentTest> test = new ThreadLocal();

    public ExtentReportListener() {
    }

    public void onStart(ITestContext context) {
    }

    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = this.extent.createTest(result.getMethod().getMethodName());
        this.test.set(extentTest);
    }

    public void onTestSuccess(ITestResult result) {
        ((ExtentTest)this.test.get()).pass("Test passed");
    }

    public void onTestFailure(ITestResult result) {
        ((ExtentTest)this.test.get()).fail(result.getThrowable());
    }

    public void onTestSkipped(ITestResult result) {
        ((ExtentTest)this.test.get()).skip(result.getThrowable());
    }

    public void onFinish(ITestContext context) {
        this.extent.flush();
    }
}