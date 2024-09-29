package com.example.solemates;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class AdapterGetCategoryWiseProduct extends BaseAdapter {
    List<POJOGetCategoryWiseProduct> pojoGetCategoryWiseProducts;
    Activity activity;

    public AdapterGetCategoryWiseProduct(List<POJOGetCategoryWiseProduct> pojoGetCategoryWiseProducts, Activity activity) {
        this.pojoGetCategoryWiseProducts = pojoGetCategoryWiseProducts;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
