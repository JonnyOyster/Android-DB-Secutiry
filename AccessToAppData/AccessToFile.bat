cd C:\Users\kvpshka\Desktop\extractor
adb backup -noapk knyaseff.net.lab2_writefile
java -jar abe.jar unpack backup.ab backup.tar ""
7z x backup.tar
cd apps
cd knyaseff.net.lab2_writefile
cd f
Lab2.txt