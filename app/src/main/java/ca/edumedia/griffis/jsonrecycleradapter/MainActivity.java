package ca.edumedia.griffis.jsonrecycleradapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;

import java.util.Arrays;

import ca.edumedia.griffis.jsonrecycleradapter.models.DataItem;
import ca.edumedia.griffis.jsonrecycleradapter.services.MyService;

public class MainActivity extends AppCompatActivity {

    public static final Uri JSON_URL = Uri.parse("http://jsonplaceholder.typicode.com/posts");
    public static final String TAG = "TAG";
    private PostAdapter mAdapter;
    private RecyclerView mRecyclerView;

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.hasExtra(MyService.SERVICE_PAYLOAD)){
                //looks like we got some data!!!
                DataItem[] dataItems = (DataItem[]) intent.getParcelableArrayExtra(MyService.SERVICE_PAYLOAD);
                Log.i(TAG, "Received the DataItems");
                //pass the data to the interface
                processTheJsonData(dataItems);

            }else if(intent.hasExtra(MyService.SERVICE_EXCEPTION)){
                //bad things happened. We bow our head in shame
                //Toast the user
                Log.i(TAG, MyService.SERVICE_EXCEPTION);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MyService.class);
        intent.setData(JSON_URL);
        startService(intent);

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(br, new IntentFilter(MyService.SERVICE_ACTION));

        mRecyclerView = (RecyclerView) findViewById(R.id.post_list);
        DataItem[] temp = new DataItem[0];
        mAdapter = new PostAdapter(this, temp);
        mRecyclerView.setAdapter( mAdapter );
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(br);
        super.onDestroy();
    }

    public void processTheJsonData( DataItem[] dataItems) {
        Log.i(TAG, "processTheJsonData: ");

        //instantiate a Custom Adapter
        mAdapter = new PostAdapter(this, dataItems);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
