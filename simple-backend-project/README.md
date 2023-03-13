# Simple Backend Project

This repository contains the same project but written in different languages. The idea is to familiarize myself with how some of the more popular languages and libraries are used to accomplish simple backend services.

## Project design

This project will mainly focus on the following
* REST APIs (Simple CRUD, HTTP responses, error handling)
* Database integration (Relational / Non-relational)
* Asynchronous method execution

### Relational Database Project

The basic design will be an inventory system where games can be sorted by publisher and inventory levels are managed using API calls.  

* Create publisher (For one-to-many relation)
* Delete publisher (Deletes all games by publisher followed by publisher)
* Create game with publisher (New item in inventory)
* Restock game (Increase stock levels)
* Delete game from inventory
* Buy game (Stock exists, reduce stock)
* Buy game (No stock, throws appropriate error messages)
* Find game by publisher name

### Future Improvements: Non-relational Database Project

The design for non-relation database project will be even simpler. It will be a warranty registration form where a user has to provide proof of purchase for their product 

Document should contain: Date of purchase, Buying Channel, Image URL if any, Email of customer, Approval status

* Register warranty
* Approve / Reject application
* Delete application
* Find applications by channel
* Find applications by date range
* Delete applications by date range (Asynchronous)