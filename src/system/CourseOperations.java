package system;
import java.util.List;

public interface CourseOperations {
    void registerCourse(Course course);
    void dropCourse(Course course);
    void viewAvailableCourses(List<Course> availableCourses);
    void trackAcademicProgress();
}