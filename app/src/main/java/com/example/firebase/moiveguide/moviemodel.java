package com.example.firebase.moiveguide;

public class moviemodel {
    String imagepath;
    String moviename;

    public moviemodel(String imagepath, String moviename) {
        this.imagepath = imagepath;
        this.moviename = moviename;
    }

    public moviemodel(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getImagepath() {
        return imagepath;
    }

    public String getMoviename() {
        return moviename;
    }
}
