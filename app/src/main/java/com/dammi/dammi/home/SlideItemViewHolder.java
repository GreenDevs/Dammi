package com.dammi.dammi.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.daimajia.slider.library.SliderLayout;
import com.dammi.dammi.R;

/**
 * Created by script on 11/25/15.
 */
public class SlideItemViewHolder extends RecyclerView.ViewHolder
{
    SliderLayout sliderShow;
    public SlideItemViewHolder(View itemView)
    {
        super(itemView);
        sliderShow=(SliderLayout)itemView.findViewById(R.id.home_slider);
    }

    public SliderLayout getSliderShow()
    {
        return sliderShow;
    }
}
