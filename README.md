# JSweet Gradle plugin

Brings the power of JSweet to Gradle

## Usage
Add the JSweet's repositories and the Gradle plugin dependency to your project's build.gradle, in the buildScript section:
```groovy
buildscript {
	repositories {
		mavenCentral()
		maven { url "http://repository.jsweet.org/artifactory/libs-release-local" }
		maven { url "http://repository.jsweet.org/artifactory/libs-snapshot-local" }
		maven { url "http://repository.jsweet.org/artifactory/plugins-release-local" }
		maven { url "http://repository.jsweet.org/artifactory/plugins-snapshot-local" }
		maven { url "http://google-diff-match-patch.googlecode.com/svn/trunk/maven" }
	}
	dependencies {
		classpath('org.jsweet:jsweet-gradle-plugin:3.0.0') { //
			transitive = true }
	}
}
```

Then apply the JSweet Gradle plugin, as usual:
```groovy
apply plugin: 'org.jsweet.jsweet-gradle-plugin'
```

and optionally  disable java compilation (JSweet sources may not be considered as standard java sources):
```groovy
compileJava {
	enabled = false
}
```

Add your JSweet dependencies (candies):
```groovy

dependencies {
	compile group: 'org.jsweet', name: 'jsweet-transpiler', version: "3.0.0"
	compile group: 'org.jsweet', name: 'jsweet-core', version: "6"
	compile group: 'org.jsweet.candies', name: 'angular', version: "1.4.0-20170726"
	compile group: 'org.jsweet.candies', name: 'angular-route', version: "1.2.0-20170726"
}
```

Configure the JSweet plugin:
```groovy
jsweet {
	verbose = true
	encoding = 'UTF-8'
	sourceMap = true
	outDir = new File('target/javascript')
	candiesJsOut = new File('target/candies')
	targetVersion = 'ES6'
	includes = ['**/fr/test/my/**/*.java']
	
	// extraSystemPath = '/my/path/to/npm'
}

```

The configuration options are based on the JSweet maven plugin options, please refer to its README for a comprehensive documentation:
https://github.com/lgrignon/jsweet-maven-plugin#basic-configuration


Then, just invoke one of the JSweet gradle task:

```
$ gradle jsweet
```

```
$ gradle jsweetClean
```

## Development / Contribution

Install with:
```
gradlew clean publishToMavenLocal
```

## Deploy the plugin (needs credentials)
```
# in local repository
gradlew publishToMavenLocal

# on JSweet's repository
gradlew publish

# on central Gradle plugins repository
gradlew publishPlugins
```
