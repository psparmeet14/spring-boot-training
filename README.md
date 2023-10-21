# spring-boot-training

Implemented Spring Security with JWT.

Includes the following features:
- User registration and login with JWT authentication
- Password encryption using BCrypt

For authentication:

POST request: 
http://localhost:8080/api/v1/auth/register

Request body:
{
    "firstName": "YourFirstName",
    "lastName": "YourLastName",
    "email": "YourEmail",
    "password": "YourPassword"
}

Now, use the token for the subsequent requests to access survey resource.

To get all surveys:

GET request:
http://localhost:8080/api/v1/surveys

Add bearer token to the request.

# HATEOAS
- Hypermedia As The Engine Of Application State
- Enhancing the response with links to other resources
- Enhancing the REST API to tell consumers how to perform subsequent actions
- Use Standard Implementation:
- HAL (JSON Hypertext Application Language): Simple format that gives a consistent and easy way to hyperlink between resources in your API
- Spring HATEOAS: Generate HAL responses with hyperlinks to resources
- Example:

{
    "name": "Parmeet",
    "birthDate": "2022-08-15",
    "_links": {
        "all-users": {
            "href": "http://localhost:8080/api/v1/users"
        }
    }
}