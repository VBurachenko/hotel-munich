# hotel-munich

## System online-booking of apartments in the hotel.

### Task description:
   Customer registers in system and authorize by email and password. After that he fill the search form, and get a list of free and available rooms. Authorized customer choose the room (or rooms),  booking it ( or them), and system generates automaticaly the invoice. Client can pay this invoice instantly, or in hotel during arriving. Booking can be canceled, or payed his invoice by customer in case it wasn't payed, otherwise customer contacts with support and solve this questions.
   Admin registers in system as an usual customer at first and than became an admin by moderator. Admin able to spectate full lists of registered users, bookings, invoices and rooms. Customer can be found  by user-id, email, or telephone number, and blocked/unblocked by admin. Booking can be found by booking-id, and process it in order of booking-status and invoice of booking. Booking can be canceled by admin. Also admin can add new rooms, found them by number, make able/disable them, change their description. Hotel-room can be finaly deleted from the system only after canceling all bookings with this room.
   Moder is predetermined superadmin witch able to doing everything as same as usual admin, including the possibility of registering new one admin.

### Technical description of the project:
Build tool: Maven;
Java 8;
JavaEE: Servlet, JSP; <br>
Server / Servlet container: Tomcat 9.0.10 <br>
Logger: Log4J; <br>
Database access technology: JDBC (with own custom connection pool);<br>
Database management system: MySQL; <br>
Tests: JUnit4; <br>
General architecture of application corresponds patterns: MVC and Layered architecture; <br>
User interface localized for next languages: English, German, Belarusian, Russian; <br>
Advanced used technologies: JavaScript, jQuery.
