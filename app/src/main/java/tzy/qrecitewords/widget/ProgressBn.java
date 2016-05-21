package tzy.qrecitewords.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import tzy.qrecitewords.R;

/**
 * Created by tzy on 2016/5/11.
 */
public class ProgressBn extends FrameLayout  {

    TextView textView;

    //TextView titleTx;

    //TextView cancleTx;

    ProgressBar progressBar;
    OnClickListener textClickListener;
    //OnClickListener cancleListener;
    OnCompletedListener mCompletedListener;

    public interface OnCompletedListener{
        void onComplete(int porgress);
    }

    public ProgressBn(Context context) {
        this(context,null);
    }

    public ProgressBn(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressBn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_porgress_bn, this, true);
        textView = (TextView) findViewById(R.id.progress_text);
        //titleTx = (TextView) findViewById(R.id.title);
       // cancleTx = (TextView) findViewById(R.id.cancleTx);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if( textClickListener != null){
                    textClickListener.onClick(v);
                }
            }
        });
    }



    public void setProgress(int progress){
        progressBar.setProgress(progress);
    }

    public void setProgressText(CharSequence text){
        textView.setText(text);
    }

    public void setProgressText(int id){
        textView.setText(id);
    }

    public OnClickListener getTextClickListener() {
        return textClickListener;
    }

    public void setTextClickListener(OnClickListener textClickListener) {
        this.textClickListener = textClickListener;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

   public void setMaxProgress(int max){
       progressBar.setMax(max);
       if(isComplete()){
           if( mCompletedListener != null){
               mCompletedListener.onComplete(progressBar.getMax());
           }
       }


   }

    public Boolean isComplete(){
        boolean tag = progressBar.getProgress() >= progressBar.getMax();
        return tag;
    }

    public void incrementProgressBy(int diff){
        progressBar.incrementProgressBy(diff);
        if(isComplete()){
            if( mCompletedListener != null){
                mCompletedListener.onComplete(progressBar.getMax());
            }
        }
    }

    public void setCancleListener(OnClickListener cancleListener) {
       // this.cancleListener = cancleListener;
       // cancleTx.setOnClickListener(cancleListener);
    }

    public OnCompletedListener getCompletedListener() {
        return mCompletedListener;
    }

    public void setOnCompletedListener(OnCompletedListener completedListener) {
        mCompletedListener = completedListener;
    }

    public int getProgress(){
      return   progressBar.getProgress();
    }

    public int getMax(){
        return progressBar.getMax();
    }
}
