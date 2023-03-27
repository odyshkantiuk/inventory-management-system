# Inventory Management System

This is an Inventory Management System built according to ***MVC*** requirements, developed in ***Java*** using ***MySQL*** as its database.

## What is the inventory management system used for?

This application can be used by stores for easy maintenance and inventory management of:
- Products
- Suppliers
- Customers
- Employees
- Transactions

## Why use an inventory management system?

Inventory management helps companies identify which and how much stock to order at what time. It tracks inventory from purchase to the sale of goods. The practice identifies and responds to trends to ensure there's always enough stock to fulfill customer orders and proper warning of a shortage.

## MVC overview

### General characteristics of MVC

MVC (Model-View-Controller) is a pattern in software development that is commonly used to implement user interfaces, data, and control logic. This emphasizes the separation between the business logic of the software and the display. This "division of tasks" ensures a better division of labor and improved maintenance.

The three parts of the MVC software design pattern can be described as follows:
1. Model: Manages data and business logic.
2. Presentation: Controls layout and display.
3. Controller: directs commands to parts of the model and view.

### The purpose of MVC

The main purpose of the MVC pattern is to promote separation of tasks, making the application more modular, extensible, and maintainable. By separating problems, you can make changes to one component without affecting others, making the development process more efficient and reducing the risk of bugs or unintended consequences.

In addition, using MVC promotes code reuse because each component can be reused in different parts of the application or in different applications altogether. This can lead to a faster development cycle and more efficient use of developer resources.

### The working principle of MVC

As written above, there are three parts to the MVC software design pattern.

***Model*** defines what data the app should contain. If the state of this data changes, then the model will usually notify the view (so the display can change as needed) and sometimes the controller (if different logic is needed to control the updated view).

***View*** defines how the app's data should be displayed.

***Controller*** contains logic that updates the model and/or view in response to input from the users of the app.

### Design patterns used in MVC

The implementation of MVC can include the use of various design patterns, such as "Observer", "Strategy", "Composite" and others.

The main implementation of the concept consists in separating the representation from the model by establishing an interaction protocol between them using the event apparatus. When the model's internal data changes, it notifies all its dependent views, which are then updated. For this, the "Observer" template is used.

When processing the user response, the view selects the appropriate controller that communicates with the model. For this, you can use the "Strategy" template or a modification in the form of the "Command" template. In addition, the "Factory method" pattern is often used, which allows you to set the default controller type for the corresponding view.

### Implementation of MVC in Java

Model represents the data and data operations performed by the application. In Java, a model is usually implemented as a set of classes that represent data and provide methods to manipulate that data.

The View displays data to the user and processes the data entered by the user. In Java, a view is usually implemented using Swing or JavaFX, which are a set of graphical user interface components provided by Java.

The Controller is responsible for processing user input and updating the model and view accordingly. It receives data from the user, updates the model, and then updates the view to reflect the changes made to the model. In Java, a controller is usually implemented as a set of event listeners that respond to user input.