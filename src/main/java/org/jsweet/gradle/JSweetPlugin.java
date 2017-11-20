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

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

/**
 * JSweet transpilation plugin for Gradle
 * 
 * @author Louis Grignon
 *
 */
public class JSweetPlugin implements Plugin<Project> {

	@Override
	public void apply(final Project project) {
		Logger logger = project.getLogger();
		logger.info("applying jsweet plugin");

		if (!project.getPlugins().hasPlugin(JavaPlugin.class) && !project.getPlugins().hasPlugin(WarPlugin.class)) {
			logger.error("No java or war plugin detected. Enable java or war plugin.");
			throw new IllegalStateException("No java or war plugin detected. Enable java or war plugin.");
		}

		JSweetPluginExtension extension = project.getExtensions().create("jsweet", JSweetPluginExtension.class);

		JavaPluginConvention javaPluginConvention = project.getConvention().getPlugin(JavaPluginConvention.class);
		SourceSetContainer sourceSets = javaPluginConvention.getSourceSets();
		SourceSet mainSources = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME);

		JSweetTranspileTask task = project.getTasks().create("jsweet", JSweetTranspileTask.class);
		task.setGroup("generate");
		task.dependsOn(JavaPlugin.COMPILE_JAVA_TASK_NAME);
		task.setConfiguration(extension);
		task.setSources(mainSources.getAllJava());
		task.setClasspath(mainSources.getCompileClasspath());
		
		JSweetCleanTask cleanTask = project.getTasks().create("jsweetClean", JSweetCleanTask.class);
		cleanTask.setConfiguration(extension);
	}
}
