package com.example.visionnewsb1.Interface;

import com.example.visionnewsb1.Modal.News;

import java.util.List;

public interface IFireStoreLoadDone {
    void onFireStoreLoadSuccess(List<News> news);
    void onFireSoreLoadFailed(String message);
}
