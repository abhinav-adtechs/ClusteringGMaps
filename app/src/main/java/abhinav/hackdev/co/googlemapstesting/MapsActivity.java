package abhinav.hackdev.co.googlemapstesting;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
            ClusterManager.OnClusterClickListener<MyClusterItem>, ClusterManager.OnClusterItemClickListener<MyClusterItem>{

    private static final String TAG = "TEST";
    private GoogleMap mMap;

    private ClusterManager<MyClusterItem> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void fetchMarkers() {
        String fetcherUrl = "https://api.swisapp.co/file/dummymarkers" ;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, fetcherUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray responseArray = new JSONArray(response) ;
                            Log.d(TAG, "onResponse: " + responseArray);
                            Log.d(TAG, "onResponse: " + responseArray.getJSONObject(0));

                            Gson gson = new Gson() ;
                            Type responseList = new TypeToken<List<MarkerObject>>(){}.getType() ;
                            List<MarkerObject> markerObjects = (List<MarkerObject>) gson.fromJson(response, responseList) ;

                            handleMarkers(markerObjects) ;

                            /*
                            Gson gson = new Gson();
                            String jsonOutput = "Your JSON String";
                            Type listType = new TypeToken<List<Post>>(){}.getType();
                            List<Post> posts = (List<Post>) gson.fromJson(jsonOutput, listType);
                            * */
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error + " " );
                error.printStackTrace();
            }
        }) ;

        AppController.getInstance().addToRequestQueue(stringRequest);

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        setUpClusterer() ;
    }

    private void setUpClusterer() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(12.968953, 79.156829), 13));

        mClusterManager = new ClusterManager<MyClusterItem>(this, mMap);

        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);

        fetchMarkers() ;

    }

    private void handleMarkers(List<MarkerObject> markerObjectList) {
        for (int i = 0; i < markerObjectList.size(); i++) {
            MyClusterItem item = new MyClusterItem(markerObjectList.get(i).getLocLat(), markerObjectList.get(i).getLocLong(),
                   markerObjectList.get(i).getUserId() , markerObjectList.get(i).getVideoId()) ;
            mClusterManager.addItem(item);
        }

    }

    @Override
    public boolean onClusterItemClick(MyClusterItem myClusterItem) {
        Log.d(TAG, "onClusterItemClick: " + myClusterItem.getPosition() + myClusterItem.getVideoId());
        Intent intent = new Intent(getApplicationContext(), VideoPlayActivity.class );
        intent.putExtra("vidId", myClusterItem.getVideoId());
        intent.putExtra("userId",myClusterItem.getUserId());
        startActivity(intent);
        return false;
    }


    @Override
    public boolean onClusterClick(Cluster cluster) {
        Log.d(TAG, "onClusterClick: " + cluster.getSize() );
        List<MyClusterItem> list = new ArrayList(cluster.getItems()) ;

        for (MyClusterItem myClusterItem :
                list) {
            Log.d(TAG, "onClusterClick: " + myClusterItem.getUserId());
        }

        EventBus.getDefault().postSticky(new ClusterListEvent(list));

        Intent intent = new Intent(getApplicationContext(), ClusterListActivity.class) ;
        startActivity(intent);
        return false;
    }


}
