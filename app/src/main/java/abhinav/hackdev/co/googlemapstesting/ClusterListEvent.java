package abhinav.hackdev.co.googlemapstesting;

import java.util.List;

/**
 * Created by abhinav on 09/06/16.
 */
public class ClusterListEvent {

    private List<MyClusterItem> myClusterItemList ;

    public ClusterListEvent(List<MyClusterItem> myClusterItemList) {
        this.myClusterItemList = myClusterItemList;
    }

    public List<MyClusterItem> getMyClusterItemList() {
        return myClusterItemList;
    }

    public void setMyClusterItemList(List<MyClusterItem> myClusterItemList) {
        this.myClusterItemList = myClusterItemList;
    }
}
