package com.example.visionnewsb1.Modal;

public class News {
    private String heading,image, description;

    public News() {
    }

    public News(String heading, String image, String description) {
        this.heading = heading;
        this.image = image;
        this.description = description;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
