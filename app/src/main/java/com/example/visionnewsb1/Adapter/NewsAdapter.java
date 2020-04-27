package com.example.visionnewsb1.Adapter;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.visionnewsb1.MainActivity;
import com.example.visionnewsb1.Modal.News;
import com.example.visionnewsb1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends PagerAdapter implements GestureDetector.OnGestureListener {

    Context context;
    List<News> newsList;
    LayoutInflater inflater;
    View v;
    private GestureDetector gestureDetector;


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
        v = view;
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
        gestureDetector = new GestureDetector(this);

        Picasso.get().load(newsList.get(position).getImage()).into(news_image);
        news_heading.setText(newsList.get(position).getHeading());
        news_description.setText(newsList.get(position).getDescription());


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
        container.addView(view);
        return view;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
        boolean result = false;
        float diffY = moveEvent.getY() - downEvent.getY();

        if(Math.abs(diffY)>50 && Math.abs(velocityX)>50)
        {
            if(diffY>0)
            {
                onSwipeBottom();
            }
            else
            {
                onSwipeTop();
            }
            result = true;
        }
        return result;
    }
    private void onSwipeTop() {
        System.out.println("Swiped Up");
        //Toast.makeText(this.get,"Swiped Up",Toast.LENGTH_SHORT).show();
        Toast.makeText(v.getContext(),"Swiped Up",Toast.LENGTH_SHORT).show();
    }

    private void onSwipeBottom() {
        System.out.println("Swiped Down");
        Toast.makeText(v.getContext(),"Swiped Down",Toast.LENGTH_SHORT).show();

    }

}
