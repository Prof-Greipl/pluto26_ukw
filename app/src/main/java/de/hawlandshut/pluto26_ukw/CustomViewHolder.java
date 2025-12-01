package de.hawlandshut.pluto26_ukw;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    public final TextView mLine1;
    public final TextView mLine2;

    public CustomViewHolder(View view) {
        super(view);
        // Note that we cache the views here...
        mLine1 = view.findViewById(R.id.post_view_line1);
        mLine2 =  view.findViewById(R.id.post_view_line2);
    }
}