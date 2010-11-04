package controllers;

import java.util.List;

import models.Module;
import models.Version;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
        List<Module> modules = Module.findAll();
        renderJSON(modules);
    }

    public static void detail(String name) {
        Module module = Module.find("byName", name).first();
        renderJSON(module);
    }

    public static void download(String name, String version) {
        Version versionObject = Version.find("byVersion", version).first();

        response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "-" + version + ".zip" + "\"");
        renderBinary(versionObject.artefact.get());
    }
}
