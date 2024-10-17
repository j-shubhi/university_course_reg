package system;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<Course> availableCourses = new ArrayList<>();
    private static ArrayList<Student> students = new ArrayList<>();
    private static ArrayList<Professor> professors = new ArrayList<>();
    private static List<TA> teachingAssistants = new ArrayList<>();
    private static Admin admin;

    public static void main(String[] args) {

        admin = new Admin("admin@iiitd.ac.in");

        availableCourses.add(new Course("MTH101", "LA", "subhajit@iiitd.ac.in", 4, "", "Mon/Wed 10-11 AM", 600, "eigen, vector space, linearity", "Mon 2-3 PM"));
        availableCourses.add(new Course("CSE101", "Introduction to Programming", "pankaj@iiitd.ac.in", 4, "", "Tue/Thu 1-2:30 PM", 300, "Basics of programming", "Tue/Thu 3-4 PM"));
        availableCourses.add(new Course("ECE201", "Digital circuits", "tammam@iiitd.ac.in", 2, "", "Fri 9-10 AM", 30, "decoder, multiplexer", "Fri 11 AM-12 PM"));
        availableCourses.add(new Course("DES101", "iHCI", "sonal@iiitd.ac.in", 4, "", "Tues 10-11 AM", 600, "user interface", "Tues 2-3 PM"));
        availableCourses.add(new Course("COM101", "COM", "payal@iiitd.ac.in", 4, "", "Wed 3-6 PM", 600, "communication skills", "Mon 1-2 PM"));

        professors.add(new Professor("tammam@iiitd.ac.in", "123"));
        professors.add(new Professor("anoop@iiitd.ac.in", "456"));
        professors.add(new Professor("shad@iiitd.ac.in", "789"));
        professors.add(new Professor("pankaj@iiitd.ac.in", "122"));

        students.add(new Student("shubhi@iiitd.ac.in", "111"));
        students.add(new Student("neha@iiitd.ac.in", "222"));
        students.add(new Student("sumit@iiitd.ac.in", "333"));

        teachingAssistants.add(new TA("harry@iiitd.ac.in", "t1"));
        teachingAssistants.add(new TA("ginny@iiitd.ac.in", "t2"));
        teachingAssistants.add(new TA("luna@iiitd.ac.in", "t3"));

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nWelcome to the Academic Management System!");
            System.out.println("1. Login as Student");
            System.out.println("2. Login as Professor");
            System.out.println("3. Login as TA");
            System.out.println("4. Login as Admin");
            System.out.println("5. Sign Up as Student");
            System.out.println("6. Sign Up as Professor");
            System.out.println("7. Sign Up as TA");
            System.out.println("8. Exit");

            System.out.println("Choose number: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    loginAsStudent(scanner);
                    break;

                case 2:
                    loginAsProfessor(scanner);
                    break;

                case 3:
                    loginAsTeachingAssistant(scanner);
                    break;

                case 4:
                    loginAsAdmin(scanner);
                    break;

                case 5:
                    signUpAsStudent(scanner);
                    break;

                case 6:
                    signUpAsProfessor(scanner);
                    break;

                case 7:
                    signUpAsTeachingAssistant(scanner);
                    break;

                case 8:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid");
            }
        }
        scanner.close();
    }

    private static void loginAsStudent(Scanner scanner) {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            boolean found = false;
            for (Student student : students) {
                if (student.login(email, password)) {
                    found = true;
                    studentMenu(scanner, student);
                    return;
                }
            }

            if (!found) {
                throw new InvalidLogin("Invalid");
            }

        }
        catch (InvalidLogin e) {
            System.out.println(e.getMessage());
        }
    }

    private static void studentMenu(Scanner scanner, Student student) {
        boolean flag = true;

        while (flag) {
            System.out.println("Current Semester: " + student.getCurrentSemester());
            student.displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    student.viewAvailableCourses(availableCourses);
                    break;

                case 2:
                    System.out.print("Enter the Course Code want to Register: ");
                    String codeToRegister = scanner.nextLine();
                    Course selectedCourseToRegister = null;

                    for (Course c : availableCourses) {
                        if (c.getCourseCode().equalsIgnoreCase(codeToRegister)) {
                            selectedCourseToRegister = c;
                            break;
                        }
                    }

                    try {
                        if (selectedCourseToRegister != null) {
                            student.registerCourse(selectedCourseToRegister);
                        }
                        else {
                            System.out.println("Invalid");
                        }
                    }
                    catch (CourseFull e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    student.viewSchedule();
                    break;

                case 4:
                    student.trackAcademicProgress();
                    break;

                case 5:
                    System.out.print ("Enter the Course Code want to Drop: ");
                    String codeToDrop= scanner.nextLine();
                    Course selectedCourseToDrop= null;

                    for (Course c : student.registeredCourses) {
                        if (c.getCourseCode().equalsIgnoreCase(codeToDrop)) {
                            selectedCourseToDrop= c;
                            break;
                        }
                    }

                    try {
                        if (selectedCourseToDrop != null) {
                            student.dropCourse(selectedCourseToDrop);
                            System.out.println("Successfully dropped " + selectedCourseToDrop.getTitle());
                        }
                        else {
                            System.out.println("Invalid");
                        }
                    }
                    catch (DropDeadlinePassed e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 6:
                    student.submitComplaint(scanner.nextLine());
                    break;
                case 7:
                    System.out.print("Enter Course Code to submit feedback: ");
                    String code = scanner.nextLine();
                    Course selectedCourse = findCourseByCode(code);
                    if (selectedCourse != null) {
                        System.out.print("Enter your rating (1-5): ");
                        double rating = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter your comment: ");
                        String comment = scanner.nextLine();
                        student.submitFeedback(selectedCourse, rating, comment);
                    }
                    else {
                        System.out.println("Invalid");
                    }
                    break;
                case 8:
                    System.out.print("Enter Course Code to view feedback: ");
                    String codeToView = scanner.nextLine();
                    Course viewCourse = findCourseByCode(codeToView);
                    if (viewCourse != null) {
                        student.viewFeedback(viewCourse);
                    }
                    else {
                        System.out.println("Invalid");
                    }
                    break;
                case 9:
                    flag=false;
                    break;

                default:
                    System.out.println ("Invalid");
            }
        }
    }

    private static void loginAsProfessor(Scanner scanner) {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (Professor professor : professors) {
            if (professor.login(email, password)) {
                professorMenu(scanner, professor, availableCourses, students);
                return;
            }
        }
        System.out.println("Invalid");
    }

    private static void professorMenu(Scanner scanner, Professor professor, List<Course> availableCourses, List<Student> students) {
        boolean flag = true;

        while (flag) {
            professor.displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    professor.manageCourses(availableCourses);
                    break;

                case 2:
                    professor.viewEnrolledStudents(students, availableCourses);
                    break;

                case 3:
                    System.out.print("Enter Course Code to view feedback: ");
                    String codeToCheck = scanner.nextLine();
                    Course checkCourse = findCourseByCode(codeToCheck);
                    if (checkCourse != null && checkCourse.getProfessor().equals(professor.getEmail())) {
                        professor.viewFeedback(students, availableCourses);
                    } else {
                        System.out.println("Invalid");
                    }
                    break;

                case 4:
                    flag = false;
                    break;

                default:
                    System.out.println("Invalid");
            }
        }
    }
    private static Course findCourseByCode(String code) {
        for (Course c : availableCourses) {
            if (c.getCourseCode().equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;
    }

    private static void loginAsAdmin(Scanner scanner) {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (admin.login(email, password)) {
            adminMenu(scanner, admin, new ArrayList<>(), availableCourses, students);
        }
        else {
            System.out.println("Invalid");
        }
    }

    private static void adminMenu(Scanner scanner, Admin admin, List<Complaint> complaints, List<Course> courses, List<Student> students) {
        boolean flag = true;

        while (flag) {
            admin.displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    admin.manageCourseCatalog(courses);
                    break;

                case 2:
                    admin.manageStudentRecords(students);
                    break;

                case 3:
                    admin.assignProfessorsToCourses(courses, professors);
                    break;

                case 4:
                    admin.handleComplaints(complaints);
                    break;

                case 5:
                    flag = false;
                    break;

                default:
                    System.out.println("Invalid");
            }
        }
    }

    private static void signUpAsStudent(Scanner scanner) {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Create password: ");
        String password = scanner.nextLine();

        students.add(new Student(email, password));
        System.out.println("Student signed up successfully");
    }

    private static void signUpAsProfessor(Scanner scanner) {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Create password: ");
        String password = scanner.nextLine();

        professors.add(new Professor(email, password));
        System.out.println("Professor signed up successfully");
    }
    private static void signUpAsTeachingAssistant(Scanner scanner) {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Create password: ");
        String password = scanner.nextLine();
        teachingAssistants.add(new TA(email, password));
        System.out.println("Teaching Assistant signed up successfully.");
    }

    private static void loginAsTeachingAssistant(Scanner scanner) {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        for (TA ta : teachingAssistants) {
            if (ta.login(email, password)) { // Assuming login method is inherited from User
                taMenu(scanner, ta);
                return;
            }

        }
        System.out.println("Invalid");
    }

    private static void taMenu(Scanner scanner, TA ta) {
        boolean flag = true;
        while (flag) {
            System.out.println("\nTeaching Assistant Menu:");
            System.out.println("1. View Student Grades");
            System.out.println("2. Update Student Grades");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    ta.viewStudentGrades(students);
                    break;
                case 2:
                    System.out.print("Enter Student Email: ");
                    String studentEmail = scanner.nextLine();
                    Student studentToUpdate = null;
                    for (Student student : students) {
                        if (student.email.equals(studentEmail)) {
                            studentToUpdate = student;
                            break;
                        }
                    }
                    if (studentToUpdate != null) {
                        System.out.print("Enter Course Code: ");
                        String courseCode = scanner.nextLine();
                        Course courseToUpdate = findCourseByCode(courseCode);
                        if (courseToUpdate != null) {
                            System.out.print("Enter New Grade: ");
                            double newGrade = scanner.nextDouble();
                            ta.updateStudentGrade(studentToUpdate, courseToUpdate, newGrade);
                        }
                        else {
                            System.out.println("Invalid");
                        }
                    }
                    else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 3:
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid");
            }
        }
    }
}