package android.workshop.dmii.playlistspotifygenerator.helpers;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.workshop.dmii.playlistspotifygenerator.R;
import android.workshop.dmii.playlistspotifygenerator.models.Artist;
import android.workshop.dmii.playlistspotifygenerator.models.Music;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<?> listProperties;

    public CustomAdapter(Context context, ArrayList<?> listProperties){
        this.context = context;
        this.listProperties = listProperties;
    }


    @Override
    public int getCount() {
        if(listProperties != null){
            return listProperties.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return listProperties.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        TextView textview;

        Object property = listProperties.get(position);
        textview = new TextView(context);
        textview.setTextColor(ContextCompat.getColor(context, R.color.colorDark));
        textview.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
        textview.setGravity(Gravity.CENTER);

        if(property instanceof Music){
            textview.setText(((Music) property).getName());
        }else if(property instanceof Artist){
            textview.setText(((Artist) property).getName());
        }
        return textview;
    }
}