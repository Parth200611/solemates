package com.example.solemates;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.example.solemates.comman.urls;

import java.util.ArrayList;
import java.util.List;

public class AdapterClassforgetallcategory extends BaseAdapter {

    List<POJOclassforgetallcategory> pojOclassforgetallcategories;
    Activity activity;

    public AdapterClassforgetallcategory(List<POJOclassforgetallcategory> pojOclassforgetallcategories, Activity activity) {
        this.pojOclassforgetallcategories = pojOclassforgetallcategories;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pojOclassforgetallcategories.size();
    }

    @Override
    public Object getItem(int position) {
        return pojOclassforgetallcategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            holder = new ViewHolder();
            convertView=inflater.inflate(R.layout.lv_allcategory_desgin,null);
            holder.cvcategory=convertView.findViewById(R.id.cvlvcategorydesgincategorycard);
            holder.ivcategoryimage = convertView.findViewById(R.id.ivlvcategorydesgincategoryimage);
            holder.tvcategoryname=convertView.findViewById(R.id.tvlvcategorydesgincategoryname);
            holder.cvcategory = convertView.findViewById(R.id.cvlvcategorydesgincategorycard);

            convertView.setTag(holder);

        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        final POJOclassforgetallcategory obj = pojOclassforgetallcategories.get(position);
        holder.tvcategoryname.setText(obj.getCategoryname());


        Glide.with(activity)
                .load(urls.address+"images/"+obj.getCategoryimage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)
                .downsample(DownsampleStrategy.CENTER_INSIDE) // Scale down image to fit within specified bounds
                .override(800, 800) // Resize the image to 800x800 pixels
                .into(holder.ivcategoryimage);

        holder.cvcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity,GetcategorywiseproductActivity.class);
                i.putExtra("categoryname",obj.getCategoryname());
                activity.startActivity(i);
            }
        });






        return convertView;
    }

    class ViewHolder{
        CardView cvcategory;
        ImageView ivcategoryimage;
        TextView tvcategoryname;
    }
}
