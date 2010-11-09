package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Module extends Model {

    public String name;

    public String fullname;

    public String description;

    public String url;

    public String author;

    @OneToMany
    @Basic(fetch = FetchType.LAZY)
    public List<Version> versions;

    public Module() {
        this.versions = new ArrayList<Version>();
    }

}
