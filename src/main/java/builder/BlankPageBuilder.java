package builder;

import util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sadiq on 16/04/19.
 * Used to construct data from data-provider read via csv
 */
public class BlankPageBuilder {

    public String title;
    public String content;


    /**
     * Random method is appended to the title value read from the csv as a hack to
     * execute on multiple browsers.
     * @param fillBlankPageDetails
     */
    public BlankPageBuilder(String[] fillBlankPageDetails){


        try {
            title = fillBlankPageDetails[0] + new Random().nextInt(10000);
            content = fillBlankPageDetails[1];
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }



    }



}
