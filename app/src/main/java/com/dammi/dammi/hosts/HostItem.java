package com.dammi.dammi.hosts;

/**
 * Created by script on 12/9/15.
 */
public class HostItem
{
    int image,bgImage;
    String rating;
    String about;
    int noOfActivities,noOfEvents;

    public HostItem(int image, String rating, String about, int noOfActivities,int noOfEvents,int bgImage)
    {
        this.image = image;
        this.rating = rating;
        this.about = about;
        this.noOfActivities = noOfActivities;
        this.noOfEvents=noOfEvents;
        this.bgImage=bgImage;
    }
}
