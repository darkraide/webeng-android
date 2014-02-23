package Adapter;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BengItem {
    private TextView mName;
    private TextView mDeadline;
    private ImageView mImage;
    private TextView btnBeng;
    private TextView btnViewResult;
    private ProgressBar progress;

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

    public TextView getBeng() {
        return btnBeng;
    }

    public void setBeng(TextView btnBeng) {
        this.btnBeng = btnBeng;
    }

    public TextView getViewResult() {
        return btnViewResult;
    }

    public void setViewResult(TextView btnViewResult) {
        this.btnViewResult = btnViewResult;
    }

    public ProgressBar getProgress() {
        return progress;
    }

    public void setProgress(ProgressBar progress) {
        this.progress = progress;
    }


}
