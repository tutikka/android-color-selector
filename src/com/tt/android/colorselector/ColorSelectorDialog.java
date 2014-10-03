package com.tt.android.colorselector;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ColorSelectorDialog extends Dialog implements android.view.View.OnClickListener, OnSeekBarChangeListener {

	private static final String TAG_CANCEL = "cancel";
	
	private static final String TAG_SELECT = "select";
	
	private static final int PREVIEW_WIDTH= 256;
	
	private static final int PREVIEW_HEIGHT = 256;
	
	private ImageView preview;
	
	private TextView hex;
	
	private SeekBar alpha;
	
	private SeekBar red;
	
	private SeekBar green;
	
	private SeekBar blue;
	
	private Button cancel;
	
	private Button select;
	
	private List<ColorSelectorListener> listeners = new ArrayList<ColorSelectorListener>();
	
	private int a, r, g, b;
	
	public static class Builder {
		
		private Context context;
		
		private int color = Color.argb(128, 0, 128, 0);
		
		public Builder color(int color) {
			this.color = color;
			return (this);
		}
		
		public Builder(Context context) {
			this.context = context;
		}
		
		public ColorSelectorDialog build() throws IllegalArgumentException {
			return (new ColorSelectorDialog(context, color));
		}
		
	}
	
	private ColorSelectorDialog(Context context, int color) throws IllegalArgumentException {
		super(context);
		
		setTitle(R.string.color_selector_dialog_title);
		setContentView(R.layout.color_selector_dialog);
		
		a = Color.alpha(color);
		r = Color.red(color);
		g = Color.green(color);
		b = Color.blue(color);
		
		preview = (ImageView) findViewById(R.id.color_selector_preview);
		preview.setImageBitmap(generatePreview(PREVIEW_WIDTH, PREVIEW_HEIGHT, color));
		
		hex = (TextView) findViewById(R.id.color_selector_hex);
		hex.setText(generateHex());
		
		alpha = (SeekBar) findViewById(R.id.color_selector_alpha);
		alpha.setTag("alpha");
		alpha.setProgress(a);
		alpha.setOnSeekBarChangeListener(this);
		
		red = (SeekBar) findViewById(R.id.color_selector_red);
		red.setTag("red");
		red.setProgress(r);
		red.setOnSeekBarChangeListener(this);
		
		green = (SeekBar) findViewById(R.id.color_selector_green);
		green.setTag("green");
		green.setProgress(g);
		green.setOnSeekBarChangeListener(this);
		
		blue = (SeekBar) findViewById(R.id.color_selector_blue);
		blue.setTag("blue");
		blue.setProgress(b);
		blue.setOnSeekBarChangeListener(this);
		
		cancel = (Button) findViewById(R.id.color_selector_cancel);
		cancel.setTag(TAG_CANCEL);
		cancel.setOnClickListener(this);
		
		select = (Button) findViewById(R.id.color_selector_select);
		select.setTag(TAG_SELECT);
		select.setOnClickListener(this);
	}
	
	public void addColorSelectorListener(ColorSelectorListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	
	public void removeColorSelectorListener(ColorSelectorListener listener) {
		listeners.remove(listener);
	}

	private String generateHex() {
		StringBuilder sb = new StringBuilder();
		sb.append("#");
		String a = Integer.toHexString(this.a);
		if (a.length() == 1) {
			sb.append("0");
		}
		sb.append(a);
		String r = Integer.toHexString(this.r);
		if (r.length() == 1) {
			sb.append("0");
		}
		sb.append(r);
		String g = Integer.toHexString(this.g);
		if (g.length() == 1) {
			sb.append("0");
		}
		sb.append(g);
		String b = Integer.toHexString(this.b);
		if (b.length() == 1) {
			sb.append("0");
		}
		sb.append(b);
		return (sb.toString());
	}
	
	private Bitmap generatePreview(int width, int height, int color) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
    	Paint p = new Paint();
    	for (int x = 0; x < 8; x++) {
    		for (int y = 0; y < 8; y++) {
    			if (y % 2 == 0) {
    				if (x % 2 == 0) { 
    					p.setColor(Color.BLACK);
    				} else {
    					p.setColor(Color.GRAY);
    				}
    			} else {
    				if (x % 2 == 0) { 
    					p.setColor(Color.GRAY);
    				} else {
    					p.setColor(Color.BLACK);
    				}
    			}
    			canvas.drawRect(x * width / 8, y * width / 8, (x + 1) * width / 8, (y + 1) * width / 8, p);
    		}
    	}
    	canvas.drawColor(color);
        return (bitmap);
	}
	
	@Override
	public void onClick(View view) {
		if (TAG_CANCEL.equals(view.getTag())) {
			cancel();
		}
		if (TAG_SELECT.equals(view.getTag())) {
			for (ColorSelectorListener listener : listeners) {
				listener.onColorSelected(Color.argb(a, r, g, b));
			}
			cancel();
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if ("alpha".equals(seekBar.getTag())) {
			a = progress;
		}
		if ("red".equals(seekBar.getTag())) {
			r = progress;
		}
		if ("green".equals(seekBar.getTag())) {
			g = progress;
		}
		if ("blue".equals(seekBar.getTag())) {
			b = progress;
		}
		preview.setImageBitmap(generatePreview(PREVIEW_WIDTH, PREVIEW_HEIGHT, Color.argb(a, r, g, b)));
		hex.setText(generateHex());
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

}
