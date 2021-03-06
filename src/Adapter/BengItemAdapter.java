package Adapter;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import com.example.webeng.BengSubmitDiagActivity;
import com.example.webeng.MainActivity;
import com.example.webeng.R;
import com.example.webeng.ViewImages;

import com.example.webeng.ViewResultActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import BaseClasses.BaseActivity;
import database.BengRegisteredHelper;
import models.BengModelItem;

import Fonts.FontManager;
import models.WeBengConstant;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import org.w3c.dom.Text;

public class BengItemAdapter extends BaseAdapter {
    private FontManager font = new FontManager();
    private LayoutInflater mInflater;
    private Context mcontext;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private List<BengModelItem> mitems;
    private static final String PHOTOS = "photos";
    DisplayImageOptions options;
    private final ImageLoader imageLoader = ImageLoader.getInstance();
    private BaseActivity _activity;
    private String[] bengRegistered;
    public BengItemAdapter(Context context, List<BengModelItem> items) {
        //_activity=activity;
        mInflater = LayoutInflater.from(context);
        mcontext = context;
        mitems = items;
        options = new DisplayImageOptions.Builder()

                //.showImageForEmptyUri(R.drawable.no_image)
                .showImageOnFail(R.drawable.no_image).cacheInMemory(true)
                .cacheOnDisc(true)
                //.considerExifParams(true)
                //.displayer(new RoundedBitmapDisplayer(5))
                .imageScaleType(ImageScaleType.EXACTLY)
                .resetViewBeforeLoading(true)
                .build();
        bengRegistered=new BengRegisteredHelper(context).getBengs();
    }
    public void updateBengRegistered(){
        bengRegistered=new BengRegisteredHelper(mcontext).getBengs();
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mitems.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        imageLoader.init(ImageLoaderConfiguration.createDefault(mcontext));
        final BengItem holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.bengitemlayout, null);
            holder = new BengItem();
            holder.setProgress((ProgressBar) convertView
                    .findViewById(R.id.loading));
            holder.setName((TextView) convertView.findViewById(R.id.NameItem));

            holder.setDeadline((TextView) convertView
                    .findViewById(R.id.Deadline));

            holder.setImage((ImageView) convertView.findViewById(R.id.Image));

            holder.setBeng((TextView) convertView.findViewById(R.id.BengButton));

            holder.setViewResult((TextView) convertView
                    .findViewById(R.id.BtnViewResult));

            holder.getImage().setScaleType(ScaleType.FIT_XY);
            convertView.setTag(holder);
        } else {
            holder = (BengItem) convertView.getTag();
        }
        Date date = new Date();
        DateFormat fm = DateFormat.getDateTimeInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US);
        // TimeZone timezone = TimeZone.getTimeZone("GMT");

        // dateFormat.setTimeZone(timezone);
        Date convertedDate = null;
        ParsePosition parseErr = new ParsePosition(0);
        convertedDate = dateFormat.parse(mitems.get(position).getDeadline()
                .trim(), parseErr);
        dateFormat.applyPattern("HH:mm:ss dd-MM-yyyy");
        if (parseErr.getErrorIndex() < 0)
            holder.getDeadline().setText(dateFormat.format(convertedDate));

        TextView txtComment=(TextView)convertView.findViewById(R.id.txtComment);
        txtComment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test","call click on comment");
            }
        });
        holder.getName().setText(mitems.get(position).getDescription());
        // holder.getName().setTypeface(font.getInstance().mMedium);
        final int pos = position;
        holder.getImage().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mcontext, ViewImages.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(PHOTOS, mitems.get(pos).getPhotos());
                mcontext.startActivity(intent);
                // startActivity(intent);
            }
        });

        imageLoader.displayImage(this.mitems.get(position).getThump(),
                holder.getImage(), options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                holder.getProgress().setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view,
                                        FailReason failReason) {
                String message = null;
                switch (failReason.getType()) {
                    case IO_ERROR:
                        message = "Input/Output error";
                        break;
                    case DECODING_ERROR:
                        message = "Image can't be decoded";
                        break;
                    case NETWORK_DENIED:
                        //message = "Downloads are denied";
                        break;
                    case OUT_OF_MEMORY:
                        message = "Out Of Memory error";
                        break;
                    case UNKNOWN:
                        message = "Unknown error";
                        break;
                }
                Toast.makeText(mcontext, message,
                        Toast.LENGTH_SHORT).show();

                holder.getProgress().setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view,
                                          Bitmap loadedImage) {
                holder.getProgress().setVisibility(View.GONE);
            }
        });
        // imgLoader.DisplayImage(mitems.get(position).getPhotos()[0],
        // holder.getImage());
        // holder.getDeadline().setTypeface(font.mBold);
        // holder.getImage().setImageResource(R.drawable.koala);

        if (!mitems.get(position).getWinner()) {
            holder.getBeng().setVisibility(View.VISIBLE);
            // holder.getBeng().setTypeface(font.mBold);
            holder.getDeadline().setTextColor(Color.parseColor("#e4511d"));
            if(containBeng(mitems.get(position).getId())){
                holder.getBeng().setEnabled(false);
                holder.getBeng().setBackground(mcontext.getResources().getDrawable(R.drawable.red_benged_gradient_around));
                holder.getBeng().setText(mcontext.getString(R.string.btnBengAlready));
            }else{
                holder.getBeng().setEnabled(true);
                holder.getBeng().setBackground(mcontext.getResources().getDrawable(R.drawable.red_button_style));
                holder.getBeng().setText(mcontext.getString(R.string.Benggg));
                holder.getBeng().setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        // Create custom dialog object
                        Intent i = new Intent(mcontext, BengSubmitDiagActivity.class);
                        i.putExtra(WeBengConstant.BENGID_KEY,mitems.get(position).getId());
                        mcontext.startActivity(i);
                }
            });
            }
            holder.getViewResult().setVisibility(View.GONE);
        } else {
            holder.getBeng().setVisibility(View.GONE);
            // holder.getViewResult().setTypeface(font.mBold);
            holder.getViewResult().setVisibility(View.VISIBLE);
            holder.getViewResult().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mcontext, ViewResultActivity.class);
                    i.putExtra(WeBengConstant.BENGID_KEY,mitems.get(position).getId());
                    Bundle ani =
                            ActivityOptions.makeCustomAnimation(mcontext, R.anim.in_from_right_slowly,R.anim.out_from_right_slowly ).toBundle();
                    mcontext.startActivity(i,ani);


                }
            });
            holder.getDeadline().setTextColor(Color.parseColor("#000000"));
        }
        return convertView;
    }
    private boolean containBeng(String bengId){
        for(int i=0;i<bengRegistered.length;i++){
            if(bengRegistered[i].equals(bengId))
                return true;
        }
        return false;
    }
    private static class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}
