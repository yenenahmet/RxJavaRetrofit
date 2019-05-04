package com.journaldev.rxjavaretrofit.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.journaldev.rxjavaretrofit.adapter.RecyclerViewAdapter;
import com.journaldev.rxjavaretrofit.base.BaseViewModel;
import com.journaldev.rxjavaretrofit.network.ApiClient;
import com.journaldev.rxjavaretrofit.network.CryptocurrencyService;
import com.journaldev.rxjavaretrofit.pojo.Crypto;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;

public class MainViewModel extends BaseViewModel {

    private RecyclerViewAdapter recyclerViewAdapter;
    private MutableLiveData<List<Crypto.Market>> cryptoMarkets;
    public final MutableLiveData<String> handleMessage = new MutableLiveData<>();
    private final CryptocurrencyService service;

    MainViewModel() {
        service = ApiClient.createService(CryptocurrencyService.class);
    }

    public void init() {
        recyclerViewAdapter = new RecyclerViewAdapter();
    }

    public LiveData<List<Crypto.Market>> getCrypto() {
        if (cryptoMarkets == null) {
            cryptoMarkets = new MutableLiveData<>();
            Log.e("Start Get Data Time =", String.valueOf(System.currentTimeMillis()));
            loadDataZip();
        }
        return cryptoMarkets;
    }

    // Merge ,  Gelen service çağrılarında bekleme yapmaz !
    private void loadDataMerge() {
        disposable.add(getObservableConfig(Observable.merge(getObservableBtc(), getObservableEth()))
                .subscribe(this::handleResults, this::handleError));
    }
    // Zip , Gelen service çağrıları bekletip ,birleştirip sonucu iletir !
    private void loadDataZip() {
        disposable.add(getObservableConfig(Observable.zip(getObservableBtc(), getObservableEth(), (s, s2) -> {
            final List<Crypto.Market> newList = new ArrayList<>();
            newList.addAll(s);
            newList.addAll(s2);
            return newList;
        })).subscribe(this::handleResults, this::handleError));
    }

    private Observable<List<Crypto.Market>> getObservableBtc() {
        return service.getCoinData("btc")
                .map(result -> Observable.fromIterable(result.ticker.markets))
                .flatMap(x -> x).filter(y -> {
                    y.coinName = "btc";
                    return true;
                }).toList().toObservable();
    }

    private Observable<List<Crypto.Market>> getObservableEth() {
        return service.getCoinData("eth")
                .map(result -> Observable.fromIterable(result.ticker.markets))
                .flatMap(x -> x).filter(y -> {
                    y.coinName = "eth";
                    return true;
                }).toList().toObservable();
    }

    public RecyclerViewAdapter getAdapter() {
        return recyclerViewAdapter;
    }

    private void handleResults(final List<Crypto.Market> crypto) {
        if (crypto != null && crypto.size() > 0) {
            Log.e("Handler =", crypto.get(0).coinName + String.valueOf(crypto.size()));
            Log.e("End Get Data Time =", String.valueOf(System.currentTimeMillis()));
            cryptoMarkets.setValue(crypto);
            handleMessage.setValue("Success");
        } else {
            handleMessage.setValue("NO RESULTS FOUND");
        }
    }

    private void handleError(Throwable t) {
        handleMessage.setValue(ApiClient.failMessage(t));
    }

}
