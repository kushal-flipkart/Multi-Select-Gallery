package in.kushalsharma.multiselectgallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import in.kushalsharma.adapters.PhotoAlbumAdapter;
import in.kushalsharma.utils.MediaStoreHelperMethods;
import in.kushalsharma.utils.MediaStorePhoto;

public class PhotoAlbumActivity extends AppCompatActivity {

    private ArrayList<MediaStorePhoto> selectedPhotoList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private ArrayList<MediaStorePhoto> bucketItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_album);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new PhotoAlbumAdapter(bucketItemList, selectedPhotoList, this);
        mLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getPhotoAlbumData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_album, menu);
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

    public void getPhotoAlbumData() {
        bucketItemList.clear();
        for (MediaStorePhoto bucketPhoto : MediaStoreHelperMethods.getBucketCoverItems(getContentResolver())) {
            bucketItemList.add(bucketPhoto);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            selectedPhotoList.clear();
            ArrayList<MediaStorePhoto> photoList = data.getParcelableArrayListExtra("selected_photo_list");
            for (MediaStorePhoto mPhoto : photoList) {
                selectedPhotoList.add(mPhoto);
                Log.e("Selected photo list ", mPhoto.getDataUri());
            }
        }
    }
}
