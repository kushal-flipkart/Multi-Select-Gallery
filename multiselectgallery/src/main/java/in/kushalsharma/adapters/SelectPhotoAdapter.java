package in.kushalsharma.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import in.kushalsharma.multiselectgallery.R;
import in.kushalsharma.utils.MediaStorePhoto;

/**
 * Created by Kush on 9/11/2015.
 * Adapter for photo select
 */

public class SelectPhotoAdapter extends RecyclerView.Adapter {

    private ArrayList<MediaStorePhoto> bucketPhotoList;
    private Activity mAct;

    public SelectPhotoAdapter(ArrayList<MediaStorePhoto> bucketPhotoList, Activity mActivity) {
        this.bucketPhotoList = bucketPhotoList;
        this.mAct = mActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_image_item, parent, false);

        return new PhotoViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((PhotoViewHolder) holder).setId(position);
        ((PhotoViewHolder) holder).getImageView().setImageBitmap(null);

        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                return MediaStore.Images.Thumbnails.getThumbnail(
                        mAct.getContentResolver(),
                        Long.parseLong(bucketPhotoList.get(position).getId()),
                        MediaStore.Images.Thumbnails.MINI_KIND,
                        null);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (((PhotoViewHolder) holder).getId() == position)
                    ((PhotoViewHolder) holder).getImageView().setImageBitmap(bitmap);
            }
        }.execute();
    }

    @Override
    public int getItemCount() {
        return bucketPhotoList.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private int id;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.bucket_image);
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

    }
}
