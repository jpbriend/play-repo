import java.util.List;

import models.Module;
import models.Version;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class BasicTest extends UnitTest {

    @Before
    public void setup() {
        Fixtures.deleteAll();
    }

    @Test
    public void createModule() {
        Module module = new Module();
        module.name = "testModule";
        module.fullname = "Test Module";
        module.author = "Tester";
        module.description = "Description for Test Module";
        module.url = "http://test";
        module.save();

        Module result = Module.find("byName", "testModule").first();

        assertNotNull(result);
        assertEquals(module, result);
    }

    @Test
    public void createVersion() {
        Version version = new Version();
        version.version = "TestVersion";
        version.isDefault = true;
        version.matches = "1.1";
        version.save();

        Version result = Version.find("byVersion", "TestVersion").first();

        assertNotNull(result);
        assertEquals(version, result);
    }

    @Test
    public void createModuleAndVersion() {
        Module module = new Module();
        module.name = "testModule";
        module.fullname = "Test Module";
        module.author = "Tester";
        module.description = "Description for Test Module";
        module.url = "http://test";
        module.save();

        Version version = new Version();
        version.version = "TestVersion";
        version.isDefault = true;
        version.matches = "1.1";
        version.save();

        module.versions.add(version);
        module.save();

        List<Module> result = Module.find("byName", "testModule").fetch();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(module, result.get(0));
        assertEquals(version, result.get(0).versions.get(0));
    }

}
