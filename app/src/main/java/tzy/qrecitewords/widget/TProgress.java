package tzy.qrecitewords.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tzy on 2016/5/15.
 */
public class TProgress extends View {

    int leftValue;

    int rightValue;

    TextPaint textPaint;

    int value;

    int increLength;//步进值

    public TProgress(Context context) {
        this(context,null);
    }

    public TProgress(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void init(){
        textPaint  = new TextPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
    /*主线程*/
    public void setLeftValue(int leftValue){
        this.leftValue = leftValue;
        adjust();
    }
    /*主线程*/
    public void setRightValue(int rightValue){
        this.rightValue  = rightValue;
        adjust();
    }
    /*主线程*/
    public void adjust(){
        if(leftValue + rightValue > value){
            throw new IllegalArgumentException("总数值小于左右值之和");
        }
        postInvalidate();
    }

    public void changeValue(int nValue){

        if(leftValue + rightValue > nValue){
            throw new IllegalArgumentException("总数值小于左右值之和");
        }
        this.value = nValue;
        postInvalidate();
    }


}
