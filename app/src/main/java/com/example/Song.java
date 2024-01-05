package com.example;

public class Song {
    private String title;
    private int fileName;
    private int imgPath;

    public Song(String title, int fileName, int imgPath) {
        this.title = title;
        this.fileName = fileName;
        this.imgPath = imgPath;
    }

    // Add getters for title, fileName, and imgPath
    public String getTitle() {
        return title;
    }

    public int getFile() {
        return fileName;
    }

    public int getImg() {
        return imgPath;
    }

}
