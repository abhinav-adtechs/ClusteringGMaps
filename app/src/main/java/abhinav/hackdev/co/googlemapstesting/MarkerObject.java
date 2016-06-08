package abhinav.hackdev.co.googlemapstesting;


import com.google.gson.annotations.SerializedName;

public class MarkerObject {

    @SerializedName("user_id")
    private String userId ;

    @SerializedName("c_x")
    private double locLat ;

    @SerializedName("c_y")
    private double locLong ;

    @SerializedName("vid_id")
    private String videoId ;

    public MarkerObject() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getLocLat() {
        return locLat;
    }

    public void setLocLat(double locLat) {
        this.locLat = locLat;
    }

    public double getLocLong() {
        return locLong;
    }

    public void setLocLong(double locLong) {
        this.locLong = locLong;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
