package com.wordpress.smdaudhilbe.listdem;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.demoListView)
    StoryListView listView;

    @Bind(R.id.newStoriesButton)
    Button newStoriesButton;

    List<String> items;
    private StoryListAdapter adapter;
    int count = 1, listItemCount = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        items = new ArrayList<String>();
        for (int i = 0; i < listItemCount; i++) {
            items.add("Story : " + (i + 1));
        }
        adapter = new StoryListAdapter(getApplicationContext(), 0, items);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(scrollListener);
    }

    //  Adapter
    public class StoryListAdapter extends ArrayAdapter<String> {

        private List<String> items;
        private LayoutInflater layoutInflator;

        public StoryListAdapter(Context context, int resource, List<String> items) {
            super(context, resource);
            layoutInflator = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = layoutInflator.inflate(android.R.layout.simple_list_item_1, parent, false);
                holder.textView = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(items.get(position));
            return convertView;
        }

        @Override
        public int getCount() {
            return items.size();

        }

        //  ViewHolder
        class ViewHolder {
            TextView textView;
        }
    }

    @OnClick(R.id.loadItem)
    public void loadItemClick() {
        //  tutorial by http://chris.banes.me/2013/02/21/listview-keeping-position/
        int firstVisPos = listView.getFirstVisiblePosition();
        View firstVisView = listView.getChildAt(0);
        int top = firstVisView != null ? firstVisView.getTop() : 0;
        listView.setBlockLayoutChildren(true);
        items.add(0, "New Story " + count++);
        int itemsAddedBeforeFirstVisible = 1;   //  no. of stories added in list
        adapter.notifyDataSetChanged();
        listView.setBlockLayoutChildren(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setSelectionFromTop(firstVisPos + itemsAddedBeforeFirstVisible, top);
        } else {
            listView.setSelection(firstVisPos + itemsAddedBeforeFirstVisible);
        }
        newStoriesButton.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.newStoriesButton)
    public void scrollListClick() {
        listView.smoothScrollToPosition(0);
        newStoriesButton.setVisibility(View.INVISIBLE);
    }

    @OnItemClick(R.id.demoListView)
    public void listItemClick(int position) {
        Toast.makeText(getApplicationContext(), "Position : " + position, Toast.LENGTH_SHORT).show();
    }

    //  OnScrollListener
    AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem == 0) {
                newStoriesButton.setVisibility(View.INVISIBLE);
            }
        }
    };
}