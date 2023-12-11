## Hotel Reservation App ##

### Purpose of the App ###

This is a command line app for a hotel reservation system. It has the following capabilities:

- As an admin, users can create new rooms, list all rooms, list all customers and list all reservations.
- As a customer, user can create account, find and reserve room, list all their reservations.
- If rooms are not found for the dates specified, the system will recommend new date and rooms if available.

### Running the App ###

#### Clone the Repository ####

Open a terminal and git clone the repo to local system.

#### Run the App ####

Open the cloned folder in Intellij IDE. In the Project explorer, go to src > main > java > HotelApplication.java.

Run the application by clicking on the run button next to the class name or main function.

Once the application is started, a menu will be displayed, which can be used to traverse the application.

### Scope of the project ###

- Currently only .com domains are supported for the email field and should be of the format name@domain.com
- Accounts created are available only till the application is running. Once exited, new accounts has to be created
  again.
- Though account needs to be created only once, validation of existing account by providing email address has to be
  performed for any reservations related operations
