package org.example.libraryfx.model;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String email;
    private String login;
    private String password;
    private String role;
    private Integer course;
    private String faculty;
    private String groupName;
    private String teacherCode;
    private String librarianCode;

    public User() {}

    public User(int id, String firstName, String lastName, String middleName, String phone,
                String email, String login, String password, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.phone = phone;
        this.email = email;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Integer getCourse() { return course; }
    public void setCourse(Integer course) { this.course = course; }

    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public String getTeacherCode() { return teacherCode; }
    public void setTeacherCode(String teacherCode) { this.teacherCode = teacherCode; }

    public String getLibrarianCode() { return librarianCode; }
    public void setLibrarianCode(String librarianCode) { this.librarianCode = librarianCode; }

    public String getFullName() {
        return lastName + " " + firstName + (middleName != null ? " " + middleName : "");
    }
}

