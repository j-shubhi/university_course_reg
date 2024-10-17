package system;

import java.time.LocalDateTime;
// deadline to drop the course is 1min + time when registered

class Course {
    private String courseCode;
    private String title;
    String professor;
    private int credits;
    private String prerequisite;
    private String timings;
    private int enrollmentLimit;
    private String syllabus;
    private String officeHours;
    private int currentEnrollment;
    private LocalDateTime dropDeadline;

    public Course(String courseCode, String title, String professor, int credits, String prerequisite, String timings, int enrollmentLimit, String syllabus, String officeHours) {
        this.courseCode = courseCode;
        this.title = title;
        this.professor = professor;
        this.credits = credits;
        this.prerequisite = prerequisite;
        this.timings = timings;
        this.enrollmentLimit = enrollmentLimit;
        this.syllabus = syllabus;
        this.officeHours = officeHours;
        this.currentEnrollment = 0;
        this.dropDeadline = LocalDateTime.now().plusMinutes(1);
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getProfessor() {
        return professor;
    }

    public int getCredits() {
        return credits;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public String getTimings() {
        return timings;
    }
    public int getEnrollmentLimit() {
        return enrollmentLimit;
    }

    public void setEnrollmentLimit(int enrollmentLimit) {
        this.enrollmentLimit = enrollmentLimit;
    }

    public int getCurrentEnrollment() {
        return currentEnrollment;
    }

    public void incrementEnrollment() {
        currentEnrollment++;
    }

    public void decrementEnrollment() {
        currentEnrollment--;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public String getOfficeHours() {
        return officeHours;
    }

    public void setOfficeHours(String officeHours) {
        this.officeHours = officeHours;
    }

    public LocalDateTime getDropDeadline(){
        return dropDeadline;
    }
}