package com.pmk.app.service;

import com.pmk.app.dao.IUserRepo;
import com.pmk.app.model.User;
import com.pmk.app.util.AppUtil;
import com.pmk.app.util.PasswordUtil;
import com.pmk.app.util.TokenUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserService extends AppUtil {
    private IUserRepo iUserRepo;
    private final Date TODAY = new Date();

    public UserService(IUserRepo usrRepo) {
        this.iUserRepo = usrRepo;
    }

    public List<User> getUsers(String jwt) {
        String token = jwt.replaceAll("Bearer ", "");

        if (!TokenUtil.validate(token)) return null;
        return iUserRepo.findAll();
    }

    public User getUser(String id, String jwt) {
        String token = jwt.replaceAll("Bearer ", "");
        if (!TokenUtil.validate(token)) return null;
        return iUserRepo.findOne(id);
    }

    public boolean addUser(User user, String jwt) {
        String token = jwt.replaceAll("Bearer ", "");

        if (!TokenUtil.validate(token)) return false;

        if (iUserRepo.findByUsername(user.getUsername()) != null) {
            System.out.println("UsrSvc:addUser Duplicate username "+user.getUsername()+" in the database!");
            return false;
        } else {
            user.setUserId(getMaxId("USER", iUserRepo));
        }

        user.setUserId(getMaxId("ADDRESS", iUserRepo)); // 2019-9-30 TODO DELETE

        user.setCreateDt(TODAY);
        user.setUpdateDt(TODAY);

        User loggedInUsr = getLoggedInUser(token,iUserRepo);
        user.setUpdateBy(loggedInUsr.getUsername());

        user.setPassword(PasswordUtil.getPasswordHash(user.getPassword()));

        try {
            iUserRepo.upsert(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateUser(User user, String jwt) {
        String token = jwt.replaceAll("Bearer ", "");
        if (!TokenUtil.validate(token)) return false;

        user.setUpdateDt(TODAY);

        User loggedInUsr = getLoggedInUser(token,iUserRepo);
        user.setUpdateBy(loggedInUsr.getUsername());

        if (user.getPassword().length() < 20) // Don't update database with Hash unless the user just changed the password
            user.setPassword(PasswordUtil.getPasswordHash(user.getPassword()));

        try {
            iUserRepo.upsert(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteUser(String id, String jwt) {
        String token = jwt.replaceAll("Bearer ", "");
        if (!TokenUtil.validate(token)) return false;

        try {
            iUserRepo.delete(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<User> searchUsers(String term) {
        List<User> usrLst = new ArrayList<>();

        for (User user: iUserRepo.findAll()) {
            if (user.getUsername().toLowerCase().contains(term.toLowerCase()))
                usrLst.add(user);
        }
        return usrLst;
    }
}
