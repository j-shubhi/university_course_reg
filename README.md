## Assumptions
email id of admin: admin@iiitd.ac.in
FIXED_PSWD for admin is admin123
Available Courses:
MTH101: LA, Professor: subhajit@iiitd.ac.in, Credits: 4, Prerequisites: , Timings: Mon/Wed 10-11 AM
CSE101: Introduction to Programming, Professor: pankaj@iiitd.ac.in, Credits: 4, Prerequisites: , Timings: Tue/Thu 1-2:30 PM
ECE201: Digital circuits, Professor: tammam@iiitd.ac.in, Credits: 2, Prerequisites: , Timings: Fri 9-10 AM
DES101: iHCI, Professor: sonal@iiitd.ac.in, Credits: 4, Prerequisites: , Timings: Tues 10-11 AM
COM101: COM, Professor: payal@iiitd.ac.in, Credits: 4, Prerequisites: , Timings: Wed 3-6 PM
Current Semester: 1
professors
email                  password
"tammam@iiitd.ac.in"    "123"
"anoop@iiitd.ac.in"     "456"
"shad@iiitd.ac.in"      "789"
students
email                  password
"shubhi@iiitd.ac.in"    "111" 
"neha@iiitd.ac.in"      "222"
"sumit@iiitd.ac.in"     "333"
TAs
email                  password
"harry@iiitd.ac.in"      "t1"
"ginny@iiitd.ac.in       "t2"
"luna@iiitd.ac.in"       "t3"
cgpa = sgpa
# Demo
Some Admin, Student, TAs, Professors are initialised
For student login: enter mail and password
If successful, submit feedback for a particular course
To submit feedback: enter course code and then feedback
It will be submitted if code is valid
These feedbacks can be viewed by professor of that course
For TA login: enter mail and password
Grades can be viewed or assigned
For exceptions:
Invalid login: enter invalid credentials in login- it will throw an error
Course drop failures: register for a course as a student, try to drop course after 1 minute it will throw an error
Invalid course registration: add a course as an admin(keep capacity of course 1 for simplicity), then register for that course as student1 and then try register that course as student2 it will throw an error as capacity(i.e., 1) is full
How to run the code:
download my folder, open in IntelliJ, reach main.java, press on run, follow the instructions shown in terminal after running the code.
## **How OOPs concepts are used**
### **Classes and Objects**
The code defines multiple classes, Student, Professor, Admin, Course, and Complaint. These classes serve as blueprints for creating objects that encapsulate data and behavior related to students, professors, administrators, courses, and complaints, respectively.
### **Interface**
CourseOperations in an Interface. Interfaces is used to define common contracts between classes.
### **Inheritance**
The Student, Professor, and Admin classes inherit from the User class, which is an abstract class. This demonstrates the use of inheritance, where the derived classes (Student, Professor, and Admin) inherit attributes and methods from the base class (User).
Code reusability and hierarchical relationships between classes are some key features my code is using.
The TeachingAssistant class inherits from the Student class, allowing it to access all methods and properties of a student while adding specific functionalities related to grading.
### **Abstraction**
The User class is declared as an abstract class. Methods that are declared without implementation. The derived classes (Student, Professor, and Admin) provides the implementation for these abstract methods, like in displayMenu().
### **Encapsulation**
The classes in the code encapsulate data and methods within their respective classes. For example, the Student class encapsulates attributes like registeredCourses, completedCourses, complaints, currentSemester, sgpa, cgpa, and grades. These attributes are typically marked as private to ensure data hiding and prevent direct access from outside the class.
### **Polymorphism**
The displayMenu() method is overridden in each of the derived classes, Student, Professor, and Admin to provide specific implementations for their respective menus. This allows objects of different classes to be treated as objects of the base class (User) while exhibiting different behaviors.
### **Composition**
The Student class has a List of Course objects registeredCourses and completedCourses and a List of Complaint objects complaints. This composition allows students to have multiple registered courses, completed courses, and complaints.
### **Generic Classes**
The use of List< T > in method parameters ( manageCourseCatalog(List<Course> courses)) allows the methods to operate on lists of any specific type, such as Course, Student, or Professor. This means that the same method can handle lists of different data types without needing to rewrite code for each type.This approach reduces code duplication and enhances type safety, as it ensures that only the specified type can be added to the list.
### **Object Class**
In Java, all classes implicitly inherit from the Object class, which is the root of the class hierarchy. This means that both the Student and TeachingAssistant classes inherit methods from the Object class, such as toString(), equals(), and hashCode().
This inheritance allows TAs to utilize any overridden methods from the Student class, ensuring that they can interact with other objects in a consistent manner.
### **Exception Handling**
**Custom Exceptions:** Classes like CourseFull, DropDeadlinePassed, and InvalidLogin extend the RuntimeException class. This allows for clear error signaling when specific conditions are not met.
**Try-Catch Blocks:** The use of try-catch blocks in methods allows for catching exceptions thrown during invalid attempts. This ensures that the program does not crash and provides meaningful feedback to users when an error occurs.
