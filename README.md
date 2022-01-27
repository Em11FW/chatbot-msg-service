# Chatbot messages API

## Usage scenario

Assuming there is a chatbot that processing data from customers in real time.

A background job pushes the data (text messages) provided by the customers via HTTP to a data management API. 

In order to be compilant with the data regulation laws, customers must give an
explicit consent AT THE END of the dialogue to process the data - the API also manages that.

The API is used by data scientists to retrieve the data with consent.

## How to setup and run the application

1. clone this project
2. build the executable JAR
   - Run `./mvnw clean package -DskipTests`
   - Verify `target/chatbot-msg-0.0.1-SNAPSHOT.jar` exists
3. build and run the application image with db images in container
   - Run `docker-compose up`

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

If false it should delete the customer's data.

#### GET `/data/(?language=:language|customerId=:customerId|page=:page|size=:size)`

This endpoint is used by data scientists to retrieve data to improve the chatbot, it will return all the datapoints:

- that match the search params `customerId` and `language`, if no param is provided, then return all
- with consent
- and sorted by data creation time in a descending order (recent data first).
- pagination is configurable with params `page` and `size`: 
    when `page` is not defined, set to 0 as default (0-based indexing); 
    when `size` is not defined, set to 100 as default;

## Implementation decisions

1. Build intial project with Spring Boot
   - an easy to start to build a RESTful API application avoiding the long preparation and configuration of the environment.
   - it provides robust functionalities (development tools, database transactions, testing)
2. Packaging by layer
   - to clearly show the architecture of the application
   - in case of designing a larger application with more features, I may consider packaging by features
3. Use Redis to store data before consent granted, and move the data to external database after consent granted 
   - Make users' data not accessible externally before consent granted
   - Redis has a cache mechanism to avoid lossing data
   - Use Redis to store frequent pushed messages temporarily can achive a higher performance of data management  
5. Using Spring JPA to handle the database accessing
   - It significantly reduces development time than manually writting query to handle simple CRUD operations
   - It is less error-prone

## Future improvement

1. Availability (e.g, Load balancer)
2. Reliability
3. Security
4. Database management(e.g, indexing, transactions, lock management)
