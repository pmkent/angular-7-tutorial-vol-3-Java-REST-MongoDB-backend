package com.pmk.app.model;

import org.bson.types.ObjectId;

import java.util.Date;

public class DAOBean {
    private ObjectId _id;
    private Date createDt;
    private Date updateDt;
    private String updateBy;
    private Date deleteDt;

    public String getId() {
        if (_id == null) return null;
        else return _id.toString();
    }

    public void setId(String id) {
        if ((id == null) || (id.length() < 10)) this._id = null;
        else this._id = new ObjectId(id);
    }

    public Date getCreateDt() {
        return createDt;
    }
    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getUpdateDt() {
        return updateDt;
    }
    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    public String getUpdateBy() {
        return updateBy;
    }
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getDeleteDt() {
        return deleteDt;
    }
    public void setDeleteDt(Date deleteDt) {
        this.deleteDt = deleteDt;
    }
}
