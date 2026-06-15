@echo off
setlocal
set MAVEN_PROJECTBASEDIR=%~dp0
set MVNW_CMD_LINE_ARGS=%*

if exist "%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar" (
    java -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" -cp "%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar" org.apache.maven.wrapper.MavenWrapperMain %MVNW_CMD_LINE_ARGS%
) else (
    echo Maven Wrapper JAR not found.
    exit /b 1
)
endlocal
