package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import models.Module;
import models.Modules;
import models.Version;
import play.Logger;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.db.jpa.Blob;
import play.mvc.Controller;
import play.mvc.With;

@With(Security.class)
public class Admin extends Controller {
    public static void index() {
        Modules modules = new Modules();
        modules.modules = Module.findAll();
        Logger.info("Found " + modules.modules.size() + " modules.");
        render(modules);
    }

    public static void show(Long id) {
        Module module = Module.findById(id);
        notFoundIfNull(module);
        render(module);
    }

    public static void add(String name, String fullname) {
        Module m = Module.find("byName", name).first();
        if (m != null) {
            Validation.addError(name, "Module '%s' already exists.");
            Validation.keep();
        } else {
            m = new Module();
            m.name = name;
            m.fullname = fullname;
            m.save();
        }
        index();
    }

    public static void delete(Long id) {
        Module m = Module.findById(id);
        notFoundIfNull(m);
        m.delete();
        index();
    }

    public static void addVersion(Long id, Version version, @Required File artefact) {
        notFoundIfNull(id);

        if (Validation.hasErrors()) {
            Logger.error("Error %s", validation.errorsMap());
            Validation.keep();
        } else {

            Version v = Version.find("byVersion", version.version).first();
            if (v != null) {
                Validation.addError(version.version, "Version '%s' already exists.");
                Validation.keep();
            } else {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(artefact);
                    version.artefact = new Blob();
                    version.artefact.set(fis, "application/zip");
                } catch (FileNotFoundException e) {
                    Logger.error("Error while retrieving attachment :" + e.getMessage(), e);
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            Logger.error("Error while retrieving attachment :" + e.getMessage(), e);
                        }
                    }
                }

                Module m = Module.findById(id);
                m.versions.add(version);
                version.save();
                m.save();
            }
        }
        show(id);
    }

    public static void download(Long idModule, Long idVersion) {
        Module m = Module.findById(idModule);
        Version v = Version.findById(idVersion);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + m.name + "-" + v.version + ".zip" + "\"");
        renderBinary(v.artefact.get());
    }
}
