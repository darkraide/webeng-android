package com.example.webeng;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

/**
 * Created by sangcu on 2/13/14.
 */
public class UserCreate extends Activity {
    private ViewFlipper viewFlipper;
    private float lastX;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_create);
        this.getActionBar().hide();
        viewFlipper= (ViewFlipper) findViewById(R.id.viewFlipper);
        Button nextButton=(Button)findViewById(R.id.btnNext);
        viewFlipper.setInAnimation(this, R.anim.in_from_right);
        viewFlipper.setOutAnimation(this, R.anim.out_to_left);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                viewFlipper.showNext();
            }
        });
    }

}