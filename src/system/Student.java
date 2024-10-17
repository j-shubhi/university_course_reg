package system;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Map;

class Student extends User implements CourseOperations{
    ArrayList<Course> registeredCourses;
    private List<Course> completedCourses;
    private ArrayList<Complaint> complaints;
    private int currentSemester;
    private double sgpa;
    private double cgpa;
    private HashMap<Course, Feedback<String>> courseFeedback;
    private Map<Course, Double> grades;


    public Student(String email, String password) {
        super(email, password);
        this.registeredCourses = new ArrayList<>();
        this.completedCourses = new ArrayList<>();
        this.complaints = new ArrayList<>();
        this.grades = new HashMap<>();
        this.sgpa = 0.0;
        this.cgpa = 0.0;
        this.currentSemester = 1;
        this.courseFeedback = new HashMap<>();

    }
    public void selectSemester(int semester) {
        if (semester > 0) {
            this.currentSemester = semester;
            System.out.println("Current semester set to: " + currentSemester);
        }
        else {
            System.out.println("Invalid semester selection.");
        }
    }
    @Override
    public void displayMenu() {
        System.out.println("\nStudent Menu:");
        System.out.println("1. View Available Courses");
        System.out.println("2. Register for Courses");
        System.out.println("3. View Schedule");
        System.out.println("4. Track Academic Progress");
        System.out.println("5. Drop Courses");
        System.out.println("6. Submit Complaints");
        System.out.println("7. Submit Feedback");
        System.out.println("8. View Feedback");
        System.out.println("9. Exit");
    }
    public void viewAvailableCourses(List<Course> availableCourses) {
        System.out.println("Available Courses:");
        for (Course course : availableCourses) {
            System.out.println(course.getCourseCode() + ": " + course.getTitle() + ", Professor: " + course.getProfessor() +
                    ", Credits: " + course.getCredits() + ", Prerequisites: " + course.getPrerequisite() +
                    ", Timings: " + course.getTimings());
        }
    }

    public void registerCourse(Course course) throws CourseFull{
        if (registeredCourses.contains(course)) {
            System.out.println("You are already registered for this course.");
            return;
        }
        int totalCredits = calculateTotalCredits(registeredCourses);
        if (course.getEnrollmentLimit() <= course.getCurrentEnrollment()) {
            throw new CourseFull("Cannot register for " + course.getTitle() + ". Course is full.");
        }
        if (totalCredits + course.getCredits() > 20) {
            System.out.println("Cannot register for " + course.getTitle() + ". Credit limit exceeded.");
            return;
        }

        if (isPrerequisiteMet(course.getPrerequisite())) {
            course.incrementEnrollment();
            registeredCourses.add(course);
            System.out.println("Registered for course: " + course.getTitle());
        }
        else {
            System.out.println("Cannot register for " + course.getTitle() + ". Prerequisite not met.");
        }
        completedCourses.add(course);

        grades.put(course, 0.0);
    }

    private int calculateTotalCredits(List<Course> courses) {
        int totalCredits = 0;
        for (Course course : courses) {
            totalCredits += course.getCredits();
        }
        return totalCredits;
    }

    private boolean isPrerequisiteMet(String prerequisite) {
        if (prerequisite.isEmpty()) return true;


        String[] requiredCourses = prerequisite.split(",");

        for (String requiredCourse : requiredCourses) {
            boolean found = false;

            for (Course completedCourse : completedCourses) {
                if (completedCourse.getCourseCode().equals(requiredCourse.trim())) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                return false;
            }
        }
        return true;
    }
    public void completeSemester() {
        double totalPoints = 0.0;
        int totalCredits = 0;

        for (Course course : registeredCourses) {
            Double grade = grades.get(course);
            if (grade != null) {
                totalPoints += grade * course.getCredits();
                totalCredits += course.getCredits();
            }
        }

        sgpa = totalPoints / totalCredits;

        cgpa = sgpa;

        completedCourses.addAll(registeredCourses);
        registeredCourses.clear();
        currentSemester++;

    }
    public int getCurrentSemester() {
        return currentSemester;
    }

    public void addCompletedCourse(Course course, double grade) {
        completedCourses.add(course);
        grades.put(course, grade);
    }

    public void viewSchedule() {
        System.out.println("Schedule:");
        for (Course course : registeredCourses) {
            System.out.println(course.getTitle() + " - " + course.getTimings() + " by " + course.getProfessor());
        }
    }


    public void trackAcademicProgress() {
        double totalPoints = 0.0;
        int totalCredits = 0;

        for (Course course : completedCourses) {
            Double grade = grades.get(course);
            if (grade != null) {
                totalPoints += grade * course.getCredits();
                totalCredits += course.getCredits();
            }
        }

        sgpa = totalPoints / totalCredits;

        cgpa = sgpa;

        System.out.printf("SGPA: %.2f, CGPA: %.2f%n", sgpa, cgpa);
    }

    public void dropCourse(Course course) {
        if (LocalDateTime.now().isAfter(course.getDropDeadline())) { // Check if current date is past the drop deadline
            throw new DropDeadlinePassed("Cannot drop course " + course.getTitle() + ": Deadline has passed.");
        }
        if (registeredCourses.remove(course)) {
            System.out.println("Dropped course: " + course.getTitle());
        }
        else {
            System.out.println("Course not found in registered courses.");
        }
    }

    public void submitComplaint(String description) {
        Complaint complaint = new Complaint(description);
        complaints.add(complaint);
        System.out.println("Complaint submitted: " + description);
    }

    public void setGrade(Course course, double grade) {
        grades.put(course, grade);
        System.out.println("Grade for " + course.getTitle() + " set to: " + grade);
    }

    //feedback
    public void submitFeedback(Course course, double rating, String comment) {
        Feedback<String> feedback = courseFeedback.getOrDefault(course, new Feedback<>());
        String feedbackEntry = "Rating: " + rating + " - Comment: " + comment;
        feedback.addFeedback(feedbackEntry);
        courseFeedback.put(course, feedback);
    }

    public HashMap<Course, Feedback<String>> getCourseFeedback() {
        return courseFeedback;
    }
    public void completeCourse(Course course, double rating, String comment) {
        if (registeredCourses.contains(course)) {
            completedCourses.add(course);
            registeredCourses.remove(course); // Optionally remove from registered courses
            submitFeedback(course, rating, comment); // Call to submit feedback
            System.out.println("Course " + course.getTitle() + " completed successfully.");
        }
        else {
            System.out.println("You are not registered for this course.");
        }
    }
    public void viewFeedback(Course course) {
        Feedback<String> feedback = courseFeedback.get(course);
        if (feedback != null) {
            System.out.println("Feedback for " + course.getTitle() + ":");
            for (String entry : feedback.getFeedbackList()) {
                System.out.println(entry);
            }
        }
        else {
            System.out.println("No feedback available for this course.");
        }
    }
    public List<Course> getCompletedCourses() {
        return completedCourses;
    }

    public Map<Course, Double> getGrades() {
        return grades;
    }
}