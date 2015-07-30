package com.learningandroid.rajesh.moviesapp1.data;

/**
 * Created by RajPriyaMAC on 7/28/15.
 */
public class MovieData {
    private long movieId;
    private String imagePath;
    private String title; //original title
    private String overview; //A plot synopsis
    private double popularity;
    private double voteAverage;


    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public String toString() {
        return "MovieData{" +
                "movieId='" + movieId + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", voteAverage=" + voteAverage +
                '}';
    }

}
