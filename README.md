# Account Validate Aggregator
This document is to provide the detail about the implementation and the assumption that I has made in order to complete this assessment.

### Implementation  Details:
1. Although few extra implementations are not required for this assessment I create this project based on the same attitude that I would like to when I work on the real-world project such that I would like to be able to track the logs throughout the request.
2. This implementation is based on Java 1.8 and Spring Boot 2.5.5
3. MockExternalProviderService has 1 second waiting time in order to simulate the external provider process time.

### Assumption
1. It is an aggregate endpoint that in most cases it does two things. 1. hide the underlying provider user connection information from the client. 2. consolidate all underlying providers' requests and responses that are specified by the client and return results with a single response to the clients.  Basically, reduce the overhead from the client side. Therefore, I make technical trade-offs based on the limited information. If some reason we cannot verify the account number with a provider, I will return "isValid": false instead of return error (Internal Server Error)
2. The requirement does not mention not to modify the config format, so I did make some format change to make things easy to parse.
3. The requirement does mention no need to implement the provider, so I did not handle parsing the provider's response in this assessment event though the document does mention the provider response. 

### To Run
1. You will need to run this app with Java 1.8
2. To run the app. you can run this command `./mvnw spring-boot:run`

### Troubleshooting
1. This app is leveraging the ThreadPoolTaskExecutor and CompletedFuture to perform async provider requests. The pool setting is based on my local machine.  If you are testing with a large dataset, you may need to configure the pool accordingly. 