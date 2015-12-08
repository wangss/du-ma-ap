package com.taoren.app.test.userBatch;

import com.taoren.app.util.http.DefaultHttpClient;
import com.taoren.common.constant.Constants;
import com.taoren.common.model.FileItem;
import com.taoren.common.util.TaorenUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by king on 5/28/15.
 */
public class NewPhoneBatch {
    public static void main(String[] args) throws Exception{

        double latitude = 22.625129;
        double longitude = 114.028259;

        long now = System.currentTimeMillis();

        DateFormat dateFormat= new SimpleDateFormat(Constants.DATE_FORMAT);


        for(long l=16200015202L; l<16200016202L; l++){
            latitude+=0.00001D;
            longitude+=0.00001D;
            now -= 86400;
            System.out.println(l + "doneA");
            NewUser newUser = new NewUser(l+"", "我是"+l,dateFormat.format(new Date(now)), latitude, longitude);
            Thread t = new Thread(newUser);
            t.start();
//            newUser.run();
            System.out.println(l + "doneB");
        }

        System.out.println("time is :" + (System.currentTimeMillis() - now)/1000);

    }
}
