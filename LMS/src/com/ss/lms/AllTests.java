package com.ss.lms;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;


@RunWith(JUnitPlatform.class)
@SelectPackages({"com.ss.lms.dao","com.ss.lms.service","com.ss.lms.ui"})
public class AllTests {

}

