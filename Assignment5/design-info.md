* * * * * * * * -------- * * * * * *DESIGN INFORMATION * * * * * * ------------- * * * * * * * * *

(1) A grocery list consists of items the users want to buy at a grocery store. 
* * *I added a class called Grocery list
The application must allow users to add items to a list, delete items from a list, and change the quantity of items in the list (e.g., change from one to two pounds of apples)
/////  My Grocerylist will allow you to add, delete, and change the quantities of the items that will descried by the customer. The grocery list plays as a main role ,it will communicates with other classes.
(2) The application must contain a database (DB) of ​items​ and corresponding ​item types​.
//////My design wil have ItemDatabase, will not do any thing because the databade is not effecting directly. So, i added a datamanager class that allows cutomer to add and manuplate the items from the database.
(3)Users must be able to add items to a list by picking them from a hierarchical list, where the first level is the item type (e.g., cereal), and the second level is the name of the actual item (e.g., shredded wheat). After adding an item, users must be able to specify a quantity for that item.
///////
(4)Users must also be able to specify an item by typing its name. In this case, the application must look in its DB for items with similar names and ask the users, for each of them, whether that is the item they intended to add. If a match cannot be found, the application must ask the user to select a type for the item and then save the new item, together with its type, in its DB.
///////
(5)Lists must be saved automatically and immediately after they are modified
//////
(6)Users must be able to check off items in a list (without deleting them).
///////
(7)Users must also be able to clear all the check-off marks in a list at once.
//////
(8)Check-off marks for a list are persistent and must also be saved immediately.
//////
(9)The application must present the items in a list grouped by type, so as to allow users to
shop for a specific type of products at once (i.e., without having to go back and forth
between aisles).
///
(10)The application must support multiple lists at a time (e.g., “weekly grocery list”, “monthly
farmer’s market list”). Therefore, the application must provide the users with the ability to
create, (re)name, select, and delete lists.
///////
