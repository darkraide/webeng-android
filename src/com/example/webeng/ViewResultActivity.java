package com.example.webeng;

import android.app.ActionBar;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import BaseClasses.BaseActivity;

/**
 * Created by sangcu on 3/10/14.
 */
public class ViewResultActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setContentView(R.layout.view_result_activity);
        LayoutInflater inflator = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.action_bar_menu, null);

        //final TextView title = ((TextView) v.findViewById(R.id.titleActivity));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);

        lp.gravity = Gravity.LEFT;

        TextView t=(TextView)v.findViewById(R.id.txtActionBarTitle);
        ImageView preButton=(ImageView)v.findViewById(R.id.imgPreviewItem);
        preButton.setVisibility(View.VISIBLE);
        LinearLayout preLayout=(LinearLayout)v.findViewById(R.id.layout_groupTitle);
        t.setBackgroundResource( R.drawable.actionbar_button);
        t.setText(getString(R.string.lblViewResult));
        //t.setOnClickListener(new GroupButtonClick());
        preLayout.setOnClickListener(new GroupButtonClick());

        v.setLayoutParams(lp);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(v);
        getActionBar().setTitle(R.string.lblViewResult);
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public void onBackPressed()
    {
        this.finish();
        overridePendingTransition(R.anim.out_from_right_activityresult, R.anim.out_to_left_activityresult);
    }
    private class  GroupButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    }
}
