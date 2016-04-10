package tzy.qrecitewords.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import tzy.qrecitewords.R;

/**
 * Created by tzy on 2016/4/10.
 */
public class WordLableView extends LinearLayout {

    TextView txNum;

    TextView txLable;

    int txNumColor;

    int txLableColor;

    int txNumTextSize;

    int txLableTextSize;

    public final static int visible = 0;

    public final static int inVisible = 1;

    public final static int gone = -1;

    public WordLableView(Context context) {
        this(context, null);
    }

    public WordLableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public WordLableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        attachAttr(context, attrs);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_word_lable, this, true);
        txNum = (TextView) findViewById(R.id.tx_num);
        txLable = (TextView) findViewById(R.id.tx_word_table);

        txNumColor = txNum.getTextColors().getDefaultColor();
        txLableColor = txLable.getTextColors().getDefaultColor();

        //txNumTextSize = px2sp(context, txNum.getTextSize());
        //txLableTextSize = px2sp(context,txLable.getTextSize());
    }

   private void attachAttr(Context context,AttributeSet attrs){
       TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WordLableView,0,0);

       Drawable numBg = a.getDrawable(R.styleable.WordLableView_WordLableNumbg);
       if(null != numBg) txNum.setBackground(numBg);

       String lableString = a.getString(R.styleable.WordLableView_WordLableTextContent);
       txLable.setText(lableString);

       String numTx = a.getString(R.styleable.WordLableView_WordLableNumText);
       txNum.setText(numTx);

       txNumColor = a.getColor(R.styleable.WordLableView_WordLableNumTxColor, txNumColor);
       txLableColor = a.getColor(R.styleable.WordLableView_WordLableTxColor, txLableColor);

       float txNumTextSize_f = a.getDimension(R.styleable.WordLableView_WordLableNumTxSize,
               getResources().getDimension(R.dimen.defalut_txNum_textSize));
       txNumTextSize = px2sp(context,txNumTextSize_f);

       float txLableTextSize_f = a.getDimensionPixelSize(R.styleable.WordLableView_WordLableTxSize,
               getResources().getDimensionPixelSize(R.dimen.defalut_txLable_textSize));
       txLableTextSize = px2sp(context,txLableTextSize_f);

       txNum.setTextSize(txNumTextSize);
       txNum.setTextColor(txNumColor);

       txLable.setTextSize(txLableTextSize);
       txLable.setTextColor(txLableColor);

       int txTableVisibility = a.getInt(R.styleable.WordLableView_WordLableVisibility,View.VISIBLE);

       switch(txTableVisibility){
           case visible:
               txLable.setVisibility(View.VISIBLE);
               break;
           case inVisible:
               txLable.setVisibility(View.INVISIBLE);
               break;
           case gone:
               txLable.setVisibility(View.GONE);
               break;
       }
       a.recycle();

   }

    public void setLbaleText(String text){
        txLable.setText(text);
    }

    public void setLbaleText(int id){
        txLable.setText(id);
    }

    public void setLNumText(String text){
        txNum.setText(text);
    }

    public void setLNumText(int id){
        txNum.setText(id);
    }

    public void setNumBg(Drawable drawable){
        txNum.setBackground(drawable);
    }

    public TextView getTxLable() {
        return txLable;
    }

    public void setTxLable(TextView txLable) {
        this.txLable = txLable;
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
