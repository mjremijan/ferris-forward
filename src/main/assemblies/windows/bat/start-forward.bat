@Echo OFF
Echo.

Echo LAUNCH DIRECTORY
Echo ----------------
Echo "%~dp0"
Echo. 

Echo CURRENT DIRECTORY
Echo -----------------
Echo "%CD%"
Echo. 

Set PD=%~dp0..
Set JAVA_BIN=%PD%\jre\bin\java.exe
Set JAVAW_BIN=%PD%\jre\bin\javaw.exe
Echo JAVA BIN
Echo --------
Echo "%JAVA_BIN%"
Echo. 

Echo JAVA VERSION
Echo ------------
%JAVA_BIN% -version
Echo.

Echo START FORWARD
Echo -------------
%JAVA_BIN% -jar %PD%\lib\${project.build.finalName}-windows.jar
