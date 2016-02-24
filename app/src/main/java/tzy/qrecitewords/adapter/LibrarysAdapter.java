package tzy.qrecitewords.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tzy.qrecitewords.R;
import tzy.qrecitewords.javabean.Library;

/**
 * Created by tzy on 2016/2/24.
 */
public class LibrarysAdapter extends BaseAdapter {

    List<Library> libraries ;

    Activity context;

    public LibrarysAdapter(List<Library> libraries,Activity context) {
        this.libraries = libraries;
        this.context = context;
    }

    @Override
    public int getCount() {
        return null == libraries ? 0 : libraries.size();
    }

    @Override
    public Object getItem(int position) {
        return libraries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler = null;

        Library library = libraries.get(position);
        if(null == convertView){
            convertView = context.getLayoutInflater().inflate(R.layout.item_listview_library,null);
            viewHodler = new ViewHodler();
            viewHodler.textViewLibrary = (TextView) convertView.findViewById(R.id.textView_libraryName);
            viewHodler.selectorIcon = (ImageView)convertView.findViewById(R.id.imageView_selector_icon);
            convertView.setTag(viewHodler);
        }else{
            viewHodler = (ViewHodler) convertView.getTag();
        }

        viewHodler.textViewLibrary.setText(library.getLibraryName());

        return convertView;
    }

    private static  class ViewHodler {
        public TextView textViewLibrary;
        public ImageView selectorIcon;
    }
}
