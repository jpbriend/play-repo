import org.junit.Before;
import org.junit.Test;

import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

public class ApplicationTest extends FunctionalTest {

    @Before
    public void setup() {
        Fixtures.deleteAll();

        Fixtures.load("data.yml");
    }

    /**
     * Test JSON is returned for /modules URL.
     */
    @Test
    public void testModules() {
        Request request = new Request();
        request.format = "json";
        Response response = GET(request, "/modules");
        assertIsOk(response);
        assertContentType("application/json", response);
        assertCharset("utf-8", response);
    }

    /**
     * Test JSON is returned for /modules/xxxx URL.
     */
    @Test
    public void testModule() {
        Request request = new Request();
        request.format = "json";
        Response response = GET(request, "/modules/testModule");
        assertIsOk(response);
        assertContentType("application/json", response);
        assertCharset("utf-8", response);
    }

    /**
     * Tests an invalid adress for /modules/xxxx(returns 404).
     */
    @Test
    public void testModuleNotFound() {
        Request request = new Request();
        request.format = "json";
        Response response = GET(request, "/modules/notFound");
        assertIsNotFound(response);
        assertContentType("application/json", response);
    }

}
