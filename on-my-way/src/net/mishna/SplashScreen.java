package net.mishna;

import net.mishna.utils.DBAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;

public class SplashScreen extends Activity
{

    // TODO : add the app initilize here :  DB copy to sd card etc ... 
    protected int _splashTime = 5000;

    private Thread splashTread;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide activity title .

	super.onCreate(savedInstanceState);
	setContentView(R.layout.splash);

	// thread for displaying the SplashScreen
	splashTread = new Thread() {
	    @Override
	    public void run()
	    {
		try
		{
		    synchronized (this)
		    {
			wait(_splashTime);
		    }

		}
		catch (InterruptedException e)
		{
		}
		finally
		{
		    finish();

		    Intent i = new Intent();
		    i.setClass(getApplicationContext(), MainActivity.class);
		    startActivity(i);

		}
	    }
	};

	splashTread.start();
	initializeApp();
	
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
	if (event.getAction() == MotionEvent.ACTION_DOWN)
	{
	    synchronized (splashTread)
	    {
		splashTread.notifyAll();
	    }
	}
	return true;
    }
    
    /**
     * This method is responsible for application init processes .
     */
    private void initializeApp()
    {
	Context ctx = BaseApplication.getContext() ;
	DBAdapter.initilizeApplicationDatabase(ctx.getDatabasePath(DBAdapter.DATABASE_NAME).getParent(), ctx.getAssets() ) ;
    }


}
