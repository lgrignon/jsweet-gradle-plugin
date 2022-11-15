package org.jsweet.gradle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.jsweet.transpiler.EcmaScriptComplianceLevel;
import org.jsweet.transpiler.ModuleKind;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JSweetTranspileTaskTestFilters {

	@TempDir
	File testProjectDir;

	/*
	 * file paths
	 */
	private static final String RESOURCES_PATH = "src/test/resources";
	private static final String TEST_MAIN_FOLDER = "transpile_test_filters";
	private static final String TEST_PROJECT = "test_project";
	private static final String JAVA_SRC = "java_src";
	private static final String JAVA_MAIN_PATH = "src/main/java";
	private static final String OUTPUT_FOLDER = "output";

	private static final String WORKING_FOLDER = ".jsweet";
	private static final Path JS_GENERATED_FOLDER = Paths.get(".generated_js", "js");
	private static final Path TS_GENERATED_FOLDER = Paths.get(".generated_js", "ts");
	private static final Path TS_BOOLEAN_CONDITION_GENERATED_FILE = Paths.get("def", "execcontext",
			"BooleanCondition.d.ts");
	private static final Path TS_EXECUTION_CONTEXT_GENERATED_FILE = Paths.get("def", "execcontext",
			"ExecutionContext.d.ts");

	/*
	 * file names
	 */
	private static final String BUILD_GRADLE = "functionalTest1.gradle";
	private static final String SETTINGS_GRADLE = "settings.gradle";
	private static final String GRADLE_PROPERTIES = "gradle.properties";

	private static final String JS_GENERATED_FILE = "IsTestCondition.js";
	private static final String TS_GENERATED_FILE = "IsTestCondition.ts";

	private Path resourceDir;
	private Path outputDir;

	@BeforeEach
	public void setup() throws IOException {

		resourceDir = Paths.get(RESOURCES_PATH, TEST_MAIN_FOLDER, TEST_PROJECT);
		outputDir = Paths.get(RESOURCES_PATH, TEST_MAIN_FOLDER, OUTPUT_FOLDER);

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

		Path jsOutputDir = testProjectDir.toPath().resolve(JS_GENERATED_FOLDER);
		Path tsOutputDir = testProjectDir.toPath().resolve(TS_GENERATED_FOLDER);
		File workingDir = testProjectDir.toPath().resolve(WORKING_FOLDER).toFile();

		configuration.setVerbose(true);
		configuration.setBundle(false);
		configuration.setDtsOut(null);
		configuration.setTargetVersion(EcmaScriptComplianceLevel.ES6);
		configuration.setEncoding(null);
		configuration.setDeclaration(false);
		configuration.setDefinitions(true);
		configuration.setTsOnly(false);
		configuration.setEnableAssertions(false);
		configuration.setIgnoreJavaFileNameError(false);
		configuration.setOutDir(jsOutputDir.toFile());
		configuration.setModule(ModuleKind.none);
		configuration.setNoRootDirectories(false);
		configuration.setTscWatchMode(false);
		configuration.setTsOut(tsOutputDir.toFile());
		configuration.setWorkingDir(workingDir);
		configuration.setIncludes(new String[] { "**/*.java" });

		task.setConfiguration(configuration);
		task.transpile();

		// working dir
		assertTrue(new File(testProjectDir, WORKING_FOLDER).exists(), "working dir not generated");

		// expected files
		// javacript
		File jsConditionFile = jsOutputDir.resolve(JS_GENERATED_FILE).toFile();
		assertTrue(jsConditionFile.exists(), "Javascript file not generated");

		// typescript IsTestCondition.ts
		File tsConditionFile = tsOutputDir.resolve(TS_GENERATED_FILE).toFile();
		assertTrue(tsConditionFile.exists(), "Typescript file not generated");

		// typescript BooleanCondition.d.ts
		File tsBooleanFile = tsOutputDir.resolve(TS_BOOLEAN_CONDITION_GENERATED_FILE).toFile();
		assertTrue(tsBooleanFile.exists(), "Typescript file not generated");

		// typescript ExecutionContext.d.ts
		File tsExecutionFile = tsOutputDir.resolve(TS_EXECUTION_CONTEXT_GENERATED_FILE).toFile();
		assertTrue(tsExecutionFile.exists(), "Typescript file not generated");

		// verify files content

		// IsTestCondition.js
		final String expectedJs = Files.readString(outputDir.resolve(JS_GENERATED_FILE));
		final String actualJs = Files.readString(jsConditionFile.toPath());
		assertEquals(expectedJs, actualJs);

		// IsTestCondition.ts
		final String expectedConditionTs = Files.readString(outputDir.resolve(TS_GENERATED_FILE));
		final String actualConditionTs = Files.readString(tsConditionFile.toPath());
		assertStringEquals(expectedConditionTs, actualConditionTs);

		// BooleanCondition.d.ts
		final String expectedBooleanTs = Files.readString(outputDir.resolve(TS_BOOLEAN_CONDITION_GENERATED_FILE));
		final String actualBooleanTs = Files.readString(tsBooleanFile.toPath());
		assertStringEquals(expectedBooleanTs, actualBooleanTs);

		// ExecutionContext.d.ts
		final String expectedExecutionTs = Files.readString(outputDir.resolve(TS_EXECUTION_CONTEXT_GENERATED_FILE));
		final String actualExecutionTs = Files.readString(tsExecutionFile.toPath());
		assertStringEquals(expectedExecutionTs, actualExecutionTs);
	}

	private void assertStringEquals(String expectedConditionTs, String actualConditionTs) {
		String a = expectedConditionTs.replace("\r\n", "\n");
		String b = actualConditionTs.replace("\r\n", "\n");
		assertEquals(a, b);
	}
}
