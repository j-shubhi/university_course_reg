package system;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Professor extends User {
    List<Course> courses;

    public Professor(String email, String password) {
        super(email, password);
        this.courses = new ArrayList<>();
    }

    @Override
    public void displayMenu() {
        System.out.println("Professor Menu:");
        System.out.println("1. Manage Courses");
        System.out.println("2. View Enrolled Students");
        System.out.println("3. View Feedback");
        System.out.println("4. Exit");
    }

    public void manageCourses(List<Course> allCourses) {
        System.out.println("Courses Managed by Professor " + email + ":");
        for (Course course : allCourses) {
            if (course.getProfessor().equals(this.email)) {
                System.out.println(course.getCourseCode() + ": " + course.getTitle());
            }
        }

        System.out.print("Enter course code to update details or 'exit' to quit: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("exit")) return;

        for (Course course : allCourses) {
            if (course.getCourseCode().equalsIgnoreCase(input) && course.getProfessor().equals(this.email)) {
                System.out.println("Updating details for: " + course.getTitle());
                System.out.print("Enter new syllabus: ");
                course.setSyllabus(scanner.nextLine());
                System.out.print("Enter new office hours: ");
                course.setOfficeHours(scanner.nextLine());
                System.out.print("Enter new enrollment limit: ");
                course.setEnrollmentLimit(scanner.nextInt());
                scanner.nextLine(); // Consume newline
                System.out.println("Course details updated successfully.");
                return;
            }
        }

        System.out.println("Course not found or you do not have permission to update it.");
    }

    public void viewEnrolledStudents(List<Student> students, List<Course> allCourses) {
        for (Course course : allCourses) {
            if (course.getProfessor().equals(this.email)) {
                System.out.println("Enrolled Students in " + course.getTitle() + ":");
                for (Student student : students) {
                    if (student.registeredCourses.contains(course)) {
                        System.out.println(student.getEmail());
                    }
                }
            }
        }
    }

    public void viewFeedback(List<Student> students, List<Course> allCourses) {
        for (Course course : allCourses) {
            if (course.getProfessor().equals(this.getEmail())) {
                System.out.println("Feedback for " + course.getTitle() + ":");
                for (Student student : students) {
                    if (student.registeredCourses.contains(course)) {
                        Feedback<String> feedback = student.getCourseFeedback().get(course);
                        if (feedback != null && !feedback.getFeedbackList().isEmpty()) {
                            System.out.println(student.getEmail() + "'s Feedback:");
                            for (String entry : feedback.getFeedbackList()) {
                                System.out.println(entry);
                            }
                        }
                        else {
                            System.out.println(student.getEmail() + ": No feedback available.");
                        }
                    }
                }
            }
        }
    }
}


