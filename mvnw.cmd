@ECHO OFF
SETLOCAL
SET MVN_VERSION=3.9.6
SET MVN_DIR=.mvn\wrapper
SET PROPERTIES_FILE=%MVN_DIR%\maven-wrapper.properties
SET JAR_FILE=%MVN_DIR%\maven-wrapper.jar

IF NOT EXIST %JAR_FILE% (
    ECHO Downloading Maven Wrapper...
    IF NOT EXIST %MVN_DIR% mkdir %MVN_DIR%
    FOR /F "tokens=2 delims==" %%A IN ('findstr "wrapperUrl" %PROPERTIES_FILE%') DO (
        powershell -Command "(New-Object Net.WebClient).DownloadFile('%%A','%JAR_FILE%')"
    )
)

java -jar %JAR_FILE% %*
