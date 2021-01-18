package com.petmeeting.zoocheck;

public class ReviewId {
    Users user;
    Long walkId;

    public ReviewId(String userId) {
        //this.walkId = walkId;
        setUser(userId);
    }

    public void setUser(String userId) {
        this.user = new Users(userId, "", "");
    }
}
