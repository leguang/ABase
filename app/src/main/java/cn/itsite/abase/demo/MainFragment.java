package cn.itsite.abase.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import cn.itsite.abase.mvvm.view.base.BaseFragment;

public class MainFragment extends BaseFragment<MainViewModel> {
    private static final String TAG = "MainFragment";
    private TextView tv;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @NonNull
    @Override
    protected MainViewModel onCreateViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

//    @Override
//    protected Class<MainViewModel> onBindViewModel() {
//        return MainViewModel.class;
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
