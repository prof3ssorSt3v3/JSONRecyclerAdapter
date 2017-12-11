package ca.edumedia.griffis.jsonrecycleradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import ca.edumedia.griffis.jsonrecycleradapter.models.DataItem;

/**
 * Created by griffis on 2017-12-10.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {


    private Context mContext;
    private DataItem[] mDataItems;
    public static final String TAG = "tag";

    public PostAdapter(Context c, DataItem[] dataItems) {
        mContext = c;
        mDataItems = dataItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View postView = LayoutInflater.from(mContext)
                            .inflate(R.layout.post_main, parent, false);

        //send back an inflated view for one dataitem
        return new ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //bind a single DataItem to a single ViewHolder
        final DataItem myDataItem = mDataItems[position];

        //set the text in the various views
        holder.title.setText( myDataItem.getTitle() );
        holder.body.setText( myDataItem.getBody() );

        Log.i(TAG, "onBindViewHolder: " + myDataItem.getTitle() );
        //add click listeners
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //the View v will be pointing to holder.mView
                TextView ttl = (TextView) v.findViewById(R.id.text_title);
                Log.i(TAG, "onClick: " + ttl.getText().toString() );
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataItems.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        //declare a member variable for each view
        public View mView;
        public TextView title;
        public TextView body;

        public ViewHolder(View itemView) {
            super(itemView);
            //set the value for each member variable
            mView = itemView;
            title = (TextView) mView.findViewById(R.id.text_title);
            body = (TextView) mView.findViewById(R.id.text_body);
        }
    }
}
