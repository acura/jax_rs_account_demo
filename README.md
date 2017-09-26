Developer's Guide for AccountDemo:-

	Get the project from github repository

		-- URL to download project : https://github.com/acura/account-jax-demo.git

	Import the project to IntelliJ IDE

		-- Import the project in your selected workspace.
		-- Update it using maven reimport so that it will download the jars if any left.

	Adding tomcat to your IntelliJ run configuration

		  To configure the tomcat:

		--Click on 'Run>Edit Configurations>Configure',browse and select your tomcat installation directory.
		--Click OK.
		--Select server version under 'Application Server'.
		--Under 'On 'Update' action' select 'restart server'.
		--Select JRE version 1.8 and give path where your Jdk is installed.
		--Check port number under 'HTTP port' and port number under 'open browser' is same.
		--Click on 'Apply'and 'OK'.
		--Click on 'Run>Edit Configurations>Deployment' on the right side of the server,then click on the '+' icon.
		  Select AccountDemo : war exploded,then 'Apply'and 'OK'.(Application context is not provided,I have used default i.e 
                  '/',if you want your own then enter it under 'Application context' appears on the right side of '+' icon under 'Run>Edit 			  Configurations>Deployment').


	Testing with Postman:

		# To create select 'POST' method in postman and enter following URL :
		--URL to create will be:"localhost:8080/v1/accounts/account"
		 -Select radio button 'Body',select 'raw' and under dropdown list select 'JSON(application/json)'.
		 -Then add data in following format:

			{
			     "accountId": "1",
			     "customerName": "Test Customer 1505999048160",
			     "currency": "$",
			     "amount": 25550
			}
		 -Click on 'Send'.

	 	# To Get All accounts select 'GET' method in postman and enter following URL:
		--URL:"localhost:8080/v1/accounts/account/"

		 -Click on 'Send'.
		you will get output as follows:

		 {
			"accountId": "1",
			"customerName": "Test Customer 1505999048160",
			"currency": "$",
			"amount": 25550
   		 }{
			"accountId": "2",
			"customerName": "Test Customer 1505999048155",
			"currency": "$",
			"amount": 250
   		 } {
			"accountId": "3",
			"customerName": "Test Customer 1505999048144",
			"currency": "$",
			"amount": 20
   		 } 

		# Get By Id: Select 'GET' method in postman and enter following URL:
		--URL:"localhost:8080/v1/accounts/account/{id}"
 
		for e.g.
		--URL to get by id will be:"localhost:8080/v1/accounts/account/1"

		  -Click on 'Send'.

		  you will get output as follows:

		 {
			"accountId": "1",
			"customerName": "Test Customer 1505999048160",
			"currency": "$",
			"amount": 25550
   		 }


		# To update: Select 'PATCH' method in postman and enter following URL :
		--URL:"localhost:8080/v1/accounts/account/{id}"

		for e.g.
		--URL to update will be:"localhost:8080/v1/accounts/account/1"

		 -Select radio button 'Body',select 'raw' and under dropdown list select 'JSON(application/json)'.

		 -Then add data in following format:

		[{"op": "replace","path": "/customerName","value": "Cust1"},{"op": "replace","path": "/amount","value": "1323444"}]

		Account before update:

		{
			"accountId": "1",
			"customerName": "Test Customer1",
			"currency": "$",
			"amount": 20000
   		 }

		Account after update:

		{
			"accountId": "1",
			"customerName": "Cust1",
			"currency": "$",
			"amount": 1323444
   		 }

		 -Update data and click on 'Send'. 


	About JSON Data File Location:

		-System.getenv("db.path") will get the value of the specified environment variable. An environment variable is a 			 system-dependent external named value.
		
		-If it returns a drive path then file according to the path will be created.
		-If System.getenv("db.path") returns null, then it will create the file under bin folder of tomcat (because of 			  System.getProperty("user.dir") being the default - this points to bin folder of the tomcat).


	About JSONDB library being used to create JSON:

		-Following dependancy was added in pom.xml and used as a library in the application:

		 <dependency>
			<groupId>io.jsondb</groupId>
			<artifactId>jsondb-core</artifactId>
			<version>1.0.36</version>
		</dependency>

		-This will provide a library which internally uses ObjectMapper to write and read JSON data into file.

		-This will provide some methods which works out of the box. It will avoid our task to iterate over file and makes code more 			 concise and simpler to manage.
			1)"jsonDBTemplate.insert(account)" to create account.
			2)"jsonDBTemplate.findById(accountId, AccountDTO.class)" to get by id.
			3)"jsonDBTemplate.findAll(AccountDTO.class)" to get all accounts.
			4)"jsonDBTemplate.upsert(dbRecord)" to update account.

		-jsonDBTemplate is reference to a object of class JsonDBTemplate present in library.

	About JSON PATCH RFC 6902 JSON:

		-JSON PATCH is used for resource modification like JSON objects.
		-JSON PATCH will modify the content from the resource that we want.It will not modify the content of resource like PUT method 			 as put method replaces the whole content.
	
		 For e.g.
			In account there are four fields:
			1)accountId
			2)customerName
			3)currency
			4)amount
		Suppose we want to modify amount then it will only replace amount by new value not other fields. 

		-JSON PATCH defines the which operation we have to perform on the JSON file.
		-There are number of operations as follows:
			1)test
			2)remove
			3)add
			4)replace
			5)move
			6)copy
		-It is a document that represents an array of objects. Each object represents a single operation to be applied to the target 			 JSON document.

		   The following is an example JSON Patch document, transferred in an  HTTP PATCH request:

		   PATCH /my/data HTTP/1.1
		   Host: example.org
		   Content-Length: 326
		   Content-Type: application/json-patch+json
		   If-Match: "abc123"

		   [
		     { "op": "test", "path": "/a/b/c", "value": "foo" },
		     { "op": "remove", "path": "/a/b/c" },
		     { "op": "add", "path": "/a/b/c", "value": [ "foo", "bar" ] },
		     { "op": "replace", "path": "/a/b/c", "value": 42 },
		     { "op": "move", "from": "/a/b/c", "path": "/a/b/d" },
		     { "op": "copy", "from": "/a/b/d", "path": "/a/b/e" }
		   ]

		-Following example will illustrates how patch works:

		Send request in the following format :

		URL:"localhost:8080/v1/accounts/account/1"

		Body will contain:[{"op": "replace","path": "/customerName","value": "Cust1"},
				   {"op": "replace","path": "/amount","value": "1323444"}]

		In "op" provide the operation that we have to perform,In "path" provide the fieldname that we want to change and in "value" 			specify the new value.

		Account before update:

		{
			"accountId": "1",
			"customerName": "Test Customer1",
			"currency": "$",
			"amount": 20000
   		}

		Account after update:

		{
			"accountId": "1",
			"customerName": "Cust1",
			"currency": "$",
			"amount": 1323444
   		}

		 

		



