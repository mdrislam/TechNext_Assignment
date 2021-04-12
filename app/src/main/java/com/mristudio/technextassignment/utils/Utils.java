package com.mristudio.technextassignment.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.hdodenhof.circleimageview.CircleImageView;

public class Utils {

    public static String getArratoString(List<String> arrayList){
        return arrayList.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    public static Integer getIndexByString(List<String> arrayList, String value){

        return arrayList.indexOf(value);
    }

    @BindingAdapter("android:loadImage")
    public  static void loadImage(ImageView imageView,String imUrl){
        Glide.with(imageView).load(imUrl).into(imageView);
    }
    @BindingAdapter("android:loadCircleImage")
    public  static void loadCircleImage(CircleImageView imageView, String imUrl){
        Glide.with(imageView).load(imUrl).into(imageView);
    }
}
