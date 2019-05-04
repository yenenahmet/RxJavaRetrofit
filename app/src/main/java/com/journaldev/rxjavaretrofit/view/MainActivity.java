package com.journaldev.rxjavaretrofit.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.journaldev.rxjavaretrofit.R;
import com.journaldev.rxjavaretrofit.databinding.ActivityMainBinding;
import com.journaldev.rxjavaretrofit.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Bitcoin");
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        final MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        viewModel.setViewDataBinding(binding);
        binding.setMainViewModel(viewModel);
        binding.setLifecycleOwner(this);
        observeData(viewModel);
    }

    private void observeData(final MainViewModel viewModel) {
        viewModel.getCrypto().observe(this, markets -> {
            if(markets!=null){
                viewModel.getAdapter().addItems(markets);
            }
        });
    }

}
