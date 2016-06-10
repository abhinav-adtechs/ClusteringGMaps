package abhinav.hackdev.co.googlemapstesting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ClusterListActivity extends AppCompatActivity {

    private static final String TAG = "SPLTAG";
    private List<RVData> myClusterItems = new ArrayList<>() ;
    private RecyclerView recyclerView ;
    private RVAdapter adapter ;
    String clusterListString ;
    private JSONArray jsonResponseArray ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cluster_list);


        clusterListString = getIntent().getStringExtra("clusterList") ;
        Log.d(TAG, "onCreate: " + clusterListString);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMain) ;
        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListner(this, new RecyclerViewItemClickListner.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG, "onItemClick: " + position);

                handleClick(position) ;
            }
        }));

        LinearLayoutManager llm = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(llm);


        adapter = new RVAdapter();
        recyclerView.setAdapter(adapter);

        fillListData(clusterListString);


    }

    private void handleClick(int position) {
        Intent videoPlay = new Intent(this, VideoPlayActivity.class) ;
        try {
            Log.d(TAG, "handleClick: " + jsonResponseArray.getJSONObject(position).getString("videoId"));
            videoPlay.putExtra("videoId", jsonResponseArray.getJSONObject(position).getString("videoId")) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            startActivity(videoPlay);
        }
    }

    private void fillListData(String clusterListString) {

        try {
            jsonResponseArray = new JSONArray(clusterListString) ;

            for (int i = 0; i < jsonResponseArray.length(); i++) {
                JSONObject jsonObject = jsonResponseArray.getJSONObject(i) ;

                Log.d(TAG, "fillListData: " + jsonObject.getString("userId"));
                myClusterItems.add(new RVData(jsonObject.getString("position"))) ;
            }
        } catch (JSONException e) {
            Log.d(TAG, "fillListData: ERROR ");
            e.printStackTrace();
        }

        adapter.setStrings(myClusterItems);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
