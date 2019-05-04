package com.journaldev.rxjavaretrofit.network;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.net.UnknownHostException;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    // Singleton Design Pattern //
    public static <S> S createService(final Class<S> classs) {
        if (retrofit == null) {
            synchronized (ApiClient.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.cryptonator.com/api/full/")
                            .client(getClient())
                            .addConverterFactory(GsonConverterFactory.create(getGson()))
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit.create(classs);
    }

    private static OkHttpClient getClient(){
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    private static Gson getGson(){
        return  new GsonBuilder()
                .setLenient()
                .create();
    }

    // Error Handler Message // gelen hata mesajına göre handler edilip geriye ona göre mesaj yollanabilir !
    public static String failMessage(final Throwable t) {
        if(t!=null){
            Log.e("ApiClientFailMessage", t.toString());
            if(t instanceof UnknownHostException || t instanceof HttpException || t instanceof IOException){
                return "İnternet Bağlantınızda Sorun Var ! Bağlantı Kurulamadı ";
            }else if(t instanceof JsonSyntaxException){
                return "Yapısal Bir Sorun Oluştu";
            }else{
                return "Veriler alınırken hata oluştu ! ";
            }
        }
        return "";
    }
}
