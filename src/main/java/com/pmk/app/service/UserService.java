package com.pmk.app.service;

import com.pmk.app.dao.IUserRepo;
import com.pmk.app.model.User;
import com.pmk.app.util.AppUtil;
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

    /**/
    public List<User> getUsers(String jwt) {
        String token = jwt.replaceAll("Bearer ", "");
        if (!TokenUtil.verify(token,getLoggedInUser(jwt,iUserRepo).getUsername())) return null;
        return iUserRepo.findAll();
    }

    /**/
    public User getUser(String id, String jwt) {
        //System.out.println("\nUserSvc:getUser id >"+id+"<\n"); // System.out.println("\nUserSvc:getUser jwt "+jwt+"\n");
        String token = jwt.replaceAll("Bearer ", "");

        if (!TokenUtil.validate(token)) return null;

        return iUserRepo.findOne(id);
    }

    /**/
    public boolean addUser(User user, String jwt) {
        //System.out.println("\nUserSvc:addUser\n"); // System.out.println("\nUserSvc:addUser jwt "+jwt+"\n");
        String token = jwt.replaceAll("Bearer ", "");

        if (!TokenUtil.validate(token)) return false;

        if (iUserRepo.findByUsername(user.getUsername()) != null) {
            System.out.println("UserSvc:addUser OOPS! Username : "+user.getUsername()+"< Exists in the database!!");
            return false;
        } else {
            user.setUserId(getMaxId("USER", iUserRepo));
            System.out.println("UserSvc:addUser adding NEW user, username: "+user.getUsername());
        }

        user.setUserId(getMaxId("USER",iUserRepo));

        user.setCreateDt(TODAY);
        user.setUpdateDt(TODAY);

        User loggedInUsr = getLoggedInUser(jwt,iUserRepo);
        user.setUpdateBy(loggedInUsr.getUsername());

        try {
            iUserRepo.upsert(user);
            System.out.println("UserSvc:addUser DONE adding user : "+user);
            return true;
        } catch (Exception e) {
            System.out.println("UserSvc:addUser ERROR! adding user : "+user);
            return false;
        }
    }

    /**/
    public boolean updateUser(User user, String jwt) {
        //System.out.println("\nUserSvc:updateUser user >"+user+"<\n"); // System.out.println("\nUserSvc:updateUser jwt "+jwt+"\n");
        String token = jwt.replaceAll("Bearer ", "");

        if (!TokenUtil.validate(token)) return false;

        System.out.println("UserSvc:updateUser : username " + user.getUsername());

        user.setUpdateDt(TODAY);

        User loggedInUsr = getLoggedInUser(jwt,iUserRepo);
        user.setUpdateBy(loggedInUsr.getUsername());

        try {
            iUserRepo.upsert(user);
            System.out.println("UserSvc:updateUser DONE updating user : "+user);
            return true;
        } catch (Exception e) {
            System.out.println("UserSvc:updateUser ERROR! updating user : "+user);
            return false;
        }
    }

    public boolean deleteUser(String id, String jwt) {
        //System.out.println("\nUsrSvc:deleteUser\n"); // System.out.println("\nUserSvc:deleteUser jwt "+jwt+"\n");
        String token = jwt.replaceAll("Bearer ", "");

        if (!TokenUtil.validate(token)) return false;

        try {
            iUserRepo.delete(id);
            System.out.println("UsrSvc:deleteUser DONE updating user id : "+id);
            return true;
        } catch (Exception e) {
            System.out.println("UsrSvc:deleteUser ERROR! updating user id : "+id);
            return false;
        }
    }

    public List<User> searchUsers(String term) {
        System.out.println("UsrSvc:searchHeroes() term : "+term);
        List<User> usrLst = new ArrayList<>();

        for (User user: iUserRepo.findAll()) {
            if (user.getUsername().toLowerCase().contains(term.toLowerCase()))
                usrLst.add(user);
        }
        return usrLst;
    } // end: searchUsers() method
}
