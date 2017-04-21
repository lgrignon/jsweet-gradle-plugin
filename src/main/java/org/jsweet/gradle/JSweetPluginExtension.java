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

import java.io.File;

import org.jsweet.transpiler.EcmaScriptComplianceLevel;
import org.jsweet.transpiler.ModuleKind;

/**
 * Plugin's configuration
 * 
 * @author Louis Grignon
 *
 */
public class JSweetPluginExtension {
	public EcmaScriptComplianceLevel getTargetVersion() {
		return targetVersion;
	}

	public void setTargetVersion(EcmaScriptComplianceLevel targetVersion) {
		this.targetVersion = targetVersion;
	}

	public ModuleKind getModule() {
		return module;
	}

	public void setModule(ModuleKind module) {
		this.module = module;
	}

	public File getOutDir() {
		return outDir;
	}

	public void setOutDir(File outDir) {
		this.outDir = outDir;
	}

	public File getTsOut() {
		return tsOut;
	}

	public void setTsOut(File tsOut) {
		this.tsOut = tsOut;
	}

	public boolean isTsOnly() {
		return tsOnly;
	}

	public void setTsOnly(boolean tsOnly) {
		this.tsOnly = tsOnly;
	}

	public String[] getIncludes() {
		return includes;
	}

	public void setIncludes(String[] includes) {
		this.includes = includes;
	}

	public String[] getExcludes() {
		return excludes;
	}

	public void setExcludes(String[] excludes) {
		this.excludes = excludes;
	}

	public boolean isBundle() {
		return bundle;
	}

	public void setBundle(boolean bundle) {
		this.bundle = bundle;
	}

	public boolean isSourceMap() {
		return sourceMap;
	}

	public void setSourceMap(boolean sourceMaps) {
		this.sourceMap = sourceMaps;
	}

	public File getSourceRoot() {
		return sourceRoot;
	}

	public void setSourceRoot(File sourceRoot) {
		this.sourceRoot = sourceRoot;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public boolean isNoRootDirectories() {
		return noRootDirectories;
	}

	public void setNoRootDirectories(boolean noRootDirectories) {
		this.noRootDirectories = noRootDirectories;
	}

	public boolean isEnableAssertions() {
		return enableAssertions;
	}

	public void setEnableAssertions(boolean enableAssertions) {
		this.enableAssertions = enableAssertions;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public File getJdkHome() {
		return jdkHome;
	}

	public void setJdkHome(File jdkHome) {
		this.jdkHome = jdkHome;
	}

	public boolean isDeclaration() {
		return declaration;
	}

	public void setDeclaration(boolean declaration) {
		this.declaration = declaration;
	}

	public File getDtsOut() {
		return dtsOut;
	}

	public void setDtsOut(File dtsOut) {
		this.dtsOut = dtsOut;
	}

	public File getCandiesJsOut() {
		return candiesJsOut;
	}

	public void setCandiesJsOut(File candiesJsOut) {
		this.candiesJsOut = candiesJsOut;
	}

	public boolean isDefinitions() {
		return definitions;
	}

	public void setDefinitions(boolean definitions) {
		this.definitions = definitions;
	}

	public boolean isDisableJavaAddons() {
		return disableJavaAddons;
	}

	public void setDisableJavaAddons(boolean disableJavaAddons) {
		this.disableJavaAddons = disableJavaAddons;
	}

	public String getFactoryClassName() {
		return factoryClassName;
	}

	public void setFactoryClassName(String factoryClassName) {
		this.factoryClassName = factoryClassName;
	}

	public boolean isIgnoreTypeScriptErrors() {
		return ignoreTypeScriptErrors;
	}

	public void setIgnoreTypeScriptErrors(boolean ignoreTypeScriptErrors) {
		this.ignoreTypeScriptErrors = ignoreTypeScriptErrors;
	}

	public File getHeader() {
		return header;
	}

	public void setHeader(File header) {
		this.header = header;
	}

	private EcmaScriptComplianceLevel targetVersion = EcmaScriptComplianceLevel.ES3;
	private ModuleKind module = ModuleKind.none;
	private File outDir = new File("js");
	private File tsOut = new File(".ts");
	private boolean tsOnly = false;
	private String[] includes = null;
	private String[] excludes = null;
	private boolean bundle = false;

	private boolean sourceMap = false;
	private File sourceRoot = null;
	private String encoding = "UTF-8";

	private boolean noRootDirectories = false;

	private boolean enableAssertions = false;

	private boolean verbose = false;

	private File jdkHome = null;

	private boolean declaration;
	private File dtsOut;
	private File candiesJsOut;
	private boolean definitions = true;
	private boolean disableJavaAddons = false;
	private String factoryClassName;

	protected boolean ignoreTypeScriptErrors;
	protected File header;
}
