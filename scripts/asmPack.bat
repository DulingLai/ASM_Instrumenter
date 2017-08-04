@ECHO OFF

set apkDir="C:\Users\zackl\Desktop\ResearchTool\ASM\ASM_Instrumenter\testApk"
set baseDir="C:\Users\zackl\Desktop\ResearchTool\ASM\ASM_Instrumenter"
set appName=%1
set instrumentType=%2

if not exist "%apkDir%\decompiled\asmOutput\%appName%_%instrumentType%" mkdir %apkDir%\decompiled\asmOutput\%appName%_%instrumentType%
cd %apkDir%\decompiled\asmOutput\%appName%_%instrumentType%

jar cf ..\%appName%.jar *

echo "executing jar2dex.bat -f -o classes.dex ..\%appName%.jar"
java -jar C:\Users\zackl\Desktop\ResearchTool\ASM\ASM_Instrumenter\lib\Jar2Dex.jar -f -o %apkDir%\decompiled\asmOutput\%appName%_%instrumentType% %apkDir%\\decompiled\\asmOutput\\%appName%.jar

REM CALL C:\\Users\\zackl\\AppData\\Local\\Android\\sdk\\build-tools\\24.0.3\\dx.bat --dex --multi-dex --no-strict --output=%CD% %apkDir%\decompiled\asmOutput\%appName%.jar

copy %apkDir%\\%appName%.apk %apkDir%\\decompiled\\asmOutput\\%appName%.apk
C:\Users\zackl\Desktop\ResearchTool\zip\zip.exe -d %apkDir%\\decompiled\\asmOutput\\%appName%.apk "\META-INF\*"
    
C:\Users\zackl\Desktop\ResearchTool\zip\zip.exe -r %apkDir%\\decompiled\\asmOutput\\%appName%.apk %apkDir%\\decompiled\\asmOutput\\%appName%_%instrumentType%\\classes.dex %apkDir%\\decompiled\\asmOutput\\%appName%_%instrumentType%\\classes2.dex
del classes.dex classes2.dex

C:\Users\zackl\AppData\Local\Android\sdk\build-tools\24.0.3\zipalign -c -v 4 %apkDir%\\decompiled\\asmOutput\\%appName%.apk

C:\Users\zackl\AppData\Local\Android\sdk\build-tools\24.0.3\apksigner sign --ks %baseDir%\\key\\appkey.jks --ks-pass pass:123456 --ks-key-alias MyKey %apkDir%\\decompiled\\asmOutput\\%appName%.apk

C:\Users\zackl\AppData\Local\Android\sdk\build-tools\24.0.3\apksigner verify %apkDir%\\decompiled\\asmOutput\\%appName%.apk
