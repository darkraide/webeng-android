package com.example.webeng;




import java.util.List;

import models.BengModelItem;
import models.BengModelItem.BengType;


import Fonts.FontManager;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class BengItemAdapter extends BaseAdapter {
	FontManager font = new FontManager();
	private static final String Name = "Beng : ";
	private static final String Deadline = "Deadline : ";
	private LayoutInflater mInflater;
	private List<BengModelItem> mitems;
    public BengItemAdapter(Context context,List<BengModelItem> items)
    {
    	mInflater = LayoutInflater.from(context);
    
    	mitems = items;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		
		BengItem holder;
		if(convertView == null )
		{
			convertView = mInflater.inflate(R.layout.imagedata, null);
			holder = new BengItem();
			
            holder.setName((TextView) convertView.findViewById(R.id.NameItem));
            
            holder.setDeadline((TextView) convertView.findViewById(R.id.Deadline));
            
            holder.setImage((ImageView) convertView.findViewById(R.id.Image));
            
            holder.setBeng((Button) convertView.findViewById(R.id.BengButton));
            
            holder.setViewResult((Button) convertView.findViewById(R.id.BtnViewResult));
            
            holder.getImage().setScaleType(ScaleType.FIT_XY);
            convertView.setTag(holder);			
		}
		else
		{
			holder = (BengItem) convertView.getTag();
		}
		
		((TextView) convertView.findViewById(R.id.name)).setTypeface(font.mMedium);
		((TextView) convertView.findViewById(R.id.date)).setTypeface(font.mMedium);
		holder.getName().setText(mitems.get(position).getName());
		holder.getName().setTypeface(font.getInstance().mMedium);
		holder.getDeadline().setText(mitems.get(position).getDaedline());
		holder.getDeadline().setTypeface(font.mBold);
        holder.getImage().setImageBitmap(mitems.get(position).getImage());
        
        if(mitems.get(position).getBeng() == BengType.BENGING){
        	holder.getBeng().setVisibility(View.VISIBLE);
        	holder.getBeng().setTypeface(font.mBold);
        	holder.getDeadline().setTextColor(Color.parseColor("#e4511d"));
        	
        	holder.getViewResult().setVisibility(View.GONE);
        }
        else{
        	holder.getBeng().setVisibility(View.GONE);
        	holder.getViewResult().setTypeface(font.mBold);
        	holder.getViewResult().setVisibility(View.VISIBLE);
        }
        return convertView;		
	}


}
