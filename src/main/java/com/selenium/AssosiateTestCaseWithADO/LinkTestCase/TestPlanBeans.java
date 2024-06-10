package com.selenium.AssosiateTestCaseWithADO.LinkTestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This File will define required paratmeter helpful in loading Test Plan id, Suit id as a key and test case as value
 ** @author Vivek Singh Bhatnagar
 * @date 09 june 2024
 * current working as Principal Software Engineer[QC] in Powerserv Technologies
 * https://github.com/keviv72/
 */

public class TestPlanBeans {

	private  int testPlanId;
	private HashMap<Integer, List<Integer>> allSuites;
	public int getTestPlanId() {
		return testPlanId;
	}
	public HashMap<Integer, List<Integer>> getAllSuites() {
		return allSuites;
	}
	public void setAllSuites(HashMap<Integer, List<Integer>> allSuites) {
		this.allSuites = allSuites;
	}
	public void setTestPlanId(int testPlanId) {
		this.testPlanId = testPlanId;
	}


	
}
