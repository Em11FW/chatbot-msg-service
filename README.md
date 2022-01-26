# Chatbot messages API

## Usage scenario

Assuming there is a chatbot that processing data from customers in real time.

A background job pushes the data (text messages) provided by the customers via HTTP to a data management API. 

In order to be compilant with the data regulation laws, customers must give an
explicit consent AT THE END of the dialogue to process the data - the API also manages that.

The API is used by data scientists to retrieve the data with consent.

## How to setup and run the application

1. Clone this project
2. Set up a local postgresql database for testing purpose
   - install postgresql if it is not installed
   - Run `psql -U postgres --file chatbotmsg_db.sql` 
4. build and run the executable JAR
   - Run `./mvnw clean package`
   - Verify `target/chatbot-msg-0.0.1-SNAPSHOT.jar` exists
   - Run `java -jar target/chatbot-msg-0.0.1-SNAPSHOT.jar`

## API endpoints

#### POST `/data/:customerId/:dialogId`
With the payload
```json
{
    "text": "the text from the customer",
    "language": "EN"
}
```
This is the endpoint used by the background job to push each customer input during their dialogue with the chatbot.


#### POST `/consents/:dialogId`
With the payload  
`true` or `false`  

This endpoint is called AT THE END of the dialogue when the customer is asked if they gives consent for us to store and use their data for further improving the chatbot.  

If false it should delete the customer's data

#### GET `/data/(?language=:language|customerId=:customerId|page=:page|size=:size)`

This endpoint is used by data scientists to retrieve data to improve the chatbot, it will return all the datapoints:

- that match the search params `customerId` and `language`, if no param is provided, then return all
- with consent
- and sorted by data creation time in a descending order (recent data first).
- pagination is possible with params `page` and `size`: 
    when `page` is not defined, set to 1 as default; 
    when `size` is not defined, set to 100 as default

## Implementation decisions

1. Build intial project with Spring Boot
   - an easy to start to build a RESTful API application avoiding the long preparation and configuration of the environment.
   - it provides robust functionalities (development tools, database transactions, testing)
2. Packaging by layer
   - to clearly show the architecture of the application
   - in case of designing a larger application with more features, I may consider packaging by features
3. Using database to store the data through all the stages
   - An alternative is to store the data in the memory before the consent is granted. However there is a risk of losing data
   - In case of using an external database, always checking the consent status before returning the users' data
   - Later I may consider use in-memory database (e.g, redis) to store the data before the consent is granted
5. Using JdbcTemplate to handle the database transactions
   - I got more control on the performance since I manually write the sql queries.
   - I am more familiar with using the JdbcTemplate. I may try to use Spring JPA later.
6. Using a separate table to store the consent information
   - reduce database redundancy

## Future improvement

1. Availability (e.g, Load balancer)
2. Reliability
3. Security
4. Database management(e.g, indexing, transactions, lock management)
