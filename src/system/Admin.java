package system;
import java.util.List;
import java.util.Scanner;

class Admin extends User {

    private static final String FIXED_PASSWORD = "admin123";  // initialised password for admin

    public Admin(String email) {
        super(email, FIXED_PASSWORD);
    }

    @Override
    public void displayMenu() {
        System.out.println("Admin Menu:");
        System.out.println("1. Manage Course Catalog");
        System.out.println("2. Manage Student Records");
        System.out.println("3. Assign Professors to Courses");
        System.out.println("4. Handle Complaints");
        System.out.println("5. Exit");
    }

    public void manageCourseCatalog(List<Course> courses) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nManage Course Catalog:");
            System.out.println("1. View Courses");
            System.out.println("2. Add Course");
            System.out.println("3. Delete Course");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:// view
                    for (Course c : courses) {
                        System.out.println(c.getCourseCode() + ": " + c.getTitle());
                    }
                    break;

                case 2:// add
                    System.out.print("Enter Course Code: ");
                    String code = scanner.nextLine();
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Professor Email: ");
                    String professorEmail = scanner.nextLine();
                    System.out.print("Enter Credits: ");
                    int credits = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Prerequisites: ");
                    String prerequisites = scanner.nextLine();
                    System.out.print("Enter Timings: ");
                    String timings = scanner.nextLine();
                    System.out.print("Enter Enrollment Limit: ");
                    int enrollmentLimit = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Syllabus: ");
                    String syllabus = scanner.nextLine();
                    System.out.print("Enter Office Hours: ");
                    String officeHours = scanner.nextLine();

                    courses.add(new Course(code, title, professorEmail, credits, prerequisites, timings, enrollmentLimit, syllabus, officeHours));
                    System.out.println("Course added successfully.");
                    break;

                case 3://delete
                    System.out.print("Enter Course Code to delete: ");
                    String deleteCode = scanner.nextLine();

                    courses.removeIf(course -> course.getCourseCode().equalsIgnoreCase(deleteCode));
                    System.out.println("Course deleted successfully");
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid");
            }
        }
    }


    public void manageStudentRecords(List<Student> students) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\nEnter student email to view/update record or 'exit' to quit: ");
            String studentEmail = scanner.nextLine();

            if (studentEmail.equalsIgnoreCase("exit")) break;

            for (Student student : students) {
                System.out.print(student);
                if (student.email.equals(studentEmail)) {

                    for (Course c : student.getCompletedCourses()) {
                        Double grade = student.getGrades().get(c);
                        if (grade != null) {
                            System.out.printf("%s - Grade: %.2f%n", c.getTitle(), grade);
                        }
                        else {
                            System.out.printf("%s - No grade assigned yet.%n", c.getTitle());
                        }
                    }

                    System.out.print("\nEnter course code to update grade or 'skip' to continue: ");
                    String codeToUpdateGrade = scanner.nextLine();

                    if (!codeToUpdateGrade.equalsIgnoreCase("skip")) {
                        for (Course completedCourse : student.getCompletedCourses()) {
                            if (completedCourse.getCourseCode().equalsIgnoreCase(codeToUpdateGrade)) {
                                System.out.print ("Enter new grade: ");
                                double newGrade= scanner.nextDouble();
                                student.getGrades().put(completedCourse,newGrade);
                                break;
                            }
                        }
                    }
                    return;
                }
            }
            System.out.println ("Student not found.");
        }
    }

    public void assignProfessorsToCourses(List<Course> courses, List<Professor> professors) {
        Scanner scanner= new Scanner(System.in);

        while(true){
            System.out.print("\nEnter Course Code to assign a professor or 'exit' to quit: ");
            String code= scanner.nextLine();

            if(code.equalsIgnoreCase ("exit")) break;

            for(Course c:courses){
                if(c.getCourseCode().equalsIgnoreCase(code)){
                    System.out.print ("Available Professors:\n");

                    for(Professor p:professors){
                        if(!p.courses.contains(c)){
                            System.out.println(p.email);
                        }
                    }

                    System.out.print ("Select a professor's email to assign: ");
                    String profEmail= scanner.nextLine();

                    for(Professor p:professors){
                        if(p.email.equals(profEmail)){
                            c.professor= profEmail;
                            p.courses.add(c);
                            break;
                        }
                    }
                    break;
                }
            }
            System.out.print ("Professor assigned successfully.");
        }
    }

    public void handleComplaints(List<Complaint> complaints) {
        Scanner scanner= new Scanner(System.in);

        while(true){
            System.out.print("\nView complaints or 'exit' to quit: ");
            String action= scanner.nextLine();

            if(action.equalsIgnoreCase ("exit")) break;

            for(Complaint complaint:complaints){
                if(complaint.getStatus().equalsIgnoreCase ("Pending")){
                    System.out.printf("%s - Status: %s%n", complaint.getDescription(), complaint.getStatus());
                }
            }

            System.out.print("\nEnter complaint description which you wish to update status or 'skip' to continue: ");
            String desc= scanner.nextLine();

            if(!desc.equalsIgnoreCase ("skip")){
                for(Complaint complaint:complaints){
                    if(complaint.getDescription().equalsIgnoreCase(desc)){
                        complaint.setStatus ("Resolved");
                        break;
                    }
                }
            }
        }
    }
}