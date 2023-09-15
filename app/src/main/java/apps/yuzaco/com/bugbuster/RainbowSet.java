package apps.yuzaco.com.bugbuster;

import java.util.ArrayList;

/**
 * Created by jung on 9/20/16.
 */

public class RainbowSet<Butterfly> extends ArrayList<Butterfly> {
    private int numShows = 0;

    public RainbowSet()
    {
        super();
        numShows = 0;
    }
    public int getNumShows()
    {
        return numShows;
    }

    public void setNumShows(int number){
        numShows = number;
    }
}
