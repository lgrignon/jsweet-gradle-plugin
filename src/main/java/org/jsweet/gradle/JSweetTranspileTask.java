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
import org.codehaus.plexus.util.DirectoryScanner;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.tasks.TaskAction;
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
        configureLogging();

        File jdkHome = configuration.getJdkHome();
        if (jdkHome == null) {
            jdkHome = new File(System.getProperty("java.home"));
        }

        File tsOutputDir = configuration.getTsOut();
        File jsOutputDir = configuration.getOutDir();

        File baseDirectory = new File(".").getAbsoluteFile();

        File workingDir = configuration.getWorkingDir();
        if (workingDir != null && !workingDir.isAbsolute()) {
            workingDir = new File(baseDirectory, workingDir.getPath());
        }

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
                            .loadClass(configuration.getFactoryClassName()).getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    try {
                        // try forName just in case
                        factory = (JSweetFactory) Class.forName(configuration.getFactoryClassName()).getDeclaredConstructor().newInstance();
                    } catch (Exception e2) {
                        throw new Exception("cannot find or instantiate factory class: "
                                + configuration.getFactoryClassName()
                                + " (make sure the class is in the plugin's classpath and that it defines an empty public constructor)",
                                e2);
                    }
                }
            }
            if (factory == null) {
                factory = new JSweetFactory();
            }

            SourceFile[] sourceFiles = collectSourceFiles();

            try (JSweetTranspiler transpiler = new JSweetTranspiler(baseDirectory, null, factory, workingDir,
                    tsOutputDir, jsOutputDir, configuration.getCandiesJsOut(), classpath.getAsPath())) {

                transpiler.setTscWatchMode(false);

                if (configuration.isTsserver() != null) {
                    transpiler.setUseTsserver(configuration.isTsserver());
                }
                if (configuration.getTargetVersion() != null) {
                    transpiler.setEcmaTargetVersion(configuration.getTargetVersion());
                }
                if (configuration.getModule() != null) {
                    transpiler.setModuleKind(configuration.getModule());
                }
                if (configuration.getModuleResolution() != null) {
                    transpiler.setModuleResolution(configuration.getModuleResolution());
                }
                if (configuration.isBundle() != null) {
                    transpiler.setBundle(configuration.isBundle());
                }
                if (configuration.isSourceMap() != null) {
                    transpiler.setGenerateSourceMaps(configuration.isSourceMap());
                }
                if (configuration.getSourceRoot() != null) {
                    transpiler.setSourceRoot(configuration.getSourceRoot());
                }
                if (configuration.getEncoding() != null) {
                    transpiler.setEncoding(configuration.getEncoding());
                }
                if (configuration.isNoRootDirectories() != null) {
                    transpiler.setNoRootDirectories(configuration.isNoRootDirectories());
                }
                if (configuration.isEnableAssertions() != null) {
                    transpiler.setIgnoreAssertions(!configuration.isEnableAssertions());
                }
                if (configuration.isDeclaration() != null) {
                    transpiler.setGenerateDeclarations(configuration.isDeclaration());
                }
                if (configuration.getDtsOut() != null) {
                    transpiler.setDeclarationsOutputDir(configuration.getDtsOut());
                }
                if (configuration.isDefinitions() != null) {
                    transpiler.setGenerateDefinitions(configuration.isDefinitions());
                }
                if (configuration.isTsOnly() != null) {
                    transpiler.setGenerateJsFiles(!configuration.isTsOnly());
                }
                if (configuration.isIgnoreTypeScriptErrors() != null) {
                    transpiler.setIgnoreTypeScriptErrors(configuration.isIgnoreTypeScriptErrors());
                }
                if (configuration.getHeader() != null) {
                    transpiler.setHeaderFile(configuration.getHeader());
                }
                if (configuration.isDisableSinglePrecisionFloats() != null) {
                    transpiler.setDisableSinglePrecisionFloats(configuration.isDisableSinglePrecisionFloats());
                }

                transpiler.transpile(transpilationHandler, sourceFiles);
            }
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
