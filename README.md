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
targetVersion | enum | ES3, ES5, ES6 | ES3 | ``` jsweet { targetVersion = EcmaScriptComplianceLevel.ES3 } ```
module | enum | commonjs, amd, system, umd | none | ``` jsweet { module = ModuleKind.commonjs } ```
outDir | File | JS files output directory | .jsweet/js | ```jsweet { outDir = new File('target/js') }```
tsOut | File | Temporary TypeScript output directory | .jsweet/ts | ```jsweet { tsOut = new File('target/ts') }```
includes | string[] | Java source files to be included | N/A | ```jsweet { includes = ['**/org/jsweet/examples/**/*.java', '**/other/*] }```
excludes | string[] | Source files to be excluded | N/A | ```jsweet { excludes = ['**/excluded/**/*.java'] }```
bundle | boolean | Concats all JS file into one bundle | false |   ```jsweet { bundle = true }```
bundlesDirectory | File | JS bundles output directory | N/A | ```jsweet { bundlesDirectory = new File('target/js/bundles') }```
sourceMap | boolean | In-browser debug mode - true for java, typescript else | true | ```jsweet { javaDebug = true }```
encoding | string | Java files encoding | UTF-8 | ```jsweet { encoding = 'UTF-8' }```
noRootDirectories | boolean | output is relative to @jsweet.lang.Root package's directories | false | ```jsweet { noRootDirectories = true }```
enableAssertions | boolean | assert are transpiled as JS check | false | ```jsweet { enableAssertions = true }```
verbose | boolean | Verbose transpiler output | false | ```jsweet { verbose = true }```
jdkHome | File | Alternative JDK >= 8 directory, for instance if running Maven with a JRE | ${java.home} | ```jsweet { jdkHome = new File('/opt/jdk8') }```
declaration | boolean | Generates TypeScript d.ts | false | ```jsweet { declaration = true }```
dtsOut | File | TypeScript d.ts output directory when the declaration option is true | outDir | ```jsweet { dtsOut = new File('typings') }```
candiesJsOut | File | Directory where to extract candies' Javascript |  | ```candiesJsOut { jdkHome = new File('www/js/candies') }```

jsweet { verbose = true }

jsweet { sourceMap = true }

jsweet { excludes = ['**/excluded/**/*.java'] }
jsweet { module = ModuleKind.commonjs }
jsweet { module = ModuleKind.commonjs }

	
	sourceMap = true
	
	targetVersion = EcmaScriptComplianceLevel.ES3
	includes = ['**/org/jsweet/examples/**/*.java']
}

Then, just invoke the JSweet gradle task:

```
$ gradle jsweet
```