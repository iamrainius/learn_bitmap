package jing.app.bitmap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LargeBitmapActivity extends Activity implements OnClickListener {
	private static final int REQUEST_CODE = 0;
	Button mPick;
	ImageView mImageView;
	TextView mMeta;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.large_bitmap_activity);
		
		mPick = (Button) findViewById(R.id.pick_image);
		mPick.setOnClickListener(this);
		
		mImageView = (ImageView) findViewById(R.id.show_image);
		mMeta = (TextView) findViewById(R.id.meta);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.pick_image) {
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");
			startActivityForResult(intent, REQUEST_CODE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			// mImageView.setImageURI(uri);
			LoadBitmapTask task = new LoadBitmapTask();
			task.execute(uri);
		}
		
	}
	
	class LoadBitmapTask extends AsyncTask<Uri, Void, Void> {
		int mWidth;
		int mHeight;
		String mType;
		Bitmap mBitmap;
		
		@Override
		protected Void doInBackground(Uri... uris) {
			try {
				mBitmap = decodeSampledBitmapFromUri(uris[0], 100, 100);
			} catch (IOException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mImageView.setImageBitmap(mBitmap);
		}
		
	}
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	
	        // Calculate ratios of height and width to requested height and width
	        final int heightRatio = Math.round((float) height / (float) reqHeight);
	        final int widthRatio = Math.round((float) width / (float) reqWidth);
	
	        // Choose the smallest ratio as inSampleSize value, this will guarantee
	        // a final image with both dimensions larger than or equal to the
	        // requested height and width.
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }
	
	    return inSampleSize;
	}
	
	public Bitmap decodeSampledBitmapFromUri(Uri uri,
	        int reqWidth, int reqHeight) throws IOException {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    InputStream is = getContentResolver().openInputStream(uri);
	    BitmapFactory.decodeStream(is, null, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    is = getContentResolver().openInputStream(uri);
	    return BitmapFactory.decodeStream(is, null, options);
	}
	
}
