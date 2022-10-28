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

*Admin*
1. There is an admin account (or admin key) 
2. Admin can see all users 
3. Admin can see the number of logs for each user 
4. Admin can change the user's password at his request
