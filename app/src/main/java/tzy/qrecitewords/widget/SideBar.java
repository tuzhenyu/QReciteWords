package tzy.qrecitewords.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


/**
 * 
 * �Ҳ����ĸ����View 
 * 
 * @author 
 *
 */

public class SideBar extends View {
	
	//�����¼�
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	
	 // 26����ĸ  
    public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I",  
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
            "W", "X", "Y", "Z", "#" };  
    //ѡ��
    private int choose = -1;
	
    private Paint paint = new Paint();
    
    private TextView mTextDialog;
    
    /**
     * ΪSideBar��ʾ��ĸ��TextView
     * 
     * @param mTextDialog
     */
    public void setTextView(TextView mTextDialog){
    	this.mTextDialog = mTextDialog;
    }
    

	public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SideBar(Context context) {
		super(context);
	}
	/**
	 * 
	 * ��д��onDraw�ķ���
	 * 
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int height = getHeight();//��ȡ��Ӧ�ĸ߶�
		int width = getWidth();//��ȡ��Ӧ�Ŀ��
		int singleHeight = height/b.length;//��ȡÿһ����ĸ�ĸ߶�
		for (int i = 0; i < b.length; i++) {
			paint.setColor(Color.rgb(33, 65, 98));
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(20);
			//ѡ�е�״̬
			if (i == choose) {
				paint.setColor(Color.parseColor("#3399ff"));
				paint.setFakeBoldText(true);//�����Ƿ�Ϊ��������
			}
			//x�������=�м�-�ַ�����ȵ�һ��
			float xPos = width / 2- paint.measureText(b[i])/2;
			float yPos = singleHeight*i + singleHeight;
			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();//���û���
		}
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		
		final int action = event.getAction();
		final float y = event.getY();//���y����
		final int oldChoose = choose;
		
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		
		final int c = (int)(y / getHeight() * b.length);//���y������ռ�߶ȵı���*b����ĳ��Ⱦ͵��ڵ��b�еĸ���
		
		switch (action) {
		case MotionEvent.ACTION_UP:
			//setBackgroundDrawable(new ColorDrawable(0x00000000));//���ñ�����ɫ
			choose = -1;
			invalidate();
			if (mTextDialog != null) {
				mTextDialog.setVisibility(View.INVISIBLE);
			}
			break;

		default:
			if (oldChoose != c) {
				if (c>=0 && c<b.length) {
					if (listener != null) {
						listener.onTouchingLetterChanged(b[c]);
					}
					if (mTextDialog != null) {
						mTextDialog.setText(b[c]);
						mTextDialog.setVisibility(View.VISIBLE);
					}
					choose = c;
					invalidate();
				}
			}
			break;
		}
		return true;
	}
	/**
	 * �����ɿ��ķ���
	 * 
	 * @param onTouchingLetterChangedListener
	 */
	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}
	
	/**
	 * 
	 * �ӿ�
	 * 
	 * @author 
	 *
	 */
	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

}
