import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;

@RunWith(Parameterized.class)
public class GenericDomainTest {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GenericDomainTest.class);

    private final Class<?> classToTest;

    public GenericDomainTest(Class<?> classToTest) {
        this.classToTest = classToTest;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        final Reflections reflections = locateAllClassesInDomainApi();

        final Collection<Object[]> classesToTest = Lists.newArrayList();
        for (Class<?> classToTest : reflections.getSubTypesOf(Object.class)) {
			if (!classToTest.isInterface() && !classToTest.isAnonymousClass()) {
				classesToTest.add(new Object[]{classToTest});
			}
		}

        return classesToTest;
    }

    private static Reflections locateAllClassesInDomainApi() {
        return new Reflections(new ConfigurationBuilder()
				.setScanners(new SubTypesScanner(false))
				.addUrls(ClasspathHelper.forPackage("com.telemis.bowling.domain.api"))
				.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("com.telemis.bowling.domain.api")).exclude(".*Test.*").exclude(".*Builder.*").exclude(".*Metadatas.*").exclude(".*Constants.*")));
	}

    @Test
    public void thatImplementsSerializable() throws Exception {
		LOGGER.info("Verifying implements Serializable contract for {} ", classToTest);
		Assert.assertTrue(classToTest.getSimpleName() + " should be serializable.", Serializable.class.isAssignableFrom(classToTest));
    }

    @Test
    public void thatOverrideToString() throws Exception {
		LOGGER.info("Verifying toString override for {} ", classToTest);
		Method toString = classToTest.getMethod("toString");
        Assert.assertTrue(classToTest.getSimpleName() + " should override toString().", toString.getDeclaringClass().equals(classToTest));
    }
}
