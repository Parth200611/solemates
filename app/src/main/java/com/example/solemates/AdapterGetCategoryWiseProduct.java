package com.example.solemates;

import android.app.Activity;
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
        return pojoGetCategoryWiseProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return pojoGetCategoryWiseProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final  Viewholder viewholder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            viewholder = new Viewholder();
            convertView = inflater.inflate(R.layout.lv_categorywise_productdesgin,null);
            viewholder.cvprosuctcart=convertView.findViewById(R.id.cvlvCtaegorywiseproductcardview);
            viewholder.ivproductimage=convertView.findViewById(R.id.ivlvGetcategorywiseproductimage);
            viewholder.tvshoesname=convertView.findViewById(R.id.tvlvGetcategorywiseproductshoesname);
            viewholder.tvshopname=convertView.findViewById(R.id.tvlvGetcategorywiseproductshopname);
            viewholder.tvcategoryname = convertView.findViewById(R.id.tvlvGetcategorywiseproductcategoryname);
            viewholder.tvrating=convertView.findViewById(R.id.tvvlvGetcategorywiseproductrating);
            viewholder.tvshoeprice=convertView.findViewById(R.id.tvlvGetcategorywiseproductprice);
            viewholder.tvdiscription = convertView.findViewById(R.id.tvlvGetcategorywiseproductdiscription);
            viewholder.tvoffer=convertView.findViewById(R.id.tvlvproductoffer);

            convertView.setTag(viewholder);
        }else{
            viewholder = (Viewholder) convertView.getTag();
        }
       final POJOGetCategoryWiseProduct obj=pojoGetCategoryWiseProducts.get(position);
        viewholder.tvshoesname.setText(obj.getShoesname());
        viewholder.tvshopname.setText(obj.getShopname());
        viewholder.tvrating.setText(obj.getRating());
        viewholder.tvshoeprice.setText(obj.getProductprice());
        viewholder.tvdiscription.setText(obj.getProductdiscription());
        viewholder.tvcategoryname.setText(obj.getCategoryname());
        viewholder.tvoffer.setText(obj.getProductoffer());
        Glide.with(activity)
                .load(urls.address+"images/"+obj.getProductimage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)
                .downsample(DownsampleStrategy.CENTER_INSIDE) // Scale down image to fit within specified bounds
                .override(800, 800) // Resize the image to 800x800 pixels
                .into(viewholder.ivproductimage);

        return convertView;
    }


    class Viewholder{
        CardView cvprosuctcart;
        ImageView ivproductimage;
        TextView tvshoesname,tvshopname,tvrating,tvshoeprice,tvcategoryname,tvdiscription,tvoffer;
    }
}
