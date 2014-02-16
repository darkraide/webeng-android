package Adapter;

import http.ImageLoader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.example.webeng.R;

import models.BengModelItem;
import models.BengModelListItem.BengType;

import Fonts.FontManager;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
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
	// private static final String Name = "Beng : ";
	// private static final String Deadline = "Deadline : ";
	private LayoutInflater mInflater;
	Context mcontext;
	private List<BengModelItem> mitems;
	private ImageLoader  imgLoader ;
	public BengItemAdapter(Context context, List<BengModelItem> items) {
		mInflater = LayoutInflater.from(context);
		mcontext = context;
		mitems = items;
		imgLoader = new ImageLoader(context);
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
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.imagedata, null);
			holder = new BengItem();

			holder.setName((TextView) convertView.findViewById(R.id.NameItem));

			holder.setDeadline((TextView) convertView
					.findViewById(R.id.Deadline));

			holder.setImage((ImageView) convertView.findViewById(R.id.Image));

			holder.setBeng((Button) convertView.findViewById(R.id.BengButton));

			holder.setViewResult((Button) convertView
					.findViewById(R.id.BtnViewResult));

			holder.getImage().setScaleType(ScaleType.FIT_CENTER);
			convertView.setTag(holder);
		} else {
			holder = (BengItem) convertView.getTag();
		}
		Date date = new Date();
		DateFormat fm = DateFormat.getDateTimeInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"hh:mm:ss dd-MM-yyyy");
		TimeZone timezone = TimeZone.getTimeZone("Etc/GMT+7");

		dateFormat.setTimeZone(timezone);
		Date convertedDate = null;
		try {
			convertedDate = dateFormat.parse("20:33:00 05-03-2014");
		} catch (ParseException e) {
			// convertedDate = null;

			// TODO Auto-generated catch block
		Log.v("ParseDeathline",e.toString());
		}
		// date.parse("Wed Jan 22 2014 19:01:45 GMT+0700");

		// ((TextView)
		// convertView.findViewById(R.id.date)).setTypeface(font.mMedium);
		holder.getName().setText(mitems.get(position).getDescription());
		// holder.getName().setTypeface(font.getInstance().mMedium);
		holder.getDeadline().setText(dateFormat.format(convertedDate));
		// holder.getDeadline().setTypeface(font.mBold);
		
		
		   
		
		     imgLoader.DisplayImage(mitems.get(position).getPhoto()[0], holder.getImage());

		//holder.getImage().setImageResource(R.drawable.koala);

		if (mitems.get(position).getType() ==1) {
			holder.getBeng().setVisibility(View.VISIBLE);
			// holder.getBeng().setTypeface(font.mBold);
			holder.getDeadline().setTextColor(Color.parseColor("#e4511d"));
			holder.getBeng().setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Create custom dialog object
					final Dialog dialog = new Dialog(mcontext);
					// Include dialog.xml file
					dialog.setContentView(R.layout.bengdialog);
					// Set dialog title
					dialog.setTitle("Custom Dialog");

					// set values for custom dialog components - text, image and
					// button
					

					dialog.show();
				}
			});
			holder.getViewResult().setVisibility(View.GONE);
		} else {
			holder.getBeng().setVisibility(View.GONE);
			// holder.getViewResult().setTypeface(font.mBold);
			holder.getViewResult().setVisibility(View.VISIBLE);
			holder.getDeadline().setTextColor(Color.parseColor("#000000"));
		}
		return convertView;
	}

}
