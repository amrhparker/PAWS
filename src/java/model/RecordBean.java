/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Acer
 */
import java.io.Serializable;
import java.sql.Date;
public class RecordBean implements Serializable {
    
    private int recordId;
    private int appId;
    private ApplicationBean application;
    private Date recordDate;
    public RecordBean() {
    }

    public RecordBean(int recordId, int appId, Date recordDate) {
        this.recordId = recordId;
        this.appId = appId;
        this.recordDate = recordDate;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public ApplicationBean getApplication() {
        return application;
    }

    public void setApplication(ApplicationBean application) {
        this.application = application;
        if (application != null) {
            this.appId = application.getAppId(); // keep FK in sync
        }
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }
}
