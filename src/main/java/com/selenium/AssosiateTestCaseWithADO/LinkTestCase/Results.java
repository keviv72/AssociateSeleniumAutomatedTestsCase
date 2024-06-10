package com.selenium.AssosiateTestCaseWithADO.LinkTestCase;

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
