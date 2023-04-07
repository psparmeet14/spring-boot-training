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

Now, use the token for the subsequent requests to access survey resource
To get all surveys:
GET request:
http://localhost:8080/api/v1/surveys
Add bearer token to the request.
