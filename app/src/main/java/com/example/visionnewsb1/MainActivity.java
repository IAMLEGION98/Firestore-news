package com.example.visionnewsb1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.visionnewsb1.Adapter.NewsAdapter;
import com.example.visionnewsb1.Interface.IFireStoreLoadDone;
import com.example.visionnewsb1.Modal.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity implements IFireStoreLoadDone, GestureDetector.OnGestureListener {


    public static final int VELOCITY_THRESHOLD = 100;
    public static final int SWIPE_THRESHOLD = 100;
    ViewPager viewPager;
    NewsAdapter newsAdapter;
    IFireStoreLoadDone iFireStoreLoadDone;

    CollectionReference news;
    AlertDialog dialog;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iFireStoreLoadDone = this;
        news = FirebaseFirestore.getInstance().collection("news");

        dialog = new SpotsDialog.Builder().setContext(this).build();
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        getData();


        gestureDetector = new GestureDetector(this);


    }

    private void getData() {
         dialog.show();
        news.get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iFireStoreLoadDone.onFireSoreLoadFailed(e.getMessage());
            }
        })
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            List<News> newslist = new ArrayList<>();
                            for(QueryDocumentSnapshot newssnapshot : task.getResult())
                            {
                                News news = newssnapshot.toObject(News.class);
                                newslist.add(news);

                            }
                            iFireStoreLoadDone.onFireStoreLoadSuccess(newslist);
                        }
                    }
                });
    }

    @Override
    public void onFireStoreLoadSuccess(List<News> news) {
       newsAdapter = new NewsAdapter(this,news);
       viewPager.setAdapter(newsAdapter);
       dialog.dismiss();
    }

    @Override
    public void onFireSoreLoadFailed(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        dialog.dismiss();

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
        System.out.println("Gone here");
        float diffY = moveEvent.getY() - downEvent.getY();

        if(Math.abs(diffY)>SWIPE_THRESHOLD && Math.abs(velocityX)>VELOCITY_THRESHOLD)
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
        Toast.makeText(getApplicationContext(),"Swiped Up",Toast.LENGTH_SHORT).show();
    }

    private void onSwipeBottom() {
        System.out.println("Swiped Down");
        Toast.makeText(getApplicationContext(),"Swiped Down",Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
