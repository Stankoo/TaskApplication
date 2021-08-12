call runcrud.bat
if "%ERRORLEVEL%" == "0" goto startchrome
echo.
echo There were errors - breaking work
goto fail

:startchrome
start chrome http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end

:fail
echo.
echo Something went wrong, script failed

:end
echo.
echo Everything is fine, work is finished