package in.kushalsharma.multiselectgallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private ArrayList<Integer> bucketTotalImageCount = new ArrayList<>();
    private ArrayList<Integer> bucketSelectedImageCount = new ArrayList<>();

    private ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_album);

        ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
        }

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

        if (id == android.R.id.home) {
            finish();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ok) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getPhotoAlbumData() {
        bucketItemList.clear();
        bucketTotalImageCount.clear();
        bucketSelectedImageCount.clear();

        bucketTotalImageCount = getBucketTotalImageCount();
        bucketSelectedImageCount = getBucketSelectedImageCount();

        ArrayList<MediaStorePhoto> mList = new ArrayList<>(MediaStoreHelperMethods.getBucketCoverItems(getContentResolver()));

        for (int i = 0; i < mList.size(); i++) {
            MediaStorePhoto photo = mList.get(i);
            photo.setBucket("(" + bucketSelectedImageCount.get(i) + "/" + bucketTotalImageCount.get(i) + ") " + photo.getBucket());
            bucketItemList.add(photo);
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
            }
            ab.setTitle(String.valueOf(selectedPhotoList.size()) + " Photos Selected");
        }
        getPhotoAlbumData();
    }

    public ArrayList<Integer> getBucketTotalImageCount() {
        ArrayList<Integer> countList = new ArrayList<>();
        for (MediaStorePhoto photo : MediaStoreHelperMethods.getBucketCoverItems(getContentResolver())) {
            countList.add(MediaStoreHelperMethods.getAllPhotosInBucket(photo.getBucketId(), getContentResolver()).size());
        }
        return countList;
    }

    public ArrayList<Integer> getBucketSelectedImageCount() {
        ArrayList<Integer> countList = new ArrayList<>();
        for (MediaStorePhoto photo : MediaStoreHelperMethods.getBucketCoverItems(getContentResolver())) {
            int count = 0;
            for (MediaStorePhoto mPhoto : selectedPhotoList) {
                if (photo.getBucketId().equals(mPhoto.getBucketId()))
                    count++;
            }
            countList.add(count);
        }
        return countList;
    }
}
