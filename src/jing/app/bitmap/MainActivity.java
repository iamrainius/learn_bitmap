package jing.app.bitmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	
	private TextView mMemorySize;
	private Button mLargeBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mMemorySize = (TextView) findViewById(R.id.memory_size);
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		mMemorySize.setText(String.valueOf(maxMemory) + "KB");
		
		mLargeBitmap = (Button) findViewById(R.id.large_bitmap);
		mLargeBitmap.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.large_bitmap:
			openLargeBitmap();
			break;
		}
	}

	private void openLargeBitmap() {
		Intent intent = new Intent(this, LargeBitmapActivity.class);
		startActivity(intent);
	}

}
