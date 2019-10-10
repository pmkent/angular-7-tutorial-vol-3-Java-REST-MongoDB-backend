package com.pmk.app.service;

import com.pmk.app.dao.IUserRepo;
import com.pmk.app.model.Credentials;
import com.pmk.app.model.User;
import com.pmk.app.util.PasswordUtil;
import com.pmk.app.util.TokenUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Objects;

public class LoginService {
    private IUserRepo iUserRepo;

    public LoginService(IUserRepo userRepo) {
        this.iUserRepo = userRepo;
    }

    public User authenticate(String userInfo, String urlInfo) {
        Credentials creds = extractCredentials(userInfo);
        User user = login(creds);
        if (user != null)
            user.setToken(TokenUtil.issueToken(Objects.requireNonNull(creds).getUsername(),urlInfo));
        else System.out.println("LoginSvc:authenticate Login "+userInfo+" not in the database!");

        return user;
    }

    public User login(Credentials creds) {
        String username = creds.getUsername(), passwordHash = PasswordUtil.getPasswordHash(creds.getPassword());
        return iUserRepo.findByUsernameAndPassword(username,passwordHash);
    } // end: login() method

    private static Credentials extractCredentials(String credentials) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(credentials);
            String username = (String) jsonObject.get("username");
            String password = (String) jsonObject.get("password");
            return new Credentials(username,password);
        } catch (ParseException pe) {
            System.out.println("\nLoginSvc:extractCredentials() ParseException >"+pe+"< Returning null \n");
            return null;
        }
    }
}
