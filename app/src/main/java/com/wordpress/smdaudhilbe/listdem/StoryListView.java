package com.wordpress.smdaudhilbe.listdem;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class StoryListView extends ListView {

    private boolean blockLayoutChildren;

    public StoryListView(Context context) {
        super(context);
    }

    public StoryListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBlockLayoutChildren(boolean blockLayoutChildren) {
        this.blockLayoutChildren = blockLayoutChildren;
    }

    @Override
    protected void layoutChildren() {
        if (!blockLayoutChildren) {
            super.layoutChildren();
        }
    }
}