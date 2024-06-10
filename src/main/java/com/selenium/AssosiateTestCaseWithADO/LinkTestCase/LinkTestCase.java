package com.selenium.AssosiateTestCaseWithADO.LinkTestCase;
/**
 * This File will define required paratmeter helpful in making connection in hooks file of selenium project
 ** @author Vivek Singh Bhatnagar
 * @date 09 june 2024
 * current working as Principal Software Engineer[QC] in Powerserv Technologies
 * https://github.com/keviv72/
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode; 
import com.fasterxml.jackson.databind.ObjectMapper; 
import java.io.File; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class LinkTestCase{
	 private final String azureDevopsURL = "https://dev.azure.com/";
	 private final String organisationName;
	 private final String projectName;
	 private final String userPAT;
	 private final File   jsonFile;
	 private TestPlanBeans testPlanBeans = null;
	 private Results result = new Results();
	 private EventRequestBody evenRequestBody = new EventRequestBody();
	 private HashMap<Integer, List<Integer>> suites = null;
	 
	public LinkTestCase(String orgName, String projName, String token, File validJson) throws JsonProcessingException, IOException {
		  if (orgName == null && projName == null && token == null && validJson == null) {
	            throw new IllegalArgumentException("Alert! You miss OrganisationName or projectName or Personal access Token[PAT] as described in Readme text file");
	        }
	        else
	        {
		        this.organisationName = addSpaceInOrganisatioOrProjectName(orgName);
		        this.projectName = addSpaceInOrganisatioOrProjectName(projName);
		        this.userPAT = token;
		        this.jsonFile = validJson;
		    }
		  readJsonFile(validJson);
	}

	private String addSpaceInOrganisatioOrProjectName(String name)
	{
		name = name.trim();
		int loop=0;
		String splitName[] = name.split(" "); 
	   	if(splitName.length > 1)
	   	{
	   		name ="";
	   	 for (String value : splitName) {
	   		 if(loop == splitName.length-1)
	   		 {
	   			 name+=value;
	   			 break;	 
	   		 }
	   		name+= value+"%20";
	   		loop++;
	   	 }
	   	}
	   	return name;
	}
	
    public int UpdateStatus(int testCaseID , String status) throws IOException, InterruptedException
    {
    	int key;
    	int statusCode;
    	if (testCaseID >0 && status == null) {
            throw new IllegalArgumentException("Alert! You miss testCaseID! or status! as described in Readme text file");
        }
    	else if((statusCode =getStatusCode(status))==0)
    	{ 
    		throw new IllegalArgumentException("Alert! You Enter wrong status! as described in Readme text file");
        }
    	else if((key = getKeyofparticularTestcase(testCaseID,testPlanBeans.getAllSuites())) == 0){
    		throw new IllegalArgumentException("Alert! You miss Test case ID in Test plan with valid suits ID as described in Readme text file");	
    	}
    	 String AuthStr = ":" + userPAT;
         Base64 base64 = new Base64();
         int planID = testPlanBeans.getTestPlanId();
         result.setOutcome(statusCode);
         evenRequestBody.setId(testCaseID);
         evenRequestBody.setResult(result);
         ArrayList<EventRequestBody> arrayList = new ArrayList<>();
         arrayList.add(evenRequestBody);
         ObjectMapper objectMapper = new ObjectMapper();
         String jsonBody = objectMapper.writeValueAsString(arrayList);
//         String jsonInputString = "{\"id\": "+testCaseID+",\"results\": {\"outcome\": "+statusCode+"}}";
         String encodedPAT = new String(base64.encode(AuthStr.getBytes()));
         HttpRequest request = HttpRequest.newBuilder()
                 .uri(URI.create(azureDevopsURL+ organisationName+"/"+projectName+ "/_apis/testplan/Plans/"+planID+"/Suites/"+key+"/TestPoint?includePointDetails=true&returnIdentityRef=true"))
                 .header("accept", "application/json;api-version=7.1-preview.2;excludeUrls=true;enumsAsNumbers=true;msDateFormat=true;noArrayWrap=true")
                 .header("Authorization", "Basic " + encodedPAT)
                 .header("content-type", "application/json")
                 .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody))
                 .build();
         HttpResponse<String> response = HttpClient.newHttpClient()
                 .send(request, HttpResponse.BodyHandlers.ofString());
         return response.statusCode();
    }
    
    private int getKeyofparticularTestcase(int testCaseID,HashMap<Integer, List<Integer>> TestCaseList)
    {
    	int key = 0;
    	  for (Integer keys : TestCaseList.keySet()) {
    		  for (Integer value : TestCaseList.get(keys)) 
    		   	  if(testCaseID == (int)(value))
            		  return keys.intValue();	  
          }
		return key;
    }
    private int getStatusCode(String status)
    {
    	switch(status){
        case "PASS":    return 2;
        case "FAIL":    return 3;
        case "BLOCKED": return 7;
        case "NOTA":    return 11;
        default:        return 0;  
        }
    }
    private TestPlanBeans readJsonFile(File verifyJson) throws JsonProcessingException, IOException
    {
    	 testPlanBeans = new TestPlanBeans();
    	 suites =  new HashMap<Integer, List<Integer>>();
    	 ObjectMapper objectMapper = new ObjectMapper(); 
         JsonNode jsonNode = objectMapper.readTree(verifyJson).get("testplan"); 
         testPlanBeans.setTestPlanId(jsonNode.get("testplanid").asInt());
         jsonNode = jsonNode.get("suite");
         List<Integer> TestCaseList = new  ArrayList<Integer>();
         if (jsonNode.isArray()) {
                   for (JsonNode node : jsonNode) {             
            		String splitTCs[] = node.get("allTestCasesAssosiatedwithSuite").asText().split(","); 
               	 for (String value : splitTCs) {
               		TestCaseList.add(Integer.valueOf(value));
                 }
              	 suites.put(Integer.valueOf(node.get("suiteId").toString()),TestCaseList); 
               	TestCaseList = new  ArrayList<Integer>();
             }
         }
         testPlanBeans.setAllSuites(suites);
         return testPlanBeans;
    }
}
