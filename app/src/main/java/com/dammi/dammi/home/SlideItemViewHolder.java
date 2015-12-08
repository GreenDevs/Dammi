package com.dammi.dammi.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.dammi.dammi.R;
import com.dammi.dammi.activitylist.ListActivity;
import com.dammi.dammi.search.SearchableActivity;

/**
 * Created by script on 11/25/15.
 */
public class SlideItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    SliderLayout sliderShow;
    private Context context;
    TextView textviewExperiences;
    public SlideItemViewHolder(View itemView)
    {
        super(itemView);
        sliderShow=(SliderLayout)itemView.findViewById(R.id.home_slider);
        textviewExperiences= (TextView) itemView.findViewById(R.id.slide_title);
        textviewExperiences.setOnClickListener(this);
        context=itemView.getContext();
        sliderShow.setOnClickListener(this);


    }

    public SliderLayout getSliderShow()
    {
        return sliderShow;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.home_slider:
                Toast.makeText(context,"mouse",Toast.LENGTH_SHORT).show();
        }

       // context.startActivity(new Intent(context, SearchableActivity.class));

    }
}
