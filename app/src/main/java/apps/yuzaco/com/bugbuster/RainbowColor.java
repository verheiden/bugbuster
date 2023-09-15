package apps.yuzaco.com.bugbuster;

import java.util.ArrayList;

import static apps.yuzaco.com.bugbuster.MozartApp.GetAppContext;

import apps.yuzaco.com.bugbuster.R;

/**
 * Created by jung on 10/30/15.
 */
public class RainbowColor {

        int color;
        int light;
        int lLight;
        int order;
        private static ArrayList<RainbowColor> colors = null;
        private static String[] colorNames;
        private static int[]   colorResources;

        public RainbowColor(int realColor, int lightColor, int lighter, int tOrder)
        {
            if ( colors == null ) {
                colors = new ArrayList<RainbowColor>();
                colorNames = GetAppContext().getResources().getStringArray(R.array.rainbow_colors);
                colorResources = new int[DrawTouch.FULL_RAINBOW_COLORS];


            }
            color = realColor;
            light = lightColor;
            lLight = lighter;
            order = tOrder;
            colors.add(this);
            colorResources[tOrder] = realColor;
        }
    public RainbowColor(int realColor, int lightColor, int lighter)
    {
        if ( colors == null ) {
            colors = new ArrayList<RainbowColor>();
            colorNames = GetAppContext().getResources().getStringArray(R.array.rainbow_colors);
            colorResources = new int[DrawTouch.FULL_RAINBOW_COLORS];
        }
        color = realColor;
        light = lightColor;
        lLight = lighter;
        order = -1;
    }
        public int getOrder() {return order; }
        public String colorName() { return colorNames[order]; }
        static public int MainColor(int tOrder ) {return colorResources[tOrder];}
        static public String ColorName(int tOrder) {return colorNames[tOrder];}
        public int getRealColor()
        {
            return color;
        }
        public int getShadowColor()
        {
            return light;
        }
        public int getlLight() {return lLight;}
        public static RainbowColor getColor (int index)
        {
            return colors.get(index%colors.size());
        }
}
