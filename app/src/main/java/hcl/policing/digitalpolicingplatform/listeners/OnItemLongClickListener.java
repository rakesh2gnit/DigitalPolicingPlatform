package hcl.policing.digitalpolicingplatform.listeners;

import android.view.View;

public class OnItemLongClickListener implements View.OnLongClickListener {
    private int position;
    private OnItemLongClickCallback onItemClickCallback;

    public OnItemLongClickListener(int position, OnItemLongClickCallback onItemClickCallback) {
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public boolean onLongClick(View v) {
        onItemClickCallback.onItemClicked(v, position);
        return false;
    }

    public interface OnItemLongClickCallback {
        void onItemClicked(View view, int position);
    }
}