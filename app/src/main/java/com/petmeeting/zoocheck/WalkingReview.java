package com.petmeeting.zoocheck;

public class WalkingReview {

    ReviewId id;
    String ownerReview, dogReview;
    int score;
    int activity, sociality, aggression, bark;

    public WalkingReview(String userId, String ownerReview, String dogReview, int score,
                         int activity, int sociality, int aggression, int bark) {
        setId(userId);
        this.score = score;
        this.dogReview = dogReview;
        this.ownerReview = ownerReview;
        this.activity = activity;
        this.sociality = sociality;
        this.aggression = aggression;
        this.bark = bark;
    }

    public void setId(String userId) {
        this.id = new ReviewId(userId);
    }

    public ReviewId getId() {
        return id;
    }

    public String getOwnerReview() {
        return ownerReview;
    }

    public String getDogReview() { return dogReview; }

    public int getScore() {
        return score;
    }

    public int getActivity() {
        return activity;
    }

    public int getSociality() {
        return sociality;
    }

    public int getAggression() {
        return aggression;
    }

    public int getBark() {
        return bark;
    }

}