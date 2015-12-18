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

	public File getBundlesDirectory() {
		return bundlesDirectory;
	}

	public void setBundlesDirectory(File bundlesDirectory) {
		this.bundlesDirectory = bundlesDirectory;
	}

	public boolean isSourceMap() {
		return sourceMap;
	}

	public void setSourceMap(boolean sourceMaps) {
		this.sourceMap = sourceMaps;
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

	public String getJdkHome() {
		return jdkHome;
	}

	public void setJdkHome(String jdkHome) {
		this.jdkHome = jdkHome;
	}

	private EcmaScriptComplianceLevel targetVersion = EcmaScriptComplianceLevel.ES3;
	private ModuleKind module = ModuleKind.none;
	private File outDir = null;
	private File tsOut = null;
	private String[] includes = null;
	private String[] excludes = null;
	private boolean bundle = false;
	private File bundlesDirectory = null;

	private boolean sourceMap = false;
	private String encoding = "UTF-8";

	private boolean noRootDirectories = false;

	private boolean enableAssertions = false;

	private boolean verbose = false;

	private String jdkHome = null;

}
