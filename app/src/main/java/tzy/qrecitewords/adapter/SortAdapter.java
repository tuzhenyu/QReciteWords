package tzy.qrecitewords.adapter;

import java.util.List;


import android.content.Context;
import android.database.Cursor;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.Filter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import tzy.qrecitewords.R;
import tzy.qrecitewords.javabean.Word;
import tzy.qrecitewords.utils.UncaseAlphatIndexer;

public class SortAdapter extends CursorAdapter implements SectionIndexer  {

	UncaseAlphatIndexer alphabetIndexer;
	public SortAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		alphabetIndexer = new UncaseAlphatIndexer(c,1,Word.Alphabet);
	}

	public UncaseAlphatIndexer getAlphabetIndexer() {
		return alphabetIndexer;
	}

	public void setAlphabetIndexer(UncaseAlphatIndexer alphabetIndexer) {
		this.alphabetIndexer = alphabetIndexer;
	}

	/*public void updateListView(List<Word> list){
		this.list = list;
		notifyDataSetChanged();
	}
	*/

	/**
	 * 解决界面回复后alphabetIndexer没能及时更新Cursor的问题
	 * */
	@Override
	public Cursor swapCursor(Cursor newCursor) {
		alphabetIndexer.setCursor(newCursor);
		return super.swapCursor(newCursor);
	}

	@Override
	public int getCount() {
		return getCursor().getCount();
	}

	@Override
	public Object getItem(int position) {
		Word word = new Word();
		Cursor cursor = getCursor();
		cursor.moveToPosition(position);
		word.setWord(cursor.getString(1));
		word.setPhonogram(cursor.getString(2));
		word.setParaphrase(cursor.getString(3));
		return word;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		ViewHolder viewHolder = new ViewHolder();
		View view = LayoutInflater.from(context).inflate(R.layout.item_words, null);
		viewHolder.textViewWord = (TextView) view.findViewById(R.id.textView_word);
		viewHolder.textViewExpaliation = (TextView) view.findViewById(R.id.textView_expliation);
		viewHolder.textViewTtile =  (TextView) view.findViewById(R.id.catalog);
		viewHolder.word = new Word();
		view.setTag(viewHolder);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		Word.conveToWord(viewHolder.word,cursor);
		if (cursor.getPosition() == getPositionForSection(Word.getPositionForSection(viewHolder.word.word.charAt(0)))){
			viewHolder.textViewTtile.setVisibility(View.VISIBLE);
			viewHolder.textViewTtile.setText(String.valueOf(getAlpha(viewHolder.word.getWord())));
		}else {
			viewHolder.textViewTtile.setVisibility(View.GONE);
		}
		viewHolder.textViewExpaliation.setText(viewHolder.word.getParaphrase());
		viewHolder.textViewWord.setText(viewHolder.word.getWord());
	}

	@Override
	public Object[] getSections() {
		return null;
	}

	@Override
	public int getPositionForSection(int sectionIndex) {
		return alphabetIndexer.getPositionForSection(sectionIndex);
	}

	@Override
	public int getSectionForPosition(int position) {
		return alphabetIndexer.getSectionForPosition(position);
	}

	final static class ViewHolder{
		Word word;
		TextView textViewWord;
		TextView textViewExpaliation;
		TextView textViewTtile;
	}
	/**
	 * ��ȡӢ�ĵ�����ĸ����Ӣ����ĸ��#���档
	 * 
	 * @param str
	 * @return
	 */
	private char getAlpha(String str) {
		char  c = str.charAt(0);
		if(c >= 65 && c <= 90) return c;
		if(c >= 97 && c <= 122) return (char) (c-32);
		return '#';
	}

	@Override
	public Filter getFilter() {
		return super.getFilter();
	}
}
