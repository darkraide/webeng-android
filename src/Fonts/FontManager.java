package Fonts;

import android.graphics.Typeface;

public class FontManager {
    public Typeface mLight;
    public Typeface mMedium;
    public Typeface mBold;
    public Typeface mUntralight;
    private static final FontManager INSTANCE = new FontManager();

    public static FontManager getInstance() {

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
