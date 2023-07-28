package de.flower.common.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;



public class ExceptionLoggerTestListener extends TestListenerAdapter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void onTestFailure(ITestResult tr) {
        log.error(tr.toString(), tr.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        log.error(tr.toString(), tr.getThrowable());
    }
}