package com.dammi.dammi.help;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dammi.dammi.R;
import com.rubengees.introduction.IntroductionBuilder;
import com.rubengees.introduction.entity.Slide;
import com.rubengees.introduction.style.FullscreenStyle;

import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

            init();


    }

    private void init()
    {
        //new IntroductionBuilder(this); //this is the Activity you want to start from.
//        new IntroductionBuilder(this).withSlides(generateSlides());
        new IntroductionBuilder(this).withSlides(generateSlides())
                .withStyle(new FullscreenStyle()).introduceMyself();
    }


    private List<Slide> generateSlides() {
        List<Slide> result = new ArrayList<>();

        result.add(new Slide().withTitle("Some title").withDescription("Some description").
                withColorResource(R.color.colorAccent).withImage(R.drawable.higenic));
        result.add(new Slide().withTitle("Another title").withDescription("Another description")
                .withColorResource(R.color.colorAccent).withImage(R.drawable.australia));

        return result;
    }
}
