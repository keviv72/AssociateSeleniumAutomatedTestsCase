package com.selenium.AssosiateTestCaseWithADO.LinkTestCase;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventRequestBody {

    @JsonProperty("id")
	private int id;
    @JsonProperty("results")
	private Results result;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Results getResult() {
		return result;
	}
	public void setResult(Results result) {
		this.result = result;
	}    
}
