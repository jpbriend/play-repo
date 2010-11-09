package controllers;

import play.libs.OpenID;
import play.libs.OpenID.UserInfo;
import play.mvc.Before;
import play.mvc.Controller;

public class Security extends Controller {

    @Before(unless = {"login", "authenticate" })
    static void checkAuthenticated() {
        if (!session.contains("user")) {
            login();
        }
    }

    public static void index() {
        Application.index();
    }

    public static void login() {
        render();
    }

    public static void authenticate(String user) {
        if (OpenID.isAuthenticationResponse()) {
            UserInfo verifiedUser = OpenID.getVerifiedID();
            if (verifiedUser == null) {
                flash.error("Oops. Authentication has failed");
                login();
            }
            session.put("user", verifiedUser.id);
            index();
        } else {
            if (!OpenID.id(user).verify()) { // will redirect the user
                flash.error("Cannot verify your OpenID");
                login();
            }
        }
    }

}
