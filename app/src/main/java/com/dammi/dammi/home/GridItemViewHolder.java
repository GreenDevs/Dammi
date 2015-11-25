package com.dammi.dammi.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dammi.dammi.R;

/**
 * Created by script on 11/25/15.
 */
public class GridItemViewHolder extends RecyclerView.ViewHolder
{
    ImageView image;
    TextView title;
    public GridItemViewHolder(View itemView)
    {
        super(itemView);
        image=(ImageView)itemView.findViewById(R.id.item_image);
        title=(TextView)itemView.findViewById(R.id.item_title);
    }
}
