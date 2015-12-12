package com.dammi.dammi.activitylist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.dammi.dammi.R;
import com.dammi.dammi.Volley.VolleySingleton;
import com.dammi.dammi.activitydetails.DetailsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by trees on 12/1/15.
 */
public class ActivityListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private List<ActivityItem> data= Collections.emptyList();;
    private Context context;
    private LayoutInflater inflater;

    public ActivityListAdapter(Context context)
    {
        this.context=context;
        inflater=LayoutInflater.from(context);
    }

    public void updateList(List<ActivityItem> data){
        this.data=data;
        notifyItemRangeChanged(0, data.size());
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(inflater.inflate(R.layout.activity_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
    {

        final MyViewHolder holder=(MyViewHolder)viewHolder;
        ActivityItem item=data.get(position);

        holder.title.setText(item.title);
        holder.location.setText(item.location);
        holder.hostName.setText(item.hostName);
        holder.duration.setText(item.duration);
        holder.priceTag.setText(item.priceTag);

        ImageLoader loader= VolleySingleton.getInstance().getmImageLoader();

        loader.get(item.imageUrl, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.bgImage.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

                holder.bgImage.setImageResource(R.drawable.oops_loading);
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }


    class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView title, location, hostName, duration, priceTag;
        ImageView bgImage;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            bgImage=(ImageView)itemView.findViewById(R.id.item_image);
            title=(TextView)itemView.findViewById(R.id.item_title);
            location=(TextView)itemView.findViewById(R.id.activity_location);
            hostName=(TextView)itemView.findViewById(R.id.host_name);
            priceTag= (TextView) itemView.findViewById(R.id.price_tag);
            duration=(TextView)itemView.findViewById(R.id.duration);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Toast.makeText(context,"clicked",Toast.LENGTH_SHORT).show();
            ActivityItem item=data.get(getAdapterPosition());
            Intent intent=new Intent(context, DetailsActivity.class);
            Bundle bunlde=new Bundle();
            bunlde.putInt(DetailsActivity.ACT_ID_TAG, item.actId);
            bunlde.putString(DetailsActivity.TITLE, item.title);
            intent.putExtra(DetailsActivity.BUNLDE_TAG, bunlde);
            context.startActivity(intent);
        }
    }

}
