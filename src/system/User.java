package system;

abstract class User {
    protected String email;
    protected String password;

    // common features of student, professor, admin, and now TA
    public User(String email, String pswd) {
        this.email = email;
        this.password = pswd;
    }
    public String getEmail() {
        return email;
    }
    // menu-driven programme re-written in subclasses i.e. student, Professor and Admin class, and now TA
    public abstract void displayMenu();

    // to verify the email and password entered
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }
}
