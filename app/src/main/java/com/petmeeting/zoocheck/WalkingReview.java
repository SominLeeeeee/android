package com.petmeeting.zoocheck;

public class WalkingReview {

    ReviewId id;
    String message;
    int score;

    public WalkingReview(String userId, String message, int score, long walkId) {
        setId(userId, walkId);
        setMessage(message);
        setScore(score);
    }

    public void setId(String userId, long walkId) {
        this.id = new ReviewId(userId, walkId);
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