@ECHO OFF

set apkDir="C:\Users\zackl\Desktop\ResearchTool\ASM\ASM_Instrumenter\testApk"

CALL C:\\Users\\zackl\\Desktop\\ResearchTool\\dex2jar-2.1\\d2j-dex2jar.bat %apkDir%\%1.apk -o %apkDir%\decompiled\orig\%1_dex2jar.jar --force

if not exist "%apkDir%\decompiled\orig\%1" mkdir %apkDir%\decompiled\orig\%1

cd %apkDir%\decompiled\orig\%1
jar xf %apkDir%\decompiled\orig\%1_dex2jar.jar
