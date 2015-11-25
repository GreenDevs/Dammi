package com.dammi.dammi.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.dammi.dammi.R;

import java.util.ArrayList;

/**
 * Created by script on 11/25/15.
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private ArrayList<Object> data;
    private Context context;
    private final int SLIDE_SHOW=0, GRID_ITEMS=1;
    private LayoutInflater inflater;
    private SlideItemViewHolder slideHolder;
    public HomeAdapter(Context context)
    {
        this.context=context;
        inflater=LayoutInflater.from(context);
        data=new ArrayList<>();

        data.add("SLIDE SHOW HERE");
        data.add(new GridItem("DAMMI TOURS", R.drawable.nepali));
        data.add(new GridItem("DAMMI TOURS", R.drawable.nepali));
        data.add(new GridItem("DAMMI TOURS", R.drawable.nepali));
        data.add(new GridItem("DAMMI TOURS", R.drawable.nepali));
        data.add(new GridItem("DAMMI TOURS", R.drawable.nepali));
        data.add(new GridItem("DAMMI TOURS", R.drawable.nepali));
        data.add(new GridItem("DAMMI TOURS", R.drawable.nepali));
        data.add(new GridItem("DAMMI TOURS", R.drawable.nepali));


    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        switch (viewType)
        {
            case SLIDE_SHOW:
                return new SlideItemViewHolder(inflater.inflate(R.layout.slide_show, parent, false));


            case GRID_ITEMS:
                return new GridItemViewHolder(inflater.inflate(R.layout.grid_item, parent, false));


            default:
                return new GridItemViewHolder(inflater.inflate(R.layout.grid_item, parent, false));

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
    {
         int type=viewHolder.getItemViewType();

         switch (type)
         {
             case SLIDE_SHOW:

                 slideHolder=(SlideItemViewHolder)viewHolder;
                 TextSliderView textSliderView = new TextSliderView(context);

                 textSliderView
                         .description("Daami last DAMMI!!!")
                         .image(R.drawable.nepali);

                 slideHolder.sliderShow.addSlider(textSliderView);

                 break;

             case GRID_ITEMS:

                 GridItem item=(GridItem)data.get(position);
                 GridItemViewHolder holder=(GridItemViewHolder)viewHolder;
                 holder.image.setImageResource(item.image);
                 holder.title.setText(item.title);
                 break;

             default:
                 break;

         }
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        Object item=data.get(position);

        if(item instanceof String)
        {
            return SLIDE_SHOW;
        }
        else if(item instanceof GridItem)
        {
            return GRID_ITEMS;
        }
        else
        {
            return GRID_ITEMS;
        }
    }

    public SliderLayout getSliderLayout()
    {
        return slideHolder.sliderShow;
    }
}
