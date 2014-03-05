package com.example.webeng;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

import models.WeBengConstant;

/**
 * Created by sangcu on 2/13/14.
 */
public class UserCreate extends Activity {
    private ViewFlipper viewFlipper;
    private float lastX;
    private String email;
    private String password;
    private String gender;
    private String fbid;
    private String fbtoken;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_create);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        final Button nextButton = (Button) findViewById(R.id.btnNext);
        viewFlipper.setInAnimation(this, R.anim.in_from_right);
        viewFlipper.setOutAnimation(this, R.anim.out_to_left);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (viewFlipper.getDisplayedChild() == 1) {
                    nextButton.setText(R.string.btnDone);
                } else {
                    nextButton.setText(R.string.btnNext);
                }
                viewFlipper.showNext();
            }
        });
        email=getIntent().getStringExtra(WeBengConstant.EMAIL_KEY);
        gender=getIntent().getStringExtra(WeBengConstant.GENDER);
        fbid=getIntent().getStringExtra(WeBengConstant.FB_ID);
        fbtoken=getIntent().getStringExtra(WeBengConstant.ACCESS_TOKEN);

        if(email!=null&&fbid!=null){
            viewFlipper.removeViewAt(0);

        }
        if(gender!=null)
            viewFlipper.removeViewAt(1);


    }

}