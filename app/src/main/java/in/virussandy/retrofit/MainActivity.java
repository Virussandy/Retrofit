package in.virussandy.retrofit;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    private String BASE_URL = "https://dummyjson.com";
    interface RequestUser{
        @POST("/auth/login")
        Call<ResponsePost> postUser(@Body RequestPost post);

        @GET("/products/search")
        Call<ResponseGet> getproducts(@QueryMap Map<String,String> parms);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestUser requestUser = retrofit.create(RequestUser.class);

        Map<String,String> data = new HashMap<>();
        data.put("q","phone");

        requestUser.getproducts(data).enqueue(new Callback<ResponseGet>() {
            @Override
            public void onResponse(Call<ResponseGet> call, Response<ResponseGet> response) {
//                textView.setText(response.body().getLimit());
                List<ResponseGet.Products> data = response.body().getProducts();
                for (ResponseGet.Products object : data) {
                    Log.d(TAG, "onResponse: "+object.getId());
                }
            }

            @Override
            public void onFailure(Call<ResponseGet> call, Throwable t) {
                //Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
//        requestUser.postUser(new RequestPost("kminchelle", "0lelplR")).enqueue(new Callback<ResponsePost>() {
//            @Override
//            public void onResponse(Call<ResponsePost> call, Response<ResponsePost> response) {
//                Log.d(TAG, "onResponse: "+response.body().firstName);
//            }
//
//            @Override
//            public void onFailure(Call<ResponsePost> call, Throwable t) {
//                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}