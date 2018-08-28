package edm.music.t3v.network.service;

import java.util.concurrent.TimeUnit;

import edm.music.t3v.util.Constant;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vuong_IT on 19/11/2017.
 */

public class RetrofitClient {


    public static <S> S createService(Class<S> serviceClass) {

        return getRetrofit().create(serviceClass);
    }

    private static Retrofit getRetrofit(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        return builder.client(httpClientBuilder.build()).build();
    }


    public static ApiService getApiService(){
        return getRetrofit().create(ApiService.class);
    }

//    private static Retrofit getRetrofitSearch(){
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
//        httpClientBuilder.readTimeout(20, TimeUnit.SECONDS)
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .addInterceptor(logging);
//
//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_SEARCH)
//                .addConverterFactory(GsonConverterFactory.create());
//
//        return builder.client(httpClientBuilder.build()).build();
//    }

//    public static ApiService getSearchService(){
//        return getRetrofitSearch().create(ApiService.class);
//    }

}
