package com.example.mytradingapp.View.News;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mytradingapp.Adapter.NewsAdapter;
import com.example.mytradingapp.Adapter.OnListItemClickListener;
import com.example.mytradingapp.Adapter.StockTitleAdapter;
import com.example.mytradingapp.R;
import com.example.mytradingapp.Shared.Transferobjects.News;
import com.example.mytradingapp.Shared.Transferobjects.Stock;
import com.example.mytradingapp.View.Market.TopActiveViewModel;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment implements OnListItemClickListener {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<News> newsArrayList = new ArrayList<>();
    private NewsViewModel newsViewModel;
    private NewsAdapter newsAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_news, container, false);
        // Inflate the layout for this fragment

        recyclerView = inflate.findViewById(R.id.rvNews_list);
        progressBar = inflate.findViewById(R.id.newsprogress_bar);

        recyclerView.setLayoutManager(new LinearLayoutManager(inflate.getContext()));
        recyclerView.hasFixedSize();

        newsAdapter = new NewsAdapter(newsArrayList,this,inflate.getContext());

        recyclerView.setAdapter(newsAdapter);

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);


        getStockNews();


        return inflate;
    }


    private void getStockNews() {

        newsViewModel.getStockNewsLiveData().observe(getViewLifecycleOwner(), newsResponse -> {
            if (newsResponse != null && !newsResponse.isEmpty()){

                progressBar.setVisibility(View.GONE);
                List<News> stocknews = newsResponse;
                newsArrayList.clear();
                newsArrayList.addAll(stocknews);

            }
        });

    }

    @Override
    public void onClick(int position) {

        Toast.makeText(getContext(), "Position: " + position, Toast.LENGTH_SHORT).show();

        Uri webpage = Uri.parse(newsArrayList.get(position).getUrl());
        Intent webIntent = new Intent(Intent.ACTION_VIEW,webpage);

        startActivity(webIntent);

    }

}