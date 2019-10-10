package com.pmk.app.util;

import com.pmk.app.dao.IUserRepo;
import com.pmk.app.dao.Repository;
import com.pmk.app.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;

public class AppUtil {
    private Properties PROPS = new Properties();

    public String getProperty(String prop) {
        String filename = "config.properties";
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename)) {
            PROPS.load(inputStream);
            return PROPS.getProperty(prop);
        } catch (IOException ioe) {
            System.out.println("\nCould not load property "+prop+" from "+filename+"\n");
        }
        return null;
    }

    /**/
    protected Date getFormattedDt(String dtString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M-dd-yyyy");
        try {
            return simpleDateFormat.parse(dtString);
        } catch (ParseException pe) {
            System.out.println("\nAppUtil:getFormattedDate unable to parse date string >"+dtString+"<\n");
            return null;
        }
    }

    /**/
    @SuppressWarnings("unchecked")
    protected int getMaxId(String name, Repository<?> repo) {
        TreeSet<Integer> idSet = new TreeSet<>();
        switch (name) {
            case "USER": {
                List<User> users = (List<User>) repo.findAll();
                for (User user: users) idSet.add(user.getUserId());
            } break;
        }
        return getMaxIdFromSet(idSet);
    }

    /**/
    private static int getMaxIdFromSet(TreeSet<Integer> idSet) {
        if ((idSet == null) || (idSet.isEmpty())) {
            idSet = new TreeSet<>();
            idSet.add(0);
        }

        int maxId = 0, last = 0;
        for (int id: idSet) {
            if ((maxId == 0) && (id > maxId)) {
                if (id > (last + 1))
                    maxId = last + 1;
            }
            last = id;
        }

        if (maxId == 0)
            maxId = idSet.last() + 1;

        return maxId;
    }

    protected User getLoggedInUser(String token, IUserRepo iUsrRepo) {
        String email = TokenUtil.getUserEmailFromToken(token);
        return iUsrRepo.findByUsername(email);
    }

    protected Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
