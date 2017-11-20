/* 
 * Copyright (C) 2015 Louis Grignon <louis.grignon@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jsweet.gradle;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.codehaus.plexus.util.DirectoryScanner;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.TaskAction;
import org.jsweet.JSweetConfig;
import org.jsweet.transpiler.JSweetFactory;
import org.jsweet.transpiler.JSweetProblem;
import org.jsweet.transpiler.JSweetTranspiler;
import org.jsweet.transpiler.SourceFile;
import org.jsweet.transpiler.util.ConsoleTranspilationHandler;
import org.jsweet.transpiler.util.ErrorCountTranspilationHandler;
import org.jsweet.transpiler.util.ProcessUtil;

/**
 * JSweet transpilation task
 * 
 * @author Louis Grignon
 *
 */
public class JSweetTranspileTask extends AbstractJSweetTask {

	private SourceDirectorySet sources;
	private FileCollection classpath;

	@TaskAction
	protected void transpile() {
		if (configuration.isVerbose()) {
			LogManager.getLogger("org.jsweet").setLevel(Level.ALL);
		}

		File jdkHome = configuration.getJdkHome();
		if (jdkHome == null) {
			jdkHome = new File(System.getProperty("java.home"));
		}

		JSweetConfig.initClassPath(jdkHome.getAbsolutePath());

		File tsOutputDir = configuration.getTsOut();
		if (tsOutputDir == null) {
			tsOutputDir = new File("target/.ts");
		}
		tsOutputDir.mkdirs();

		File jsOutputDir = configuration.getOutDir();
		if (jsOutputDir == null) {
			jsOutputDir = new File("target/js");
		}
		jsOutputDir.mkdirs();

		ErrorCountTranspilationHandler transpilationHandler = new ErrorCountTranspilationHandler(
				new ConsoleTranspilationHandler());
		try {

			logInfo("extraSystemPath: " + configuration.getExtraSystemPath());
			if (isNotBlank(configuration.getExtraSystemPath())) {
				ProcessUtil.addExtraPath(configuration.getExtraSystemPath());
			}
			
			logInfo("encoding: " + configuration.getEncoding());
			logInfo("classpath: " + classpath.getFiles());
			logInfo("ts output dir: " + tsOutputDir);
			logInfo("js output dir: " + jsOutputDir);

			logInfo("ts only: " + configuration.isTsOnly());
			logInfo("declarations: " + configuration.isDeclaration());
			logInfo("declarationOutDir: " + configuration.getDtsOut());
			logInfo("candiesJsOutDir: " + configuration.getCandiesJsOut());

			logInfo("bundle: " + configuration.isBundle());
			logInfo("ecmaTargetVersion: " + configuration.getTargetVersion());
			logInfo("moduleKind: " + configuration.getModule());
			logInfo("sourceMaps: " + configuration.isSourceMap());
			logInfo("SourceRoot: " + configuration.getSourceRoot());
			logInfo("jdkHome: " + jdkHome);
			logInfo("definitions: " + configuration.isDefinitions());
			logInfo("disableSinglePrecisionFloats: " + configuration.isDisableSinglePrecisionFloats());
			logInfo("factoryClassName: " + configuration.getFactoryClassName());

			JSweetFactory factory = null;
			if (configuration.getFactoryClassName() != null) {
				try {
					factory = (JSweetFactory) Thread.currentThread().getContextClassLoader()
							.loadClass(configuration.getFactoryClassName()).newInstance();
				} catch (Exception e) {
					try {
						// try forName just in case
						factory = (JSweetFactory) Class.forName(configuration.getFactoryClassName()).newInstance();
					} catch (Exception e2) {
						throw new Exception(
								"cannot find or instantiate factory class: " + configuration.getFactoryClassName()
										+ " (make sure the class is in the plugin's classpath and that it defines an empty public constructor)",
								e2);
					}
				}
			}
			if (factory == null) {
				factory = new JSweetFactory();
			}

			SourceFile[] sourceFiles = collectSourceFiles();

			JSweetTranspiler transpiler = new JSweetTranspiler(factory, tsOutputDir, jsOutputDir,
					configuration.getCandiesJsOut(), classpath.getAsPath());

			transpiler.setTscWatchMode(false);
			transpiler.setEcmaTargetVersion(configuration.getTargetVersion());
			transpiler.setModuleKind(configuration.getModule());
			transpiler.setBundle(configuration.isBundle());
			transpiler.setPreserveSourceLineNumbers(configuration.isSourceMap());
			if (configuration.getSourceRoot() != null) {
				transpiler.setSourceRoot(configuration.getSourceRoot());
			}

			transpiler.setEncoding(configuration.getEncoding());
			transpiler.setNoRootDirectories(configuration.isNoRootDirectories());
			transpiler.setIgnoreAssertions(!configuration.isEnableAssertions());

			transpiler.setGenerateDeclarations(configuration.isDeclaration());
			transpiler.setDeclarationsOutputDir(configuration.getDtsOut());
			transpiler.setGenerateDefinitions(configuration.isDefinitions());
			
			transpiler.setGenerateJsFiles(!configuration.isTsOnly());
			transpiler.setIgnoreTypeScriptErrors(configuration.isIgnoreTypeScriptErrors());
			transpiler.setHeaderFile(configuration.getHeader());

			transpiler.setDisableSinglePrecisionFloats(configuration.isDisableSinglePrecisionFloats());
			
			transpiler.transpile(transpilationHandler, sourceFiles);
		} catch (NoClassDefFoundError e) {
			if (configuration.isVerbose()) {
				logger.error("cannot transpile (probably not a valid JDK)", e);
			}

			transpilationHandler.report(JSweetProblem.JAVA_COMPILER_NOT_FOUND, null,
					JSweetProblem.JAVA_COMPILER_NOT_FOUND.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		int errorCount = transpilationHandler.getErrorCount();
		if (errorCount > 0) {
			throw new RuntimeException("transpilation failed with " + errorCount + " error(s) and "
					+ transpilationHandler.getWarningCount() + " warning(s)");
		} else {
			if (transpilationHandler.getWarningCount() > 0) {
				logInfo("transpilation completed with " + transpilationHandler.getWarningCount() + " warning(s)");
			} else {
				logInfo("transpilation successfully completed with no errors and no warnings");
			}
		}
	}

	private SourceFile[] collectSourceFiles() {

		logInfo("source includes: " + ArrayUtils.toString(configuration.getIncludes()));
		logInfo("source excludes: " + ArrayUtils.toString(configuration.getExcludes()));

		Collection<File> sourceDirs = sources.getSrcDirs();

		logInfo("sources dirs: " + sourceDirs);

		List<SourceFile> sources = new LinkedList<>();
		for (File sourceDir : sourceDirs) {
			DirectoryScanner dirScanner = new DirectoryScanner();
			dirScanner.setBasedir(sourceDir);
			dirScanner.setIncludes(configuration.getIncludes());
			dirScanner.setExcludes(configuration.getExcludes());
			dirScanner.scan();

			for (String includedPath : dirScanner.getIncludedFiles()) {
				if (includedPath.endsWith(".java")) {
					sources.add(new SourceFile(new File(sourceDir, includedPath)));
				}
			}
		}
		logInfo("sourceFiles: " + sources);

		return sources.toArray(new SourceFile[0]);
	}

	public SourceDirectorySet getSources() {
		return sources;
	}

	public void setSources(SourceDirectorySet sources) {
		this.sources = sources;
	}

	public FileCollection getClasspath() {
		return classpath;
	}

	public void setClasspath(FileCollection classpath) {
		this.classpath = classpath;
	}
}
