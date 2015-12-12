package com.dammi.dammi.hosts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.dammi.dammi.R;
import com.dammi.dammi.Volley.VolleySingleton;
import com.dammi.dammi.activitydetails.DetailsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by script on 12/9/15.
 */
public class HostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<HostItem> data= Collections.emptyList();
    private Context context;
    private LayoutInflater inflater;

   public HostAdapter(Context context)
   {
       this.context=context;
       inflater=LayoutInflater.from(context);
//       data=new ArrayList<>();
//
//       data.add(new HostItem(R.drawable.higenic,"1.5","Social Tours",5,10,R.drawable.australia));
//       data.add(new HostItem(R.drawable.geothermal,"3.5","Essor Kafley",5,10,R.drawable.colombo));
//       data.add(new HostItem(R.drawable.colombo,"4.5","Roshan Shrestha",5,10,R.drawable.geothermal));
//       data.add(new HostItem(R.drawable.australia,"5","Balman Rawat",5,10,R.drawable.burren));
   }


    public void updateAdapter(List<HostItem> data)
    {
        this.data=data;
        notifyItemRangeChanged(0, data.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyViewHolder(inflater.inflate(R.layout.host_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
    {
        final MyViewHolder holder=(MyViewHolder)viewHolder;
        HostItem item=data.get(position);


        holder.about.setText(""+item.about);
        holder.rating.setText(item.rating+" ");
        holder.noOfActivities.setText(item.noOfActivities+" ");
        holder.noOfEvents.setText(""+item.noOfEvents+" ");

//        holder.image.setImageResource(item.image);
//        holder.backImage.setBackgroundResource(item.bgImage);

        ImageLoader imageLoader = VolleySingleton.getInstance().getmImageLoader();
        imageLoader.get(item.image, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.image.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                holder.image.setImageResource(R.drawable.oops_loading);
            }
        });


        imageLoader.get(item.image, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.image.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                holder.image.setImageResource(R.drawable.oops_loading);
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
        ImageView image;
        View backImage;
        TextView rating,about,noOfActivities,noOfEvents;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            rating= (TextView) itemView.findViewById(R.id.rating_text);
            about= (TextView) itemView.findViewById(R.id.host_name);
            noOfActivities= (TextView) itemView.findViewById(R.id.activity_count);
            noOfEvents= (TextView) itemView.findViewById(R.id.events_count);
            image= (ImageView) itemView.findViewById(R.id.imgProfile);
            backImage=itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
//            context.startActivity(new Intent(context, DetailsActivity.class));
        }




    }
}
