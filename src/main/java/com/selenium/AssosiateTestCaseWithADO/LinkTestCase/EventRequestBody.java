package com.selenium.AssosiateTestCaseWithADO.LinkTestCase;
/**
 * This File will define required paratmeter helpful in sending patch to ADO[Azure Devops]
 ** @author Vivek Singh Bhatnagar
 * @date 09 june 2024
 * current working as Principal Software Engineer[QC] in Powerserv Technologies
 * https://github.com/keviv72/
 */

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
