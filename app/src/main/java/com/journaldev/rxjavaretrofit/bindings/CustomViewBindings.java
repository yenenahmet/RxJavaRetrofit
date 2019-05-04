package com.journaldev.rxjavaretrofit.bindings;

import android.annotation.SuppressLint;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class CustomViewBindings {


    @BindingAdapter("setAdapter")
    public static void bindRecyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @BindingAdapter("textPrice")
    public static void setTextPrice(TextView view ,String price){
        view.setText("$" + String.format("%.2f", Double.parseDouble(price)));
    }

    @BindingAdapter("cardViewBackground")
    public static void setCardViewBackground(CardView view,String coinName){
        if (coinName.equalsIgnoreCase("eth")) {
           view.setCardBackgroundColor(Color.GRAY);
        } else {
            view.setCardBackgroundColor(Color.GREEN);
        }
    }
}
