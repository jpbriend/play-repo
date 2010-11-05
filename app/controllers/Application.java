package controllers;

import models.Module;
import models.Modules;
import models.Version;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
        Modules modules = new Modules();
        modules.modules = Module.findAll();
        renderJSON(modules);
    }

    public static void detail(String name) {
        Module module = Module.find("byName", name).first();
        renderJSON(module);
    }

    public static void download(String name, String version) {
        Version versionObject = Version.find("byVersion", version).first();
        notFoundIfNull(versionObject);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "-" + version + ".zip" + "\"");
        renderBinary(versionObject.artefact.get());
    }
}
