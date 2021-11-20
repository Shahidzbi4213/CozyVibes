package com.gulehri.edu.pk.mildvideos.model;

/**
 * Created by Shahid Iqbal on 12,November,2021
 */
public class Categories {

    private final String name, url;

    public Categories(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public static final Categories[] CATEGORIES = {

            //url = "https://api.pexels.com/videos/search?query=" + name + "&per_page=50" + "&page=" + pageNumber;
            new Categories(
                    "Animals",
                    "https://images.pexels.com/photos/2541239/pexels-photo-2541239.jpeg"
            ),
            new Categories(
                    "Abstract",
                    "https://images.pexels.com/photos/2699282/pexels-photo-2699282.jpeg"
            ),
            new Categories(
                    "Birds",
                    "https://images.pexels.com/photos/1316294/pexels-photo-1316294.jpeg"
            ),
            new Categories(
                    "colors",
                    "https://images.pexels.com/photos/3580627/pexels-photo-3580627.jpeg"
            ),
            new Categories(
                    "Galaxy",
                    "https://images.pexels.com/photos/32237/pexels-photo.jpg"
            ),
            new Categories(
                    "Green",
                    "https://images.pexels.com/photos/409696/pexels-photo-409696.jpeg"
            ),
            new Categories(
                    "Landscape",
                    "https://images.pexels.com/photos/2559941/pexels-photo-2559941.jpeg"
            ),
            new Categories(
                    "Music",
                    "https://images.pexels.com/photos/995301/pexels-photo-995301.jpeg"
            ),
            new Categories(
                    "Nature",
                    "https://images.pexels.com/photos/5409751/pexels-photo-5409751.jpeg"
            ),
            new Categories(
                    "Space",
                    "https://images.pexels.com/photos/45208/world-earth-planet-globe-45208.jpeg"
            ),
            new Categories(
                    "Rain",
                    "https://images.pexels.com/photos/1529360/pexels-photo-1529360.jpeg"
            ),
            new Categories(
                    "Sunset",
                    "https://images.pexels.com/photos/1237119/pexels-photo-1237119.jpeg"
            ),
            new Categories(
                    "Mountains",
                    "https://images.pexels.com/photos/355241/pexels-photo-355241.jpeg"
            ),
            new Categories(
                    "Waterfall",
                    "https://images.pexels.com/photos/3086736/pexels-photo-3086736.jpeg"
            ),
    };

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
