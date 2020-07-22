Assumptions made:

* IDE used: **IntelliJ IDEA**.
* Database used: **MySQL**.
* Connection to database carried out using **Spring Data JPA**, using *JpaRepository*.
* Application follows a four-tier architecture.
  * *Model* <---> *Controller* <---> *Service* <---> *Repository*
* HTML templating has been implemented using **Thymeleaf**, which works great in conjuction with Spring MVC.
* Used **Bootstrap** to give the website a better look.
* **jQuery** is used to send some data to the controller  in an asynchronous way, or when there is no form attached to the submit. For example:
  * In *product-detail.html*, form submission is *preventDefault()*-ed and the data is serialized and POSTed to */cart/add* with *$.ajax()*, and the response from the controller is written in the div with *id="status"*.
  * In *cart.html*, POST requests are sent to */cart/remove* and */cart/update* without using a form/DTO, rather by building the data manually and sending it to the controller. 
* **Spring Security** has been used to implement role-based authorization for the users (configuration in file *config/WebSecurityConfig.java*).
* Two roles: *ADMIN* and *USER*
* Only *ADMIN* can see the orders that have been created and complete (close) them.
* I have also used **Thymeleaf-Extras-SpringSecurity5** to provide templating support for methods such as *sec:authorize="hasRole('ROLE_ADMIN')*, which communicates with *Spring Security* and displays the block of HTML code if the user logged in has the role. The previous code fragment is used in *sub_header.html* to hide the admin related buttons.
* Both ADMINs and USERs can buy products. This can be changed in *WebSecurityConfig.java*, by making sure that the authenticated user has the required role (using *.hasRole()*) when acessing a certain URL.
* ADMINs can add new products. A check is made so that products do not have the same name.
* If a user that does not have the role *ADMIN* tries to access */admin/orders* or any other pages intended for administrators, a 404 error is returned. 
* Trying to access any URL that does have a mapping will return a 404 error.
* The cart is stored in a map in **session**, so it will be available while maintaining the browser opened. It is not unique for a user, rather in a browser basis.
* **Validation** is used for all the user inputs, and a relevant error message will be displayed when an incorrect input is made (using *BindingResult*).
* The product list has **pagination**, and support for searching and sorting. Due to time constraints, I did not create the HTML code to have a drop-down with the different options for sorting, but they are supported by the controller. They can be accessed by accessing the address */products?sort=* and a number related to the sort order as defined in **ProductSortEnum.java**.
* Pagination also supports a different number of Products per page, however as before the HTML code has not been created, but the controller supports it.
* A **REST** controller is used to interface with the *cart* functionality (adding, updating and removing). 
* The tests focus on the user inputs when interacting with the web application.
* To simplify testing, *spring.jpa.hibernate.ddl-auto=create* is used in *application.properties*.
