package com.pmk.app.db;

import com.mongodb.MongoClient;
import com.pmk.app.dao.IUserRepo;
import com.pmk.app.model.User;
import com.pmk.app.util.AppUtil;
import com.pmk.app.util.PasswordUtil;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.util.Date;

public class UserDb extends AppUtil {
    private final Date TODAY = new Date();

    private MongoClient mongo = new MongoClient();
    private Jongo JONGO = new Jongo(mongo.getDB(getProperty("mongo.db.name")));

    /* Populate 3 Users */
    public void populateUserData() {

        // User One
        createUser(
                "userone@gmail.com", // username
                "User1", // firstname
                "M1", // middlename
                "One", // lastname
                "password", // password
                "MALE" // gender
                , new Date(getFormattedDt("1-25-2002").getTime())
        );

        // User Two
        createUser(
                "usertwo@gmail.com", // username
                "User2", // firstname
                "M2", // middlename
                "Two", // lastname
                "password", // password
                "FEMALE" // gender
                , new Date(getFormattedDt("12-25-1999").getTime())
        );

        // User Three
        createUser(
                "userthree@gmail.com", // username
                "User3", // firstname
                "M3", // middlename
                "Three", // lastname
                "userthree", // password
                "OTHER" // gender
                , new Date(getFormattedDt("8-15-1980").getTime())
        );
    }

    private void createUser(
        String username, String firstName, String middleName, String lastName, String password
        , String gender
        , Date dateOfBirth
    ) {
        User user = getUserRepo().findByUsername(username);

        if (user == null) {
            user = new User();
            user.setUserId(getMaxId("USER", getUserRepo()));
            user.setCreateDt(TODAY);
        }

        user.setUsername(username);
        user.setFirstName(firstName);
        user.setMiddleName(middleName);
        user.setLastName(lastName);
        user.setPassword(PasswordUtil.getPasswordHash(password));
        user.setGender(gender);
        user.setDateOfBirth(dateOfBirth);

        user.setUpdateDt(TODAY);
        user.setUpdateBy("ADMIN");

        getUserRepo().upsert(user);

        System.out.println("\nNew "+user+"");
    }

    private IUserRepo getUserRepo() {
        MongoCollection collection = JONGO.getCollection("user");
        collection.ensureIndex("{userId:1,username:1}", "{unique:true,dropDups: true}");
        return new IUserRepo(collection);
    }
}
