The goal of this mini project is to write a web service “Digits Bank App”. Through this web service, someone can create
customer,account. Customer can create your own account. Account have deposit money, withdraw money and transfer money. 
The details are described below.

How do I run the project?

- Start Runner
- Send some requests

Rest Endpoints

http://localhost:8080/customer/create [POST] - Create customer
http://localhost:8080/customer [GET] - Find all customer
http://localhost:8080/customer/{customer_id} [GET] - Find customer by ID
http://localhost:8080/customer/{customer_id}/delete [DELETE] - Delete customer
http://localhost:8080/customer/{customer_id}/update [PUT] - Update customer
http://localhost:8080/customer/delete_all [DELETE] - Delete all customer

