package net.mishna.ui.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageButton;

public class LabeldImageButton extends ImageButton
{
    protected String txt2Draw ; 
    protected int txtSize ; 
    protected int txtColor ;

    public LabeldImageButton(Context context, String txt, int size, int color)
    {
	super(context);
	txt2Draw = txt ;
	txtSize = size ;
	txtColor = color ;
    }

    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        final Paint paint = new Paint();
        
        paint.setColor(txtColor);
        
        final float densityMultiplier = getContext().getResources().getDisplayMetrics().density;
        final float scaledPx = txtSize * densityMultiplier;
        paint.setTextSize(scaledPx);
        final float actualSize = paint.measureText(txt2Draw);
        
        int xPos = (int) ((getWidth() / 2) - (actualSize/2)) ; 
        int yPos = (int) ((getHeight() / 2)) ;
        
        canvas.drawText(txt2Draw, xPos, yPos, paint);
    }
 
 
}
