# Logger Application  <code><img width="10%" src="https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white"></code> <code><img width="8%" src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white"></code> [![Linux](https://svgshare.com/i/Zhy.svg)](https://svgshare.com/i/Zhy.svg) [![Windows](https://svgshare.com/i/ZhY.svg)](https://svgshare.com/i/ZhY.svg)
## ✨ ITBootcamp Final Project 
**Logger** is an application that allows its registered users to store information important to them and retrieve it later using various filters.
### Technologies
Project is created with:
- Java 17
- Spring Framework 2.7.5
- Maven

<code><img width="10%" src="https://www.vectorlogo.zone/logos/java/java-icon.svg"></code>
<code><img width="8%" src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg"></code>
<code><img width="8%" src="https://raw.githubusercontent.com/vscode-icons/vscode-icons/63a4a33b35b50d243716d03b95a955e49db97662/icons/file_type_maven.svg"></code>
---

**Endpoints:**
- /api/clients
- /api/clients/register
- /api/clients/login
- /api/clients/{clientId}/reset-password
- /api/logs/create
- /api/logs/search

**Project Entities:**

*User*
1. User can register 
     - username, email, password 
2. User can retrieve his "key" using 
     - username, email, password 
3. User can type log 
     - message 
     - logType (ERROR,WARNING,INFO) 
     - createdDate 
4. User can to search logs 
     - By time (from - to) 
     - By the text found in the log message 
     - By log type (ERROR,WARNING,INFO) 
     - Or any combination
  ---

*Admin*
1. There is an admin account (or admin key) 
2. Admin can see all users 
3. Admin can see the number of logs for each user 
4. Admin can change the user's password at his request
---

# Details:

## Client

1. Register
    - HTTP Method: `POST`
    - Endpoint URL: `/api/clients/register`
    - Request body:
        ```json
        {
            "username": "string",
            "password": "string",
            "email": "string"
        }
        ```
    - Responses:
        - 201 - Registered
        - 400 - Bad Request
            - email must be valid
            - username at least 3 characters
            - password at least 8 characters and one letter and one number
        - 409 - Conflict
            - username already exists
            - email already exists

2. Login 
    - HTTP Method: `POST`
    - Endpoint URL: `/api/clients/login`
    - Request body:
        ```json
        {
            "account": "string", // email or username
            "password": "string"
        }
        ```
    - Responses:
        - 200 - OK
            ```json
            {
                "token": "string" // uuid* || JWT || username
            }
            ```
        - 400 - Bad Request
            - Email/Username or password incorrect

3. Create log
    - HTTP Method: `POST`
    - Endpoint URL: `/api/logs/create`
    - Request body:
        ```json
        {
            "message": "string",
            "logType": 0
        }
        ```
    - Request headers:
        - `Authorization` - token
    - Responses:
        - 201 - Created
        - 400 - Bad Request
            - Incorrect logType
        - 401 - Unauthorized
            - Incorrect token
        - 413 - Payload too large
            - Message should be less than 1024

4. Search logs
    - HTTP Method: `GET`
    - Endpoint URL: `/api/logs/search`
    - Request params:
        - `dateFrom` - date
        - `dateTo` - date
        - `message` - string
        - `logType` - int
    - Request headers:
        - `Authorization` - token
    - Responses:
        - 200 - OK
            ```json
            [
              {
                "message": "string",
                "logType": 0,
                "createdDate": "date"
              }  
            ]
            ```
        - 400 - Bad request
            - Invalid dates
            - Invalid logType
        - 401 - Unauthorized
            - Incorrect token

<div style="page-break-after: always;"></div>

## Admin

1. Get all clients
    - HTTP Method: `GET`
    - Endpoint URL: `/api/clients`
    - Request headers:
        - `Authorization` - token (Admin token)
    - Responses:
        - 200 - OK
            ```json
            [
              {
                "id": "uuid",
                "username": "string",
                "email": "string",
                "logCount": 0
              }  
            ]
            ```
        - 401 - Unauthorized
            - Correct token, but not admin
        - 403 - Forbidden
            - Incorrect token

2. Change client password
    - HTTP Method: `PATCH`
    - Endpoint URL: `/api/clients/{clientId}/reset-password`
    - Request body:
        ```json
        {
            "password": "string"
        }
        ```
    - Request headers:
        - `Authorization` - token (Admin token)
    - Responses:
        - 204 - No content
        - 401 - Unauthorized
            - Correct token, but not admin
        - 403 - Forbidden
            - Incorrect token
