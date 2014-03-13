package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.webeng.R;

import java.util.List;

import models.location;

/**
 * Created by sangcu on 3/6/14.
 */
public class LocationCheckAdapter extends BaseAdapter {
    private CheckBox ckLocation;
    private  List<location> _locations;
    private LayoutInflater mInflater;
    Context _context;
    public LocationCheckAdapter(Context context,List<location> locations) {
        _locations=locations;
        _context=context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return _locations.size();
    }

    @Override
    public Object getItem(int i) {
        return _locations.get(i);
    }

    @Override
    public long getItemId(int i) {
        return _locations.get(i).getKey();
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        if(convertView==null)
            convertView = mInflater.inflate(R.layout.location_item_check, null);
        if(convertView!=null){
            ckLocation=(CheckBox)convertView.findViewById(R.id.ckLocation);
            if(ckLocation!=null && position<_locations.size()&&position>=0){

                final location l=_locations.get(position);

                ckLocation.setText(l.getName());
                ckLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        l.setChecked(b);
                    }
                });
                //ckLocation.setTextKeepState(l.getId());
            }
        }
        return  convertView;
    }
}
