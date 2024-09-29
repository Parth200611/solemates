package com.example.solemates;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.solemates.comman.urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class GetcategorywiseproductActivity extends AppCompatActivity {

    SearchView searchView;
    ListView lvcategoryproductlist;
    TextView tvnocategory;
    String strgetcategoryname;
    List<POJOGetCategoryWiseProduct> pojoGetCategoryWiseProducts;
    AdapterGetCategoryWiseProduct adapterGetCategoryWiseProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_getcategorywiseproduct);
        getWindow().setStatusBarColor(ContextCompat.getColor(GetcategorywiseproductActivity.this,R.color.nevyblue));

        searchView =findViewById(R.id.svGetcategoryfragmentsearchView);
        lvcategoryproductlist = findViewById(R.id.lvGetcategorywiseproductlist);
        tvnocategory =findViewById(R.id.tvGetcategorywiseTextnocategory);
        pojoGetCategoryWiseProducts = new ArrayList<>();

        strgetcategoryname=getIntent().getStringExtra("categoryname");

        getcategoryWiseProduct();

    }

    private void getcategoryWiseProduct() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("categoryname",strgetcategoryname);


        client.post(urls.getcategorywiseproduct,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getallcategorywiseproduct");
                    if (jsonArray.isNull(0)){
                        tvnocategory.setVisibility(View.VISIBLE);
                    }
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String strid = jsonObject.getString("id");
                        String strcategoryname = jsonObject.getString("categoryname");
                        String stproductimage = jsonObject.getString("productimage");
                        String strshopname = jsonObject.getString("shopname");
                        String strating = jsonObject.getString("rating");
                        String strshoesname = jsonObject.getString("shoesname");
                        String strproductdiscription = jsonObject.getString("productdiscription");
                        String strproductprice = jsonObject.getString("productprice");
                        String strproductoffer = jsonObject.getString("productoffer");
                        pojoGetCategoryWiseProducts.add(new POJOGetCategoryWiseProduct(strid,strcategoryname,stproductimage,strshopname,strating,strshoesname,
                                strproductdiscription,strproductprice,strproductoffer));

                    }

                    adapterGetCategoryWiseProduct = new AdapterGetCategoryWiseProduct(pojoGetCategoryWiseProducts,GetcategorywiseproductActivity.this);
                    lvcategoryproductlist.setAdapter(adapterGetCategoryWiseProduct);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(GetcategorywiseproductActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}