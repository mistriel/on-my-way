package net.mishna.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontTextView extends TextView
{
    public static Typeface FONT_NAME;

    public FontTextView(Context context)
    {
	super(context);
	if (FONT_NAME == null)
	{
	    FONT_NAME = Typeface.createFromAsset(context.getAssets(), "fonts/SILEOTSR.ttf");
	}
	this.setTypeface(FONT_NAME);
    }

    public FontTextView(Context context, AttributeSet attrs)
    {
	super(context, attrs);
	if (FONT_NAME == null)
	{
	    FONT_NAME = Typeface.createFromAsset(context.getAssets(), "fonts/SILEOTSR.ttf");
	}
	this.setTypeface(FONT_NAME);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyle)
    {
	super(context, attrs, defStyle);
	if (FONT_NAME == null)
	{
	    FONT_NAME = Typeface.createFromAsset(context.getAssets(), "fonts/SILEOTSR.ttf");
	}
	this.setTypeface(FONT_NAME);
    }

}
