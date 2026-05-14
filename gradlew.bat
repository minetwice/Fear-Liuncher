@ECHO OFF

SET DIRNAME=%~dp0
SET APP_HOME=%DIRNAME%

SET DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

SET CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

java %DEFAULT_JVM_OPTS% -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
