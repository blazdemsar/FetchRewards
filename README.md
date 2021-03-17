# FetchRewards - Back-End Take Home Assignment

# Requirements
>- IDE (Preferrably Eclipse IDE)
>- Postman or any other Client app to send requests to the server
>- MySQL 8.0 or newer

# Installation - Importing Maven Project
>1. Open Eclipse
>2. Follow directions to create a working directory
>3. On your computer, navigate to your working directory and copy-paste FetchRewardsApp inside the folder
>4. Once your screen is showing a Package Explorer on the left-hand side:
>>- Click on "File"
>>- Click on "Import..."
>>- In Search Box enter : "Maven Project"
>>- Select "Existing Maven Project" and Click "Next"
>>- Click "Browse..."
>>- Locate the project with filename FetchRewardsApp and Click "Select"
>>- Make sure that under Projects the "/pom.xml" is checked and Click "Finish"
>>- You should see the project imported in Package Explorer

# Installation - MySQL v8.0 or newer
>- Install MySQL client or MySQL Workspace CE
>- Follow instructions and set username and password to anything you want and write it down
>- Set port number to anything you want and write it down

# MySQL - Create Schema
> ****
> Using command line:
>> ****
>>- Login to your local instance using the username and password from before
>>
>>- CREATE SCHEMA \`fetchrewards_db\`;

> Using MySQL Workspace:
>> ****
>>- Login to your Local Instance, provide username and password from before
>>- Under the Navigation Menu look for an icon that looks like a stack of cakes (when hovered over it should say something like "Create a new schema in the connected server") and Click on it
>>- A new tab will open, enter "fetchrewards_db" and Click "Apply"
>>- Click "Apply" again

# FetchRewardsApp - Minor Setup
>1. Navigate to src/main/java and expand it
>2. Expand com.blazdemsar.config
>3. Double-click on AppConfig.java
>4. Follow the quick instructions to configure the database connection properly

# Running the program
>- Expand com.blazdemsar package
>- Right-click on FetchRewardsAppApplication.java
>- Click "Run as..."
>- Click "Java Application"

# Postman
>- Open Postman
>> **Note:**
>> Using Java OO approach, I added aditional instance members so the Request Body has to be slightly adapted. Follow to guidelines below please.

>>Send requests as per following:
>>1. Create a New Request:
>>2. Select a request type as per below request type for each route
>>3. Enter URL using localhost:8080/ (application should by default run on port 8080, please change the port number in case yours is different)
>>4. Append a route name as below
>>5. Under "Body" tab enter JSON formatted request body as demonstrated below.
>>
>>## Request
>>- POST localhost:8080/addTransaction:
>>```
>>{
>>    "accountId": 1,
>>    "payer": "DANNON",
>>    "points": 300,
>>    "timestamp": "2020-10-31T10:00:00"
>>}
>>```
>>
>>## Response
>>```
>>{"message":"Transaction saved!"}
>>```
>>
>>## Request
>>- PUT localhost:8080/spendPoints:
>>```
>>{
>>    "accountId": 1,
>>    "points": 5000
>>}
>>```
>>
>>## Response
>>```
>>[
>>    {"UNILEVER":-200},
>>    {"DANNON":-100},
>>    {"MILLER COORS":-4700}
>>]
>>```
>>
>>## Request
>>- GET localhost:8080/checkBalance:
>>```
>>{
>>    "accountId": 1
>>}
>>```
>>
>>## Response
>>```
>>{
>>    "UNILEVER":0,
>>    "MILLER COORS":5300,
>>    "DANNON":1000
>>}
>>```

