package Fonts;

import android.graphics.Typeface;

public class FontManager {
	public static Typeface mLight;
	public static Typeface mMedium;
	public static Typeface mBold;
	public static Typeface mUntralight;
	private static final FontManager INSTANCE = new FontManager();

	public FontManager getInstance() {

		return INSTANCE;
	}

	public void prepareFont(Typeface light, Typeface medium, Typeface bold,
			Typeface untralight) {
		INSTANCE.mLight = light;
		INSTANCE.mMedium = medium;
		INSTANCE.mBold = bold;
		INSTANCE.mUntralight = untralight;
	}
}
