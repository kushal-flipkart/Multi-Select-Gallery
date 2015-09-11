package in.kushalsharma.multiselectgallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import in.kushalsharma.adapters.PhotoAlbumAdapter;
import in.kushalsharma.adapters.SelectPhotoAdapter;
import in.kushalsharma.utils.MediaStoreHelperMethods;
import in.kushalsharma.utils.MediaStorePhoto;

public class SelectPhotoActivity extends AppCompatActivity {

    private String bucketId;
    private ArrayList<MediaStorePhoto> bucketPhotoList;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photo);

        bucketId = getIntent().getStringExtra("bucket_id");
        bucketPhotoList = MediaStoreHelperMethods.getAllPhotosInBucket(bucketId, getContentResolver());

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new SelectPhotoAdapter(bucketPhotoList, this);
        mLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
