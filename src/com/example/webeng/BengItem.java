package com.example.webeng;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BengItem {
	private TextView mName;
	private TextView mDeadline;
    private ImageView mImage;
    private Button btnBeng;
    private Button btnViewResult;
   
	public TextView getName() {
		return mName;
	}
	public void setName(TextView mName) {
		this.mName = mName;
	}
	public TextView getDeadline() {
		return mDeadline;
	}
	public void setDeadline(TextView mDeadline) {
		this.mDeadline = mDeadline;
	}
	public ImageView getImage() {
		return mImage;
	}
	public void setImage(ImageView mImage) {
		this.mImage = mImage;
	}
	public Button getBeng() {
		return btnBeng;
	}
	public void setBeng(Button btnBeng) {
		this.btnBeng = btnBeng;
	}
	public Button getViewResult() {
		return btnViewResult;
	}
	public void setViewResult(Button btnViewResult) {
		this.btnViewResult = btnViewResult;
	}
	
    
   
}
