package com.dammi.dammi.activitylist;

/**
 * Created by trees on 12/1/15.
 */
public class ActivityItem
{
    String title, location, hostName, duration, priceTag, imageUrl;
    int actId;

    public ActivityItem(String title, String location, String hostName, String duration, String priceTag, int actId, String imageUrl) {
        this.title = title;
        this.location = location;
        this.hostName = hostName;
        this.duration = duration;
        this.priceTag = priceTag;
        this.actId = actId;
        this.imageUrl = imageUrl;
    }
}
