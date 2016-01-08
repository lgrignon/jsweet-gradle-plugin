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
		classpath('org.jsweet:jsweet-gradle-plugin:1.0.0-SNAPSHOT') { //
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
    compile group: 'org.jsweet.candies', name: 'jsweet-core', version:'1.0.2-SNAPSHOT'
    compile group: 'org.jsweet.candies', name: 'angular', version:'1.4.2-SNAPSHOT'
    compile group: 'org.jsweet.candies', name: 'angular-route', version:'1.3.2-SNAPSHOT'
    compile group: 'org.jsweet.candies', name: 'es6-promise', version:'0.0.2-SNAPSHOT'
    compile group: 'org.jsweet', name: 'jsweet-transpiler', version:'1.0.0-SNAPSHOT'
}
```

Configure the JSweet plugin:
```groovy
jsweet {
	verbose = true
	encoding = 'UTF-8'
	sourceMap = true
	outDir = new File('target/js')
	targetVersion = EcmaScriptComplianceLevel.ES3
	includes = ['**/fr/test/my/**/*.java']
}

```

The configuration options of the plugin:

Name     |    Type       | Values | Default | Example
-------- | ------------- | ------ | ------- | ------- 
targetVersion | enum | ES3, ES5, ES6 | ES3 | ``` <targetVersion>ES3</targetVersion> ```
module | enum | commonjs, amd, system, umd | none | ```<module>commonjs</module>```
outDir | string | JS files output directory | .jsweet/js | ```<outDir>js</outDir>```
tsOut | string | Temporary TypeScript output directory | .jsweet/ts | ```<tsOut>temp/ts</tsOut>```
includes | string[] | Java source files to be included | N/A | ```<includes><include>**/*.java</include></includes>```
excludes | string[] | Source files to be excluded | N/A | ```<excludes><exclude>**/lib/**</exclude></excludes>```
bundle | boolean | Concats all JS file into one bundle | false |   ```<bundle>true</bundle>```
bundlesDirectory | string | JS bundles output directory | N/A | ```<bundlesDirectory>js/dist</bundlesDirectory>```
sourceMap | boolean | In-browser debug mode - true for java, typescript else | true | ```<javaDebug>true</javaDebug>```
encoding | string | Java files encoding | UTF-8 | ```<encoding>ISO-8859-1</encoding>```
noRootDirectories | boolean | output is relative to @jsweet.lang.Root package's directories | false | ```<noRootDirectories>true</noRootDirectories>```
enableAssertions | boolean | assert are transpiled as JS check | false | ```<enableAssertions>true</enableAssertions>```
verbose | boolean | Verbose transpiler output | false | ```<verbose>true</verbose>```
jdkHome | string | Alternative JDK >= 8 directory, for instance if running Maven with a JRE | ${java.home} | ```<jdkHome>/opt/jdk8</jdkHome>```


Then, just invoke the JSweet gradle task:

```
$ gradle jsweet
```