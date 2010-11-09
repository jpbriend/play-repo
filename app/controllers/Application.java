package controllers;

import models.Module;
import models.Modules;
import models.Version;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void modules() {
        Modules modules = new Modules();
        modules.modules = Module.findAll();
        if (request.format == "json") {
            renderJSON(modules);
        } else {
            render(modules);
        }
    }

    public static void module(String name) {
        Module module = Module.find("byName", name).first();
        notFoundIfNull(module);
        if (request.format == "json") {
            renderJSON(module);
        } else {
            render(module);
        }
    }

    public static void download(String name, String version) {
        Version versionObject = Version.find("byVersion", version).first();
        notFoundIfNull(versionObject);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "-" + version + ".zip" + "\"");
        renderBinary(versionObject.artefact.get());
    }
}
