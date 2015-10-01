# Multi-Select-Gallery
This library enables you to select multiple images from Android Device's SD Card

How to use :
---

          Intent mIntent = new Intent(MainActivity.this, PhotoAlbumActivity.class);
          mIntent.putExtra("following_class", "in.kushalsharma.imagegallery.ResultActivity");
          mIntent.putExtra("photo_type", 1);
          startActivity(mIntent);

