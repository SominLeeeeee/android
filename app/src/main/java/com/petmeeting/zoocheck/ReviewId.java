package com.petmeeting.zoocheck;

public class ReviewId {
    Users user;

    public ReviewId(String userId) {
        setUser(userId);
    }

    public void setUser(String userId) {
        this.user = new Users(userId, "", "");
    }
}
