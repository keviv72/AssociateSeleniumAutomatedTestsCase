# AssociateSeleniumAutomatedTestsCase
Associate Azure Devops test Plan test cases with test cases of selenium project -@author Vivek Singh Bhatnagar


Devops :  https://dev.azure.com/{organization}/{project}/_apis/testplan

Please find Steps to achieve your desired goal ->

Step 1: Add Jar to your project Build Path ->configure Build path or directly add required classes from [https://github.com/keviv72/AssociateSeleniumAutomatedTestsCase/]

Step 2: Create LinkTestCase class member into your hooks file of selenium project

        2.1 Initialization above variable in Setup one of function of hooks file [LinkTestCase linkTestCase = null].

            2.1.1 new LinkTestCase("organization Name","project name", "create personal access token from azure devops token",new File("Valid JSON File path")).

        2.2 Update status of your test case in teardown one of function in hooks file of selenium project.

            2.2.1 call function linkTestCase.updateStatus(Test Case Id of your test case in ADO,"Status").

            2.2.2 to know your test case fail or pass in tear down function -> scenario.getStatus();.

    
        
Please find steps to make TestPlanJSON

Step 1: Select Test Plans [in above browser url get PlanID]

Step 2: select Suits to view more test cases [in above browser url get suitID]

Step 3: Go and execute test case one by one "PASS", "FAIL", "BLOCK", "NOTA"

        3.1 Gether all test case ID in below json under which suits using inspect element 

Step 4: Put all test cases under suitID


Note: In Devops only one Test Plan with multiple suiteID and multiple testcaseID under one suiteID.



for example:     
/Plans/{PlanID}/Suites/{suitID}/TestPoint?includePointDetails=true&returnIdentityRef=true

{
  
  "testplan": {
    "testplanid": 12345,
    "suite": [
         {
           "suiteId":324,
            "allTestCasesAssosiatedwithSuite": "2345,34345,4544,445435,565465,7565,35435,5643543,56465,5655"
         },
         {
        
           "suiteId":325,
            "allTestCasesAssosiatedwithSuite": "2345,34345,4544,445435,565465,7565,35435,5643543,56465,5655"
         }
    ]
  }
}
