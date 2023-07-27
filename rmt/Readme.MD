# Rückmeldetool

## Web-Server Configuration

### Apache HttpServer

- Virtual host flower.de
- Folder: /var/www/vhosts/flower.de
- AJP-Config: /etc/apache2/sites-available/flower.de.conf

      JKMount /das-tool ajp13_worker
      JKMount /das-tool/* ajp13_worker
      JKMount /birt-viewer/* ajp13_worker

### Tomcat9

- Folder: /var/lib/tomcat9/webapps
- AJP-Config in /var/lib/tomcat9/conf/server.xml:

        <!-- Define an AJP 1.3 Connector on port 8009 -->
        <Connector protocol="AJP/1.3"
            address="0.0.0.0"
            port="8009"
            secretRequired="false"
            redirectPort="8443" />

### Firewall

    oblume@43:~$ sudo ufw status
    Status: active
    
    To                         Action      From
    --                         ------      ----
    OpenSSH                    ALLOW       Anywhere
    Apache Full                ALLOW       Anywhere
    OpenSSH (v6)               ALLOW       Anywhere (v6)
    Apache Full (v6)           ALLOW       Anywhere (v6)

### SSL-Certificate

- Erstellt mit Let's encrypt certbot:

      Which names would you like to activate HTTPS for?
      - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
      1: flower.de
      2: www.flower.de
      - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
      Select the appropriate numbers separated by commas and/or spaces, or leave input
      blank to select all options shown (Enter 'c' to cancel):
      Requesting a certificate for flower.de and www.flower.de
      
      Successfully received certificate.
      Certificate is saved at: /etc/letsencrypt/live/flower.de/fullchain.pem
      Key is saved at:         /etc/letsencrypt/live/flower.de/privkey.pem
      This certificate expires on 2023-10-23.
      These files will be updated when the certificate renews.
      Certbot has set up a scheduled task to automatically renew this certificate in the background.
      
      Deploying certificate
      Successfully deployed certificate for flower.de to /etc/apache2/sites-available/flower.de-le-ssl.conf
      Successfully deployed certificate for www.flower.de to /etc/apache2/sites-available/flower.de-le-ssl.conf
      Congratulations! You have successfully enabled HTTPS on https://flower.de and https://www.flower.de

### Mysql

#### Backup

- /home/oblume/mysql-backup.sh
- Skript wird täglich durch cron aufgerufen:

      # backup mysql database every day
      0 4 * * * /home/oblume/mysql-backup.sh >/dev/null 2>&1

### DNS

DNS Einträge werden über KIS
konfiguriert: https://kis.hosteurope.de/administration/domainservices/index.php?menu=2&mode=autodns



### KIS

- Virtual Server Config: https://kis.hosteurope.de/administration/virtual-server-10-plus/
- IP: 92.205.63.43

### Monitoring

- https://uptimerobot.com
        
  HTTP-Monitoring von https://flower.de/das-tool
 
- Host panel bei hosteurope

  Generelle Serverüberwachung

### Issue tracker

- https://flower.acunote.com