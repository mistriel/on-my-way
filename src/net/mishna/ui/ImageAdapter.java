package net.mishna.ui;
import net.mishna.R;
import net.mishna.api.Bookmark;
import net.mishna.api.SederEnum;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
 
public class ImageAdapter extends BaseAdapter {
    private Activity activity;
    
    private SoundPool soundPool;
    
    // Keep all Images in array
    public static Integer[] sederIconsArray = {
            R.drawable.number1,
            R.drawable.number2,
            R.drawable.number3,
            R.drawable.number4,
            R.drawable.number5,
            R.drawable.number6
    };
 
    // Constructor
    public ImageAdapter(Activity activity ){
        this.activity = activity;
        
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//        sound = soundPool.load(mContext, R.raw.water, 1);
        
        
    }
 
    @Override
    public int getCount() {
        return sederIconsArray.length;
    }
 
    @Override
    public Object getItem(int position) {
        return sederIconsArray[position];
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {
	ImageButton imageView = new ImageButton(activity.getApplicationContext());
        imageView.setImageResource(sederIconsArray[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setAdjustViewBounds(true);
        imageView.setBackgroundDrawable(null);
        
        Animation anim = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.drop_down_btn);
        anim.setStartOffset(350*position);
        
        anim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
//        	soundPool.play(R.raw.drip2, 0.1f, 0.1f, 0, 0, 1.0f);
            }
        });
        
        final int pos = position ;
        imageView.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v)
	    {
		Toast.makeText(activity.getApplicationContext(), "Intent deprecated", Toast.LENGTH_SHORT).show();
//		Toast.makeText(activity.getApplicationContext(), "Button :" +pos+ " is clicked", Toast.LENGTH_SHORT).show();
//		
//		Intent intentToStartTractateSelectorActivity = new Intent(activity, TractateSelectorActivity.class);
//		Bookmark bookmark = new Bookmark(SederEnum.values()[pos]);
//		
//		intentToStartTractateSelectorActivity.putExtra("bookmark", bookmark);
//		activity.startActivity(intentToStartTractateSelectorActivity);
	    }
	});
        
        
        imageView.setAnimation(anim);
        anim.start();
        
        return imageView;
    }
}