package Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.HorizontalScrollView;
import android.widget.ImageView;

public class HorizontalAdapter extends BaseAdapter {
    private Context mContext;
    private Bitmap[] mbitmap;

    public HorizontalAdapter(Context c, Bitmap[] bm) {
        mContext = c;
        mbitmap = bm;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mbitmap.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            // new views - width 200, hwight 180, centered & cropped, 8dp padding
            imageView.setLayoutParams(new HorizontalScrollView.LayoutParams(200, 180));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(mbitmap[position]);
        return imageView;
    }
}
