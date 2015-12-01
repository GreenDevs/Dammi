package com.dammi.dammi.activitylist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dammi.dammi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trees on 12/1/15.
 */
public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private List<ListItem> data;
    private Context context;
    private LayoutInflater inflater;

    public ListAdapter(Context context)
    {
        this.context=context;
        inflater=LayoutInflater.from(context);
        data=new ArrayList<>();
        data.add(new ListItem("COLUMBIA", R.drawable.colombo));
        data.add(new ListItem("GEOTHERMAL", R.drawable.geothermal));
        data.add(new ListItem("BARREN", R.drawable.burren));
        data.add(new ListItem("HYGENIC FOOD", R.drawable.higenic));
        data.add(new ListItem("NEPALI FOOD", R.drawable.nepali));

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(inflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
    {
        MyViewHolder holder=(MyViewHolder)viewHolder;
        ListItem item=data.get(position);

        holder.image.setImageResource(item.image);
        holder.title.setText(item.title);


    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }


    class MyViewHolder extends  RecyclerView.ViewHolder
    {

        ImageView image;
        TextView title;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.item_image);
            title=(TextView)itemView.findViewById(R.id.item_title);
        }
    }
}
