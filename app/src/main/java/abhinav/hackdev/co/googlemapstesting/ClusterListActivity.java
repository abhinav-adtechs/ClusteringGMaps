package abhinav.hackdev.co.googlemapstesting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhinav on 09/06/16.
 */
public class ClusterListActivity extends AppCompatActivity {

    private List<RVData> myClusterItems = new ArrayList<>() ;
    private RecyclerView recyclerView ;
    private RVAdapter adapter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cluster_list);

        EventBus.getDefault().register(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMain) ;

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);


        adapter = new RVAdapter();
        recyclerView.setAdapter(adapter);

    }


    @Subscribe
    public void receiveEvent(ClusterListEvent clusterListEvent){

        fillListData(clusterListEvent.getMyClusterItemList()) ;
    }

    private void fillListData(List<MyClusterItem> myClusterItemList) {

        for (MyClusterItem myClusterItem: myClusterItemList) {
            myClusterItems.add(new RVData(myClusterItem.getUserId())) ;
        }

        adapter.setStrings(myClusterItems);


    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
