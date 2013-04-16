package jing.app.bitmap;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class LargeBitmapActivity extends Activity implements OnClickListener {
	private static final int REQUEST_CODE = 0;
	Button mPick;
	ImageView mImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.large_bitmap_activity);
		
		mPick = (Button) findViewById(R.id.pick_image);
		mPick.setOnClickListener(this);
		
		mImageView = (ImageView) findViewById(R.id.show_image);
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
			mImageView.setImageURI(uri);
		}
		
	}
	
	class LoadBitmapTask extends AsyncTask<Uri, Void, Void> {
		int mWidth;
		int mHeight;
		String mType;
		
		@Override
		protected Void doInBackground(Uri... uris) {
			BitmapFactory.Options options = new Options();
			options.inJustDecodeBounds = true;
			try {
				InputStream is = getContentResolver().openInputStream(uris[0]);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
}
