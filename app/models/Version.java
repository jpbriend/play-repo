package models;

import javax.persistence.Entity;

import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
public class Version extends Model {

    public String version;

    public boolean isDefault;

    public String matches;

    public Blob artefact;
}
