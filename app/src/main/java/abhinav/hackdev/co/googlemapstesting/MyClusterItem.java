package abhinav.hackdev.co.googlemapstesting;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;


public class MyClusterItem implements ClusterItem {
    private final LatLng mPosition;
    private final String userId ;
    private final String videoId ;

    public MyClusterItem(double lat, double lng, String userId, String videoId) {
        this.userId = userId;
        this.videoId = videoId;
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public LatLng getmPosition() {
        return mPosition;
    }

    public String getUserId() {
        return userId;
    }

    public String getVideoId() {
        return videoId;
    }
}
