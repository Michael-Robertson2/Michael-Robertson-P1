# Project 1: Employee Reimbursement System

## Overview

The Employee Reimbursement System is a REST API made to facilitate the process of a company's employees submitting requests for financial reimbursements for business expenses, and their managers responding to those requests.

users who access the system will fall into one of three different roles, each of which will have access to different functions of the API.

-Users can submit reimbursement requests (known as tickets) and then later edit or delete them until they are resolved by a finance manager. They can also view their personal ticket history, with several options for filtering and sorting.

-Finance Managers can approve or deny tickets, as well as view a list of all pending tickets, and previously processed tickets.

-Administrators have control over the system itself, can activate or delete accounts, and view a list of all current users.

##### Use Case Diagrams
![Use Case Diagrams](https://raw.githubusercontent.com/Michael-Robertson2/Robertson-Michael-Code/main/ERS%20Use%20Case%20Diagram.png)

### Project Design Specifications


The database is arranged in such a way that it qualifies as 3rd Normal Form. This primarily means that all its values are atomic, that it does not have any composite primary keys, and there are no transitive dependencies.
##### Relational Data Model
![Relational Data Model](https://raw.githubusercontent.com/220207-java-enterprise/assignments/main/foundations-project/imgs/ERS%20Relational%20Model.png)

##### Ticket Status State Flow
![Ticket Status State Flow](https://raw.githubusercontent.com/220207-java-enterprise/assignments/main/foundations-project/imgs/ERS%20State%20Flow%20Diagram.png)

### Tech Stack

**Persistence Tier**
- PostGreSQL (via Docker)

**Application Tier**
- **Java 8**
- **Apache Maven**
- **MySQL Connector Java**
- **Javalin**
- **JSON Web Tokens**
- **JUnit**
- **Mockito**

(Special thanks to Elias Gonzales, who provided a template for this readme)