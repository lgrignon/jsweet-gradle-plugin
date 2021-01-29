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

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.gradle.api.internal.ConventionTask;
import org.gradle.api.tasks.Input;

/**
 * Base class for JSweet tasks
 * 
 * @author Louis Grignon
 *
 */
public abstract class AbstractJSweetTask extends ConventionTask {

	protected final Logger logger = Logger.getLogger(getClass());

	@Input
	protected JSweetPluginExtension configuration;

	public JSweetPluginExtension getConfiguration() {
		return configuration;
	}

	public void setConfiguration(JSweetPluginExtension configuration) {
		this.configuration = configuration;
	}

	protected void logInfo(String content) {
		if (configuration.isVerbose() != null && configuration.isVerbose() || configuration.isVeryVerbose() != null
				&& configuration.isVeryVerbose()) {
			logger.info(content);
		}
	}

	protected void configureLogging() {
		LogManager.getLogger("org.jsweet").setLevel(Level.WARN);

		if (configuration.isVerbose() != null && configuration.isVerbose()) {
			LogManager.getLogger("org.jsweet").setLevel(Level.DEBUG);
		}
		if (configuration.isVeryVerbose() != null && configuration.isVeryVerbose()) {
			LogManager.getLogger("org.jsweet").setLevel(Level.ALL);
		}
	}
}