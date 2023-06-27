package CucumberRunner;

import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cucumber.listener.Reporter;

import helper.ConfigFileReader;
import io.cucumber.testng.TestNGCucumberRunner;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;

//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//import io.cucumber.testng.FeatureWrapper;
//import io.cucumber.testng.PickleWrapper;


@CucumberOptions(
        features = "src/test/java/Features",
        glue = {"StepDefinitions"},
        tags="@testApp",//@Watchlist//@Welcome//@Exchange//@Demorun
        //dryRun = true,
        monochrome= true,
        plugin = {
                "pretty","html:target/cucumber-reports/cucumber-pretty/cucumber.html",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "junit:target/cucumber-reports/CucumberTestReport.xml",
        		//"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:target/cucumber-reports/report.html"
        }
        )

public class FeatureRunner {
	
	    private TestNGCucumberRunner testNGCucumberRunner;
	
	    /*
	     * AppiumDriverFactory appiumDriverFactory =
	     * AppiumDriverFactory.getInstanceOfAppiumDriverFactory(); AppiumDriver driver =
	     * appiumDriverFactory.getDriver();
	     */
	
	    
	    @BeforeClass(alwaysRun = true)
	    public void setUpClass() throws Exception {
	        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
//		    ExtentReports extent = new ExtentReports();
//		    extent.attachReporter(new ExtentHtmlReporter("/AppiumReact/target/extentReports/report.html"));
//		    extent.setSystemInfo("Test Machine", "localhost");
//	        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
//	        extent.setSystemInfo("User Name", System.getProperty("user.name"));
//	        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
	    }
	    
//	    @BeforeMethod
//	    public void beforeMethod(Method method) {
//	        test = extent.createTest(method.getName());
//	        test.assignAuthor("John Doe");
//	        test.assignCategory("Regression");
//	        test.addCustomField("Build", "1.0.0");
//	    }

	    
	    //New -- for io.cucumber
	    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
	    public void scenario(PickleWrapper pickle, FeatureWrapper cucumberFeature) {
	        testNGCucumberRunner.runScenario(pickle.getPickle());
	    }


	
	    @DataProvider
	    public Object[][] scenarios() {
	    	 if(testNGCucumberRunner == null){
	    	        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	    	 }
	        return testNGCucumberRunner.provideScenarios();
	    }

	    @AfterClass(alwaysRun = true)
	    public void tearDownClass() {
	    	Reporter.loadXMLConfig(new File(ConfigFileReader.getConfigPropertyVal("reportConfigPath")));
	        testNGCucumberRunner.finish();
	    }
}
