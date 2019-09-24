package com.pmk.app;

import com.mongodb.MongoClient;
import com.pmk.app.dao.IUserRepo;
import com.pmk.app.service.LoginService;
import com.pmk.app.service.UserService;
import com.pmk.app.util.AppUtil;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener extends AppUtil implements ServletContextListener {
    private MongoClient MONGO_CLIENT;
    private Jongo JONGO;
    public AppContextListener() {
        MONGO_CLIENT = new MongoClient();
        JONGO = new Jongo(MONGO_CLIENT.getDB(getProperty("mongo.db.name")));
        System.out.println("\nAppContextListener constructor successful!! \n");
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ServletContext servletContext = servletContextEvent.getServletContext();
            servletContext.setAttribute("userService", new UserService(
                    getUserCollection()
            ));
            servletContext.setAttribute("loginService", new LoginService(getUserCollection()));
        } catch (Exception e) {
            System.out.println("\nAppContextListener initialization problems!! \n");
        }
    }

    private IUserRepo getUserCollection() {
        MongoCollection collection = JONGO.getCollection("user");
        collection.ensureIndex("{userId:1,username:1}", "{unique:true,dropDups: true}");
        return new IUserRepo(collection);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        MONGO_CLIENT.close();
    }
}
