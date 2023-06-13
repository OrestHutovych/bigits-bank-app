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

I added new credit card system where:
-

- Credit card have limit for UAH 50000
- Credit card have limit for USD 1350
- Credit card have limit for EUR 1215
- If your credit card has less than the limit, then create an expiration date by which the loan must be repaid or it will increase by 1%


http://localhost:8080/credit_card/create [POST] - Create credit card
http://localhost:8080/credit_card/{customer_id} [GET] - Find all credit card by customer
http://localhost:8080/credit_card/{customer_id}/{credit_card_id}/{creditNumber}/deposit_to_credit [POST] - Deposit to credit card
http://localhost:8080/credit_card/{customer_id}/{credit_card_id}/{creditNumber}/withdraw_from_credit [POST] - Withdraw from credit card
http://localhost:8080/credit_card/{customer_id}/{credit_card_id}/{creditNumber}/withdraw_from_credit [POST] - Transfer money from credit card