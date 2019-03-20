* * * * * * * * -------- * * * * * *DESIGN INFORMATION * * * * * * ------------- * * * * * * * * *

(1) A grocery list consists of items the users want to buy at a grocery store. 
* * *I added a class called Grocery list
The application must allow users to add items to a list, delete items from a list, and change the quantity of items in the list (e.g., change from one to two pounds of apples)
/////  My Grocerylist will allow you to add, delete, and change the quantities of the items that will descried by the customer. The grocery list plays as a main role ,it will communicates with other classes.

(2) The application must contain a database (DB) of ​items​ and corresponding ​item types​.
//////My design wil have ItemDatabase, will not do any thing because the databade is not effecting directly. So, i added a datamanager class that allows customer to add and manuplate the items from the database.

(3)Users must be able to add items to a list by picking them from a hierarchical list, where the first level is the item type (e.g., cereal), and the second level is the name of the actual item (e.g., shredded wheat). After adding an item, users must be able to specify a quantity for that item.
///////the application must look in its DB for items with similar names and ask the users, for each of them, whether that is the item they intended to add. If a match cannot be found, the application must ask the user to select a type for the item and then save the new item, together with its type, in its DB.

(4)Users must also be able to specify an item by typing its name. In this case, the application must look in its DB for items with similar names and ask the users, for each of them, whether that is the item they intended to add. If a match cannot be found, the application must ask the user to select a type for the item and then save the new item, together with its type, in its DB.
////////I implemented in my groceryList class a method additemName(itemName) which will allow the costomer to add an item to the grocery list by inserting just the name. This metod will call the finditem() method in the dataManager class and will search for similar names and ask whether that is the item Type they intended to add. If the user confirms the itemType, then itemType will be set to that type. If there is no match found in the (DB) then the cotomer will be asked to set the product type manually. The checkInitem() will check mark the product only after the itemType, itemName, and quantity is specified and will be sent to grocery list.

(5)Lists must be saved automatically and immediately after they are modified
//////To accomplish this task, the viewGroceryList() will allow the user to see all the updated items in the list currenly.

(6)Users must be able to check off items in a list (without deleting them).
///////To accomplish this task, Once the user has viwed the shopping list, the user will be able to checkOff those items in their cart that should not be included in the checkOut. All the items that are checkedOff will not be visible to the user however will not be removed unless the user wants to removeitem() which will permanantly remove the product from the list. 

(7)Users must also be able to clear all the check-off marks in a list at once.
////// I implemented a clearCheckOff() method in my design. This method will allow the user to clear all checkedOff products and set visiblity to hidden of the items that exist in the viewGroceryList(). This will not be considered because it does not affect the design directly.

(8)Check-off marks for a list are persistent and must also be saved immediately.
//////-To acomplish this task, all the product items in the viewGroceryList will be saved to the current list no matter they are checked or checkedOff from the view shopitem.
(9)The application must present the items in a list grouped by type, so as to allow users to
shop for a specific type of products at once (i.e., without having to go back and forth
between aisles).
/////////-To realise this requirment, I implemented class dataManager with method showProduct() which communicates with the database to show all the products by type for the user to shop for specific type of products.
(10)The application must support multiple lists at a time (e.g., “weekly grocery list”, “monthly
farmer’s market list”). Therefore, the application must provide the users with the ability to
create, (re)name, select, and delete lists.
/////// To fulfill this requirement into design, I implemented The listManager class that will allow the user to create, rename, modify and delete a selected list by list name.

