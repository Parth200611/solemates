package com.example.solemates;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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


public class CategoryFragment extends Fragment {


    ListView lvshoescategory;
    TextView tvnocategorypresent;
   // SearchView svserachcategory;
    List<POJOclassforgetallcategory> pojOclassforgetallcategories;
    AdapterClassforgetallcategory adapterClassforgetallcategory;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_category, container, false);


        lvshoescategory=view.findViewById(R.id.lvUsercategoryFragmentshoescategory);
        tvnocategorypresent=view.findViewById(R.id.tvUsercategoryFragmentTextnocategory);
       // svserachcategory = view.findViewById(R.id.svcategoryfragmentsearchView);
        pojOclassforgetallcategories=new ArrayList<>();


       // svserachcategory.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        //    @Override
         //   public boolean onQueryTextSubmit(String query) {
        //        searchquery(query);
          //      return false;
         //   }

          //  @Override
          //  public boolean onQueryTextChange(String query) {
            //    searchquery(query);
          //      return false;
          //  }
        //});

        getAllcategory();


        return view;
    }

    private void searchquery(String query) {
        List<POJOclassforgetallcategory> temperoeycategory = new ArrayList<>();
        temperoeycategory.clear();

        for (POJOclassforgetallcategory obj :pojOclassforgetallcategories) {

            if (obj.getCategoryname().toUpperCase().contains(query.toUpperCase())){

                temperoeycategory.add(obj);

            }else {

                tvnocategorypresent.setVisibility(View.VISIBLE);
            }
            adapterClassforgetallcategory = new AdapterClassforgetallcategory(temperoeycategory,getActivity());
            lvshoescategory.setAdapter(adapterClassforgetallcategory);

        }

    }

    private void getAllcategory() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(urls.getcategory,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("getallcategory");
                    if (jsonArray.isNull(0)){
                        tvnocategorypresent.setVisibility(View.VISIBLE);
                    }
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String strid=jsonObject.getString("id");
                        String strcategoryimage = jsonObject.getString("categoryimage");
                        String strcategoryname = jsonObject.getString("categoryname");

                        pojOclassforgetallcategories.add(new POJOclassforgetallcategory(strid,strcategoryimage,strcategoryname));
                    }
                    adapterClassforgetallcategory=new AdapterClassforgetallcategory(pojOclassforgetallcategories,getActivity());
                    lvshoescategory.setAdapter(adapterClassforgetallcategory);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}