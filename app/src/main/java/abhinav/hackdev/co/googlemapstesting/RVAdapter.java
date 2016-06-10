package abhinav.hackdev.co.googlemapstesting;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.SelfViewHolder>{

    private static final String TAG = "TAG";
    private List<RVData> rvDataList = new ArrayList<>();

    /*public RVAdapter(List<RVData> rvDataList) {
        this.rvDataList = rvDataList;
    }*/

    @Override
    public SelfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cluster_list_item, parent, false);
        SelfViewHolder pvh = new SelfViewHolder(v);
        return pvh;
    }

    public void setStrings(List<RVData> newStrings) {
        rvDataList.clear();
        rvDataList.addAll(newStrings);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(SelfViewHolder holder, int position) {
        holder.textView.setText(rvDataList.get(position).getSampleData());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return rvDataList.size();
    }

    public static class SelfViewHolder extends RecyclerView.ViewHolder{

        public TextView textView ;

        public SelfViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.username_list) ;
        }


    }

}
