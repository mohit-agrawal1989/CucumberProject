package steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import utility.DriverTestCase;
import utility.ExecutionLog;

import java.io.IOException;

public class ServiceHooks extends DriverTestCase {

    ExecutionLog log = new ExecutionLog();

    @Before
    public void initializeTest(Scenario scenario) throws Exception {
        log.logAddClass(scenario.getName());
        Runtime.getRuntime().addShutdownHook(new ProcessorHook());
        setup("application.properties");
    }

    @After
    public void shutDownHook(Scenario scenario) throws InterruptedException, IOException {
        if(scenario.isFailed()){
            getScreenshot(scenario.getName());
        }
    }

    class ProcessorHook extends Thread {

        @Override
        public void run() {
            exit();
        }
    }
}
