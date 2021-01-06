package com.petmeeting.zoocheck;

public class WalkingReview {

    ReviewId id;
    String message;
    int score;

    public WalkingReview(String userId, String message, int score) {
        setId(userId);
        setMessage(message);
        setScore(score);
    }

    public void setId(String userId) {
        this.id = new ReviewId(userId);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ReviewId getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int getScore() {
        return score;
    }

}