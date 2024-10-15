
# Transaction API

An API that simulates Bank transfer and Transaction management.





## Appendix

API Key features includes:

Creation Of An Account,
Crediting of An Account,
Transaction Processing,
Fetching Transactions By Date,
Fetching Transactions using a Scheduler, which runs every 2AM,
A Scheduler which enables Transaction Commission worthiness...


## Documentation

To create An Account You will need to pass in users information (Name, Phone and Email), an account is Generated for that user and appended to the database alongside his details.

RequestBody Account Creation:

     String accountName;
     String email;
     String phoneNumber;

Funding an Account, through direct one way transaction requires putting money into account with out a sender account, this simulates just walking into a bank with cash and put it into your account.

Request Body For Crediting Includes:

     String accountNumber;
     BigDecimal amount;

Transaction Processing, The Logic to process a transaction has some moving pieces, first it requires 2 account, one designated the senderAccount and the other the DestinationAccount, 
for money to be transfered the Logic checks if the DestinationAccount exists, if yes it checks if the senderAccount has the amount to send in his account, if no, an Insufficient balance message is displayed, if yes.

A transactionfee calculation Method is called, to calculate the transactionfee, which is the added to the amount being sent, which makes up the billedAmount from the Senders end. the details is saved and set as Pending transactionfee is a Pending the crediting of the DestinationAccount account, if successful a Method to update transaction details(update commission worthiness, append commissionfee, update status to successful) will be called.

Transaction Request Body Includes:

     String sourceAccountNumber;
     String destinationAccountNumber;
     BigDecimal amount;
     String description;


Getting Transaction Summary, fetching of transaction Summary for an account for a duration of time.

Transaction Summary request Body:

     String accountNumber;
     LocalDate startDate;
     LocalDate endDate;



API responses are customized for all Account process and all Transaction Process. 
## Run Locally

Clone the project

```bash
  git clone https://github.com/richardnick/TransactionAPI.git
```

Go to the project directory

```bash
  cd my-TansactionAPI
```

Install dependencies

```bash
  mvn clean build
```

Start the server


```bash
Use IntelliJ run button
```


## Running Tests

To run tests, run the following command

```bash
  mvn test
```


## Installation
The System has been dockerized for easy deployment, and can be ran as a Kubernetes node.

Install my-project with docker

```bash
  cd TansactionAPI
  
  docker run -p 6262:6262 transactionapi

```
    