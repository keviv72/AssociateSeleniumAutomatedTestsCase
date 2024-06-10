package com.selenium.AssosiateTestCaseWithADO.LinkTestCase;
/**
 * This File will define required paratmeter helpful in sending patch to ADO[Azure Devops]
 ** @author Vivek Singh Bhatnagar
 * @date 09 june 2024
 * current working as Principal Software Engineer[QC] in Powerserv Technologies
 * https://github.com/keviv72/
 */
import com.fasterxml.jackson.annotation.JsonProperty;

public class Results {
	@JsonProperty("outcome")
	private int outcome;

	public int getOutcome() {
		return outcome;
	}

	public void setOutcome(int outcome) {
		this.outcome = outcome;
	}
}
