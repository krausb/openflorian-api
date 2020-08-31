#Setup a raspberry pi for Openflorian as Server

##1. Setup Oracle Repo

```shell
&> echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee /etc/apt/sources.list.d/webupd8team-java.list
&> echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee -a /etc/apt/sources.list.d/webupd8team-java.list
&> apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys EEA14886
&> apt-get update
```

##2. Install base packages

```shell
apt-get install vim nginx-full php5-fpm php5-mysql apache2-utils libapr1-dev libaprutil1-dev libxml2 \
    libxml2-dev libxml2-utils libssl-dev openssl libmcrypt-dev libbz2-dev libcurl3-dev \
    libjpeg-dev libpng-dev libXpm-dev libfreetype6-dev libt1-dev libgmp3-dev \
    libc-client-dev libldap2-dev libmcrypt-dev libmhash-dev freetds-dev libz-dev \
    libmysqlclient15-dev ncurses-dev libpcre3-dev libsqlite-dev libaspell-dev \
    libreadline6-dev librecode-dev libsnmp-dev libtidy-dev libxslt-dev libt1-dev \ 
    mysql-server curl oracle-java8-installer hylafax-server tesseract-ocr
```
    
##3. Setup Apache Tomcat

- Download von: http://tomcat.apache.org/download-80.cgi

##4. Tomcat Hardening
http://www.mulesoft.com/tcat/tomcat-security
https://www.owasp.org/index.php/Securing_tomcat


Homedirectory == CATALINA_BASE: /srv/app/<INSTANZ>
```shell
&> chown <user>:<usergrp> /srv/app/<INSTANZ>
&> chmod 750 /srv/app/<INSTANZ>
&> chmod 300 /srv/app/<INSTANZ>/logs
&> chmod 400 /srv/app/<INSTANZ>/conf
```

##5. UTF-8
CATALINA_BASE/bin/setenv.sh: JAVA_OPTS="-Dfile.encoding=UTF-8" export JAVA_OPTS
CATALINA_BASE/conf/server.xml:
...
  <Service name="Catalina">
...
    <Connector port="..." protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="..." URIEncoding="UTF-8"/>
...

##6. Setup Tesseract

Copy run/server-conf/tesseract/eng.traineddata to /usr/share/tesseract-ocr/tessdata

##7. Setup HAProxy & nginx 

- haproxy.cfg: /etc/haproxy/haproxy.cfg
- nginx:
  - /etc/nginx/conf.d/caches.conf
  - /etc/nginx/sites-enabled/vhost.conf
  - /etc/nginx/nginx.conf
- php-fpm
  - /etc/php/fpm/php-fpm.conf
  - /etc/php/fpm/php.ini

##8. Setup Hylafax

copy hylafax-config.ttyACM0 to /etc/hylafax/config.ttyACM0