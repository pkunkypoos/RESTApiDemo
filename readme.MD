## Power Ledger Demo API

Code Challenge for Power Ledger built to these specifications:
```
Please implement a simple REST API in spring boot that accepts a list of Names and Postcodes in the HTTP request body, and persist the data in a database (either in memory or a traditional one).
The API should also have an endpoint that receives the postcode range and returns in the response body the list of names belonging to that postcode range, sorted alphabetically as well as the total number of characters of all names combined.

The implementation should use Java streams in some way.

We will be specifically looking for clean, well-structured code.

If you have a way to deploy the application in a cloud environment it would be a plus, but this is not expected.
```
Built using h2 in-memory database - saves will persist until Spring Boot shuts down. Here is an assumption, it's been modelled after Suburbs and postcodes.

if running locally, swagger/OpenAPI available here: http://localhost:8080/swagger-ui.html

Author : Matthew Nixon - pkunkypoos@gmail.com
Built with Gradle - simply run the bootrun task to start!

Apologies - gitignore is being silly for some reason

###Example Requests:
http://localhost:8080/names-and-postcodes
Body:
```
{
  "namesAndPostCodes": [
    {
      "name": "Perth",
      "postcode": "6000"
    },
    {
      "name": "Carlisle",
      "postcode": "6101"
    }
  ]
}
   
```

http://localhost:8080/names-and-character-count?lower=6000&upper=7000


