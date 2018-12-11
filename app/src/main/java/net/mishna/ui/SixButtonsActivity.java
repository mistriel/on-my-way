package net.mishna.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.GridView;

import net.mishna.R;

/**
 * This class shows a six buttons view Organized in pairs . The activity
 * animates the button arrangmeant on screen on the first appearance of the
 * buttons and on click on a button .
 *
 * @author Mistriel
 */
public class SixButtonsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide activity title .
        super.onCreate(savedInstanceState);


        setContentView(R.layout.six_buttons_grid);
        GridView gridView = (GridView) findViewById(R.id.grid_view);

        // Instance of ImageAdapter Class
        gridView.scheduleLayoutAnimation();
        gridView.clearDisappearingChildren();
        gridView.clearChoices();

        gridView.setAdapter(new ImageAdapter(this));

    }
}
