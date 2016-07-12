package info.boubakr.capitolagent10.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aboubakr on 03/07/16.
 */
public class DiscussionListAdapter extends BaseAdapter {
    private ArrayList<String> reps;
    private Context context;
    private LayoutInflater inflater;
    public DiscussionListAdapter(ArrayList reps, Context context){
        this.reps = reps;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return reps.size();
    }

    @Override
    public Object getItem(int position) {
        return reps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
