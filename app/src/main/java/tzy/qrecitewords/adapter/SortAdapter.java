package tzy.qrecitewords.adapter;

import java.util.List;


import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import tzy.qrecitewords.R;
import tzy.qrecitewords.javabean.Word;

public class SortAdapter extends BaseAdapter implements SectionIndexer {
	
	private List<Word> list = null;
	
	private Context mContext;
	
	public SortAdapter(Context mContext,List<Word> list){
		this.mContext = mContext;
		this.list = list;
		map = new SparseArray<>(27);
	}
	public void updateListView(List<Word> list){
		this.list = list;
		notifyDataSetChanged();
	}
	

	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		final Word mContent = list.get(position);
		if (convertView== null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_words, null);
			viewHolder.textViewWord = (TextView) convertView.findViewById(R.id.textView_word);
			viewHolder.textViewExpaliation = (TextView) convertView.findViewById(R.id.textView_expliation);
			viewHolder.textViewTtile =  (TextView) convertView.findViewById(R.id.catalog);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if (position == isLetterShowPosition(position)) {
			viewHolder.textViewTtile.setVisibility(View.VISIBLE);
			viewHolder.textViewTtile.setText(String.valueOf(getAlpha(mContent.getWord())));
		}else {
			viewHolder.textViewTtile.setVisibility(View.GONE);
		}
		viewHolder.textViewWord.setText(this.list.get(position).getWord());
		return convertView;
	}

	@Override
	public Object[] getSections() {
		return null;
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	@Override
	public int getPositionForSection(int c) {

		Integer position = map.get(getUpChar((char) c),-1);
			return position;
	}


	@Override
	public int getSectionForPosition(int position) {
		return list.get(position).getWord().charAt(0);
	}

	final static class ViewHolder{
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

	SparseArray<Integer> map;
	int isLetterShowPosition(int position){
		char firstLetter= getAlpha(list.get(position).getWord());
		Integer letterPos = map.get(firstLetter,-1);
		if(letterPos != -1)
			return letterPos;
		map.put(firstLetter, position);
		return position;
	}

	public char getUpChar(char c) {
		if(c >= 65 && c <= 90) return c;
		if(c >= 97 && c <= 122) return (char) (c-32);
		return '#';
	}
}
