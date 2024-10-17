package system;

import java.util.List;

public class TA extends Student {
    public TA(String email, String password) {
        super(email, password);
    }

    public void viewStudentGrades(List<Student> students) {
        for (Student student : students) {
            System.out.println("Grades for " + student.getEmail() + ":");
            for (Course course : student.getCompletedCourses()) {
                Double grade = student.getGrades().get(course);
                if (grade != null) {
                    System.out.printf("%s - Grade: %.2f%n", course.getTitle(), grade);
                }
                else {
                    System.out.printf("%s - No grade assigned yet.%n", course.getTitle());
                }
            }
        }
    }

    public void updateStudentGrade(Student student, Course course, double newGrade) {
        if (student.getCompletedCourses().contains(course)) {
            student.getGrades().put(course, newGrade);
            System.out.println("Updated grade for " + student.getEmail() + " in " + course.getTitle() + " to " + newGrade);
        }
        else {
            System.out.println("Student has not completed this course.");
        }
    }
}