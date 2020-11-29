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
import org.jsweet.transpiler.ModuleResolution;

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

	public ModuleResolution getModuleResolution() {
		return moduleResolution;
	}

	public void setModuleResolution(ModuleResolution moduleResolution) {
		this.moduleResolution = moduleResolution;
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

	public Boolean isTsOnly() {
		return tsOnly;
	}

	public void setTsOnly(Boolean tsOnly) {
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

	public Boolean isBundle() {
		return bundle;
	}

	public void setBundle(Boolean bundle) {
		this.bundle = bundle;
	}

	public Boolean isSourceMap() {
		return sourceMap;
	}

	public void setSourceMap(Boolean sourceMaps) {
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

	public Boolean isNoRootDirectories() {
		return noRootDirectories;
	}

	public void setNoRootDirectories(Boolean noRootDirectories) {
		this.noRootDirectories = noRootDirectories;
	}

	public Boolean isEnableAssertions() {
		return enableAssertions;
	}

	public void setEnableAssertions(Boolean enableAssertions) {
		this.enableAssertions = enableAssertions;
	}

	public Boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(Boolean verbose) {
		this.verbose = verbose;
	}

	public File getJdkHome() {
		return jdkHome;
	}

	public void setJdkHome(File jdkHome) {
		this.jdkHome = jdkHome;
	}

	public Boolean isDeclaration() {
		return declaration;
	}

	public void setDeclaration(Boolean declaration) {
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

	public Boolean isDefinitions() {
		return definitions;
	}

	public void setDefinitions(Boolean definitions) {
		this.definitions = definitions;
	}

	public Boolean isDisableSinglePrecisionFloats() {
		return disableSinglePrecisionFloats;
	}

	public void setDisableSinglePrecisionFloats(Boolean disableSinglePrecisionFloats) {
		this.disableSinglePrecisionFloats = disableSinglePrecisionFloats;
	}

	public String getFactoryClassName() {
		return factoryClassName;
	}

	public void setFactoryClassName(String factoryClassName) {
		this.factoryClassName = factoryClassName;
	}

	public Boolean isIgnoreTypeScriptErrors() {
		return ignoreTypeScriptErrors;
	}

	public void setIgnoreTypeScriptErrors(Boolean ignoreTypeScriptErrors) {
		this.ignoreTypeScriptErrors = ignoreTypeScriptErrors;
	}

	public File getHeader() {
		return header;
	}

	public void setHeader(File header) {
		this.header = header;
	}

	public String getExtraSystemPath() {
		return extraSystemPath;
	}

	public void setExtraSystemPath(String extraSystemPath) {
		this.extraSystemPath = extraSystemPath;
	}

	public Boolean isTsserver() {
		return tsserver;
	}

	public void setTsserver(Boolean tsserver) {
		this.tsserver = tsserver;
	}

	public Boolean isVeryVerbose() {
		return veryVerbose;
	}

	public void setVeryVerbose(Boolean veryVerbose) {
		this.veryVerbose = veryVerbose;
	}
	
	public File getWorkingDir() {
		return workingDir;
	}

	public void setWorkingDir(File workingDir) {
		this.workingDir = workingDir;
	}

	private EcmaScriptComplianceLevel targetVersion = EcmaScriptComplianceLevel.ES3;
	private ModuleKind module = ModuleKind.none;
	private ModuleResolution moduleResolution;
	private File outDir;
	private File tsOut;
	private Boolean tsOnly = false;
	private String[] includes = null;
	private String[] excludes = null;
	private Boolean bundle = false;

	private Boolean sourceMap = false;
	private File sourceRoot = null;
	private String encoding = "UTF-8";

	private Boolean noRootDirectories = false;

	private Boolean enableAssertions = false;

	private Boolean verbose = false;

	private File jdkHome = null;

	private Boolean declaration;
	private File dtsOut;
	private File candiesJsOut;
	private Boolean definitions = true;
	private Boolean disableSinglePrecisionFloats = false;
	private String factoryClassName;

	private String extraSystemPath;

	protected Boolean ignoreTypeScriptErrors;
	protected File header;

	protected File workingDir;
	
	private Boolean tsserver;
	private Boolean veryVerbose;
}
