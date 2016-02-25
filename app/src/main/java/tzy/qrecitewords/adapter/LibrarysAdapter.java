package tzy.qrecitewords.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import tzy.qrecitewords.LibraryWordsActivity;
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
            viewHodler.library = library;
            viewHodler.textViewLibrary = (TextView) convertView.findViewById(R.id.textView_libraryName);
            viewHodler.selectorIcon = (TextView)convertView.findViewById(R.id.imageView_selector_icon);
            convertView.setTag(viewHodler);

        }else{
            viewHodler = (ViewHodler) convertView.getTag();
        }

        viewHodler.textViewLibrary.setText(library.getLibraryName());
        ListView listView = (ListView) parent;
        if(listView.getCheckedItemPosition() == position) {
            viewHodler.selectorIcon.setText("See");
            viewHodler.context = context;
            viewHodler.selectorIcon.setOnClickListener(viewHodler);
        } else{
            viewHodler.selectorIcon.setText("");
            viewHodler.context = null;
        }
        return convertView;
    }

    private static  class ViewHodler implements View.OnClickListener{
        public  Library library;
        public TextView textViewLibrary;
        public TextView selectorIcon;
        public Context context;
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, LibraryWordsActivity.class);
            context.startActivity(intent);
            // this.getActivity().overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_out_anim);
            ((Activity)context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
        }
}
