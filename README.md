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
		classpath('org.jsweet:jsweet-gradle-plugin:2.0.0-SNAPSHOT') { //
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
    compile group: 'org.jsweet', name: 'jsweet-core', version:'5-SNAPSHOT'
    compile group: 'org.jsweet.candies.ext', name: 'angular', version:'1.4.0-SNAPSHOT'
    compile group: 'org.jsweet.candies.ext', name: 'angular-route', version:'1.2.0-SNAPSHOT'
    compile group: 'org.jsweet.candies.trusted', name: 'es6-promise', version:'0.0.0-SNAPSHOT'
    compile group: 'org.jsweet', name: 'jsweet-transpiler', version:'2.0.0-SNAPSHOT'
}
```

Configure the JSweet plugin:
```groovy
jsweet {
	verbose = true
	encoding = 'UTF-8'
	sourceMap = true
	outDir = new File('target/javascript')
	targetVersion = 'ES3'
	includes = ['**/fr/test/my/**/*.java']
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

## Deploy the plugin
```
gradlew publish
```

