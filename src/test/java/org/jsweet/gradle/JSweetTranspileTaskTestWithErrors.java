package org.jsweet.gradle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JSweetTranspileTaskTestWithErrors {

	@TempDir
	File testProjectDir;

	/*
	 * file paths
	 */
	private static final String RESOURCES_PATH = "src/test/resources";
	private static final String TEST_MAIN_FOLDER = "transpile_test_with_errors";
	private static final String TEST_PROJECT = "test_project";
	private static final String JAVA_SRC = "java_src";
	private static final String JAVA_MAIN_PATH = "src/main/java";

	private static final String WORKING_FOLDER = ".jsweet";
	private static final Path TS_GENERATED_FOLDER = Paths.get(".generated_js", "ts");

	/*
	 * file names
	 */
	private static final String BUILD_GRADLE = "functionalTest1.gradle";
	private static final String SETTINGS_GRADLE = "settings.gradle";
	private static final String GRADLE_PROPERTIES = "gradle.properties";

	private Path resourceDir;

	@BeforeEach
	public void setup() throws IOException {

		resourceDir = Paths.get(RESOURCES_PATH, TEST_MAIN_FOLDER, TEST_PROJECT);

		// java source
		final Path javaFileDir = testProjectDir.toPath().resolve(JAVA_MAIN_PATH);
		Files.createDirectories(javaFileDir);
		FileUtils.copyDirectory(resourceDir.resolve(JAVA_SRC).toFile(), javaFileDir.toFile());

		Files.copy(resourceDir.resolve(SETTINGS_GRADLE), new File(testProjectDir, SETTINGS_GRADLE).toPath(),
				StandardCopyOption.REPLACE_EXISTING);
		Files.copy(resourceDir.resolve(BUILD_GRADLE), new File(testProjectDir, BUILD_GRADLE).toPath(),
				StandardCopyOption.REPLACE_EXISTING);
		Files.copy(resourceDir.resolve(GRADLE_PROPERTIES), new File(testProjectDir, GRADLE_PROPERTIES).toPath(),
				StandardCopyOption.REPLACE_EXISTING);

	}

	@Test
	void testTranspile() throws IOException {

		final Project testProject = ProjectBuilder.builder().withProjectDir(testProjectDir).build();
		testProject.getPlugins().apply(JavaPlugin.class);

		final JSweetPlugin plugin = new JSweetPlugin();

		plugin.apply(testProject);

		JSweetTranspileTask task = (JSweetTranspileTask) testProject.getTasks().getByName("jsweet");
		JSweetPluginExtension configuration = new JSweetPluginExtension();

		Path tsOutputDir = testProjectDir.toPath().resolve(TS_GENERATED_FOLDER);
		File workingDir = testProjectDir.toPath().resolve(WORKING_FOLDER).toFile();

		configuration.setVeryVerbose(true);
		configuration.setTsOut(tsOutputDir.toFile());
		configuration.setWorkingDir(workingDir);

		task.setConfiguration(configuration);
		RuntimeException e = assertThrows(RuntimeException.class, () -> task.transpile());

		assertEquals("transpilation failed with 6 error(s) and 0 warning(s)", e.getMessage());

	}

}
