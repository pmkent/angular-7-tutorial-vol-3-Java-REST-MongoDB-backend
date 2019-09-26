package com.pmk.app.dao;

import com.pmk.app.model.User;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;

import static org.jongo.Oid.withOid;

public class IUserRepo implements Repository<User> {

    private MongoCollection usrColl;
    public IUserRepo(MongoCollection usrColl) {
        this.usrColl = usrColl;
    }

    @Override
    public List<User> findAll() {
        MongoCursor<User> all = usrColl.find().sort("{username : 1}").as(User.class);
        List<User> users = new ArrayList<>();
        while (all.hasNext()) users.add(all.next());
        return users;
    }

    @Override
    public User findOne(String id) {
        if (id == null) return null;
        return  usrColl.findOne(withOid(id)).as(User.class);
    }

    @Override
    public void upsert(User object) {
        String query = "{username : '"+object.getUsername()+"'}";
        User user = usrColl.findOne(query).as(User.class);
        if (user == null) usrColl.save(object);
        else usrColl.update(query).with(object);
    }

    @Override
    public void delete(String id) {
        usrColl.remove(new ObjectId(id));
    }

    public User findByUsernameAndPassword(String username, String password) {
        return usrColl.findOne("{username : '"+username+"', password : '"+password+"'}").as(User.class);
    }

    public User findByUsername(String username) {
        return usrColl.findOne("{username : '"+username+"'}").as(User.class);
    }
}
