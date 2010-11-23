package controllers;

import play.Play;
import play.libs.OpenID;
import play.libs.OpenID.UserInfo;
import play.mvc.Before;
import play.mvc.Controller;

public class Security extends Controller {

    @Before(unless = {"login", "authenticate", "logout" })
    public static void checkAuthenticated() {
        if (!session.contains("user")) {
            flash.error("You must be authenticated for this action.");
            login();
        }
    }

    public static void login() {
        render();
    }

    public static void authenticate(String user) {
        if (Play.id.equals("test")) {
            if ("test".equalsIgnoreCase(user)) {
                session.put("user", user);
                Application.index();
            } else {
                flash.error("Cannot verify your OpenID");
                login();
            }
        } else {
            if (OpenID.isAuthenticationResponse()) {
                UserInfo verifiedUser = OpenID.getVerifiedID();
                if (verifiedUser == null) {
                    flash.error("Oops. Authentication has failed");
                    login();
                }
                session.put("user", verifiedUser.id);
                Application.index();
            } else {
                if (!OpenID.id(user).verify()) { // will redirect the user
                    flash.error("Cannot verify your OpenID");
                    login();
                }
            }
        }
    }

    public static void logout() {
        session.remove("user");
        Application.index();
    }

}
