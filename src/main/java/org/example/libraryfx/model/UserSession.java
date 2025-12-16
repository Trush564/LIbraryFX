package org.example.libraryfx.model;

public class UserSession {
    private User user;
    private int userId;

    public UserSession(User user, int userId) {
        this.user = user;
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public int getUserId() {
        return userId;
    }

    public String getRole() {
        return user.getRole();
    }

    public String getLogin() {
        return user.getLogin();
    }
}




