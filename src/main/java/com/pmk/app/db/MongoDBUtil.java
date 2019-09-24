package com.pmk.app.db;

import com.pmk.app.util.AppUtil;

public class MongoDBUtil extends AppUtil {
    private MongoDBUtil() {
    }

    public static void main(String[] args) {
        MongoDBUtil dbUtil = new MongoDBUtil();
        dbUtil.populateUserDb();
    }

    /**/
    private void populateUserDb() {
        UserDb usrDb = new UserDb();
        usrDb.populateUserData();
    }
}
