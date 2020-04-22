package com.example.visionnewsb1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements IFireStoreLoadDone {

    ViewPager viewPager;
    NewsAdapter newsAdapter;
    IFireStoreLoadDone iFireStoreLoadDone;

    CollectionReference news;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iFireStoreLoadDone = this;
        news = FirebaseFirestore.getInstance().collection("news");

        dialog = new SpotsDialog.Builder().setContext(this).build();
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        getData();
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
}
