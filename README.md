# javaproject-appartment-rental-system
Flexi Rent Java Project

This is a FlexiRent System project, that was developed to learn the most out of JavaFx and MVC Design Pattern. 

The project is divided into 4 packages - Model, View, Controller, Service. 

Model contains the entities for the appartment property, the rental record and related classes.
View contains the fxml files, which represent single pages or elements of the pages and a single class, that dynamically generates single property's view based on the records in the database. 

Controller package has controller classes that take care of communication between model and view. 
Service package contains the so-called "helper methods" that serve as service for the controller classes.

The project has the possibility to import from the text file and export to the text file. The importing has a very strict structure, - it needs to follow the following pattern: 
```
property_id:street_number:street_name:suburb:property_type:num_of_bedrooms:status:image_name:description
```
For instance, the following property: 
```
ID: A_003
Street#: 107
Street name: Elizabeth Street
Suburb: Jolimont
Type: Appartment
Number of bedrooms: 1
Status: Available
Img name: No Image Available
Description: description of the property 80 to 100 characters
```
... could have the following import structure: 
```
A_003:107:Elizabeth Street:Jolimont:Apartment:1:Available:No Image Available:description of the property 80 to 100 characters
```
The project uses the JavaFx as the GUI library and the jdbc as the Database. 
For the project to work after being imported the jdbc driver (org.hsqldb.jdbc.JDBCDriver) needs to be first downloaded and added as the external jar file in the Library. 
After this database needs to be filled with some "dummy data", otherwise the project will not show any flexirent system properties at the main page. 

To add the so-called dummy data the following lines need to be uncommented in the loadDataToDatabaseTables() method in the StartUpClass:
```
CreateTable.createRentalPropertyTable();
CreateTable.createRentalRecordTable();
DataGenerator.generateData();
InsertRows.insertPropertyRows();
InsertRows.insertRecordsRows();
```
The program needs to be run once and the lines need to be commented back again. The tables will now have the needed rows and values as well. Program is now ready to be used.
