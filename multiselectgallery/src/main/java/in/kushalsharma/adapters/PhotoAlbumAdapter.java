package in.kushalsharma.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.kushalsharma.multiselectgallery.R;
import in.kushalsharma.multiselectgallery.SelectPhotoActivity;
import in.kushalsharma.utils.MediaStorePhoto;

/**
 * Created by Kush on 9/11/2015.
 * Adapter for photo album
 */

public class PhotoAlbumAdapter extends RecyclerView.Adapter {

    private ArrayList<MediaStorePhoto> bucketItemList;
    private ArrayList<MediaStorePhoto> selectedPhotoList;
    private Activity mAct;

    public PhotoAlbumAdapter(ArrayList<MediaStorePhoto> bucketItemList, ArrayList<MediaStorePhoto> selectedPhotoList, Activity mActivity) {
        this.bucketItemList = bucketItemList;
        this.selectedPhotoList = selectedPhotoList;
        this.mAct = mActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_bucket_item, parent, false);

        return new BucketItemViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((BucketItemViewHolder) holder).setId(position);
        ((BucketItemViewHolder) holder).getImageView().setImageBitmap(null);

        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                return MediaStore.Images.Thumbnails.getThumbnail(
                        mAct.getContentResolver(),
                        Long.parseLong(bucketItemList.get(position).getId()),
                        MediaStore.Images.Thumbnails.MINI_KIND,
                        null);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (((BucketItemViewHolder) holder).getId() == position)
                    ((BucketItemViewHolder) holder).getImageView().setImageBitmap(bitmap);
            }
        }.execute();
        ((BucketItemViewHolder) holder).getNameView().setText(bucketItemList.get(position).getBucket());

        ((BucketItemViewHolder) holder).getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mAct, SelectPhotoActivity.class);
                mIntent.putExtra("bucket_id", bucketItemList.get(position).getBucketId());
                mIntent.putParcelableArrayListExtra("selected_photo_list", selectedPhotoList);
                mAct.startActivityForResult(mIntent, 1000);
            }
        });

        ((BucketItemViewHolder) holder).getNameView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mAct, SelectPhotoActivity.class);
                mIntent.putExtra("bucket_id", bucketItemList.get(position).getBucketId());
                mIntent.putParcelableArrayListExtra("selected_photo_list", selectedPhotoList);
                mAct.startActivityForResult(mIntent, 1000);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bucketItemList.size();
    }

    public class BucketItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView nameView;
        private int id;

        public BucketItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.bucket_image);
            nameView = (TextView) itemView.findViewById(R.id.bucket_name);
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getNameView() {
            return nameView;
        }
    }
}
