package com.example.visionnewsb1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.visionnewsb1.Modal.News;
import com.example.visionnewsb1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends PagerAdapter {

    Context context;
    List<News> newsList;
    LayoutInflater inflater;

    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager)container).removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.view_pager_item,container,false);
        ImageView news_image = (ImageView)view.findViewById(R.id.news_image);
        TextView news_heading = (TextView)view.findViewById(R.id.news_heading);
        TextView news_description = (TextView)view.findViewById(R.id.news_description);
        FloatingActionButton btn_fav = (FloatingActionButton)view.findViewById(R.id.btn_fav);
        btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Liked",Toast.LENGTH_SHORT).show();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Clicked NEws",Toast.LENGTH_SHORT).show();
            }
        });
        Picasso.get().load(newsList.get(position).getImage()).into(news_image);
        news_heading.setText(newsList.get(position).getHeading());
        news_description.setText(newsList.get(position).getDescription());

        container.addView(view);
        return view;
    }
}
