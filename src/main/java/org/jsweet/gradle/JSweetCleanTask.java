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

import org.apache.commons.io.FileUtils;
import org.gradle.api.tasks.TaskAction;

/**
 * JSweet clean task
 * 
 * @author Louis Grignon
 *
 */
public class JSweetCleanTask extends AbstractJSweetTask {

	private void clean(File directory) {
		logInfo("cleaning: " + directory);
		FileUtils.deleteQuietly(directory);
	}
	
	@TaskAction
	protected void clean() {
		configureLogging();

		File tsOutputDir = configuration.getTsOut();
		if (tsOutputDir != null) {
			clean(tsOutputDir);
		}
		File jsOutputDir = configuration.getOutDir();
		if (jsOutputDir != null) {
			clean(jsOutputDir);
		}
		File dtsOutputDir = configuration.getDtsOut();
		if (dtsOutputDir != null) {
			clean(dtsOutputDir);
		}
		File candiesJsOutDir = configuration.getCandiesJsOut();
		if (candiesJsOutDir != null) {
			clean(candiesJsOutDir);
		}

		clean(new File(".jsweet"));
		
		logInfo("clean end");
	}
}
