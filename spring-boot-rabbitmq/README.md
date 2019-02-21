# RabbitMQ project based on Springboot
It helps to understand the concepts regarding the RabbitMQ publisher and consumer in an event driven approach. 

It has couple of endpoints to let the user to manage the message processing flow and to publish the messages to the rabbitmq.

### AdminService:
**/admin** ***PUT*** accepts a query parameter **processingTime** to slow down the message processing flow which eventually help us to see and analyse the messages coming into rabbitmq and the accumulation of the messages when the consumer is in **event sourcing** model.

### EmployeeService:
**/employees** ***POST*** accepts a new employee details in a json format and publishes it to the **rabbitMQ** queue: ***test***   
**/employees/pdfs**  **POST** accepts the pdf file to be uploaded and published to the **rabbitMQ** queue: ***pdfs***


It has couple of message listeners implemented to receive two different types of messages i.e **Employee** and **pdf** as byte stream. 

Feel free to raise any issues encountered.
