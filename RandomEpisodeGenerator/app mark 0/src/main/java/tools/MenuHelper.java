package tools;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.TextView;

public class MenuHelper
{
	public static TextView getTextButton(String btn_title, Drawable btn_image, Context c)
	{
		TextView textBtn = new TextView(c);
		textBtn.setText(btn_title);
		textBtn.setTextColor(Color.WHITE);
		textBtn.setTextSize(18);
		textBtn.setTypeface(Typeface.create("sans-serif-light", Typeface.BOLD));
		textBtn.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		btn_image.setBounds(0, 0, 60, 60);
		textBtn.setCompoundDrawables(null, null, btn_image, null);
		// left,top,right,bottom. In this case icon is right to the text

		return textBtn;
	}
}
