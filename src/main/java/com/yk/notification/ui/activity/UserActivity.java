package com.yk.notification.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yk.notification.R;
import com.yk.notification.base.BaseActivity;
import com.yk.notification.http.httputil.HttpUtils;
import com.yk.notification.http.interfaceable.APIInterface;
import com.yk.notification.model.WeatherModel;
import com.yk.notification.util.Constants;
import com.yk.notification.util.ToastUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Silence on 2017/4/8.
 */

public class UserActivity extends BaseActivity {

    TextView mTxtUser;
    Button mBtnGet;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_user);

    }

    @Override
    public void initView() {
        mTxtUser = $(R.id.txt_user);
        mBtnGet = $(R.id.btn_get);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get: {
                getWeather();
            }
            break;
        }

    }

    @Override
    public void setListener() {
        mBtnGet.setOnClickListener(this);
    }

    /**
     * Get请求
     */
    public void getWeather() {
        APIInterface server = HttpUtils.getRetrofit(Constants.WEATHER_URL).create(APIInterface.class);
        Call<WeatherModel> call = server.getWeather("101010100");
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                Log.e("WeatherJson", response.body().getWeatherinfo().getCity());
                ToastUtils.showMessage(UserActivity.this,response.body().getWeatherinfo().getCity());
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {

            }
        });

    }
}
