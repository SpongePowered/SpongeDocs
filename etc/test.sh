sphinx-intl build -d locale/ 1>/dev/null
find locale -d 1 | 
sed 's/locale\///' | 
xargs -P 16 -I % sphinx-build -b html -D language=% source build/lang/%