package com.learningandroid.rajesh.moviesapp1.data;

import java.io.Serializable;


public class MovieData implements Serializable {
    private static final long serialVersionUID = 1623409823;
    private long movieId;
    private String imagePath;
    private String posterPath;
    private String title; //original title
    private String overview; //A plot synopsis
    private double popularity;
    private double voteAverage;//rating
    private String releaseDate;
    private String duration;


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


    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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
                ", releaseDate=" + releaseDate +
                ", posterPath=" + posterPath +
                ", duration=" + duration +
                '}';
    }

}
