package org.dmetasoul.lakesoul;

import java.util.Date;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName User
 * @Description TODO
 * @createTime 2023/7/31 18:36
 */
public class LakeSoulUser {
    private int Id;
    private String Name;
    private String Password;

    private String License;

    private String ownerId;
    private Date CreatedAt;
    private Date UpdatedAt;

    public LakeSoulUser() {
    }

    public LakeSoulUser(int id, String name, String password, String license, String ownerId, Date createdAt, Date updatedAt) {
        Id = id;
        Name = name;
        Password = password;
        License = license;
        this.ownerId = ownerId;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getLicense() {
        return License;
    }

    public void setLicense(String license) {
        License = license;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        UpdatedAt = updatedAt;
    }
}
