package com.petmeeting.zoocheck;

public class ReviewId {
    Users user;
    long walkId;

    public ReviewId(String userId, long walkId) {
        this.walkId = walkId;
        setUser(userId);
    }

    public void setUser(String userId) {
        this.user = new Users(userId, "", "");
    }
}
