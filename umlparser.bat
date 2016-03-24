@ECHO OFF
rem ECHO Hello World !
rem hardcoded java program run command
rem "C:\Program Files\Java\jre1.8.0_73\bin\javaw.exe" -Dfile.encoding=Cp1252 -classpath C:\Users\Bala\Java\workspace\parser1\target\classes;C:\Users\Bala\.m2\repository\com\github\javaparser\javaparser-core\2.1.0\javaparser-core-2.1.0.jar Main
rem "C:\Program Files\Java\jre1.8.0_73\bin\javaw.exe" -Dfile.encoding=Cp1252 -classpath C:\Users\Bala\Java\workspace\parser1\target\classes;C:\Users\Bala\.m2\repository\com\github\javaparser\javaparser-core\2.1.0\javaparser-core-2.1.0.jar Main %1
rem %1 = "C:\Users\Bala\Desktop\202 - SSE\UML Parser Project\uml-parser-test-5\uml-parser-test-5"


rem hard coded python command to generate yuml image
rem python "C:\Users\Bala\Desktop\202 - SSE\UML Parser Project\yuml-master\yuml" -s plain -i "C:\Users\Bala\Desktop\202 - SSE\UML Parser Project\yUMLOutput.yuml" -o "C:\Users\Bala\Desktop\202 - SSE\UML Parser Project\output.jpg"
rem python "C:\Users\Bala\Desktop\202 - SSE\UML Parser Project\yuml-master\yuml" -s plain -i "C:\Users\Bala\Desktop\202 - SSE\UML Parser Project\yUMLOutput.yuml" -o %2
rem %2 = "C:\Users\Bala\Desktop\202 - SSE\UML Parser Project\output.jpg"


java -jar  parser.jar %1
python .\yuml-master\yuml -s plain -i .\yUMLOutput.yuml -o %2

rem ECHO %1
rem ECHO %2
rem pause