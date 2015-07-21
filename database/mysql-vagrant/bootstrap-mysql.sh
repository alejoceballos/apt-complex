#!/usr/bin/env bash

# Check:
# http://docs.vagrantup.com/v2/getting-started/provisioning.html
# http://howtoforge.www.com/installing-apache2-with-php5-and-mysql-support-on-ubuntu-13.04-lamp
# https://github.com/varying-vagrant-vagrants/vvv/wiki/Connecting-to-MySQL
#
# ------------------------------------------------------------------------------
# SSH Tunnel
# 
# When setting up a new database connection in MySQL Workbench:
#
# Choose "Connection method" Standard TCP/IP over SSH...
# 1) For SSH Hostname, 127.0.0.1:2222
# 2) For SSH Username, enter "vagrant" or leave it blank so you'll be asked avery time you try to connect
# 3) For SSH Password, enter "vagrant"
# 4) For SSH Key File, leave it blank
# 5) For MySQL Hostname, enter "127.0.0.1"
# 6) For MySQL Server Port, enter "3306"
# 7) For MySQL Username, use "root"
# 8) For MySQL Password, use "root" or leave it blank so you'll be asked avery time you try to connect
# 9) For Database and Port, leave these blank or as default
#
# In the Remote Management tab...
# 1) Select "SSH login based management"
# 2) For Hostname, enter "127.0.0.1"
# 3) For Port, enter "2222"
# 4) For Username, enter "vagrant"
# 5) For Password, use "root" or leave it blank so you'll be asked avery time you try to connect
# 6) For SSH Key Path, leave it blank
#
# You may get an error when trying to connect with the last steps configured. If in a different OS than Linux (i.e. Mac), go to "System Profile" tab...
# 1) For System type, choose "Linux"
# 2) For Installation Type, choose "Ubuntu Linux (Vendor Package)"
# 3) For Configuration File, enter "/etc/mysql/my.cnf"
# 4) For Configuration File Section, enter "mysqld"
# 5) For Start MySQL, enter "start mysql"
# 6) For Stop MySQL, enter "stop mysql"
# 7) Check "elevate privileges to..."
#
# ------------------------------------------------------------------------------
# Access from outside the VM (dev env)
#
# According to these:
# + http://stackoverflow.com/questions/10709334/how-to-connect-to-mysql-server-inside-virtualbox-vagrant
# + http://thoughtfulsoftware.blogspot.com.br/2013/04/connecting-to-mysql-inside-vagrant-vm.html
# + https://github.com/crohr/vagrant-presentation/blob/master/transcript.md
#
# "Make sure MySQL binds to 0.0.0.0 and not 127.0.0.1 or it will not be accessible from outside the machine"
#
# 1a) In "/etc/mysql/my.cnf", under "Instead of skip-networking the default" set "bind-address" to "0.0.0.0":
#    + bind-address = 0.0.0.0
#  or
# 1b) Using the SSH Tunel connection in MySQL Workbench, in the "INSTANCE -> Options File" menu, "Networking" tab
#    + Check "bind-address" option and set its value to 0.0.0.0
#
# 2) Create a user root (or any other needed) with "%" access:
#    + CREATE USER 'root'@'%' IDENTIFIED BY PASSWORD 'root';
#    + GRANT ALL PRIVILEGES ON * . * TO 'root'@'%';
#    + FLUSH PRIVILEGES;
#
# ------------------------------------------------------------------------------
# Other application accesses
#
# After doing all the above, use host 0.0.0.0 and port 3307 from outside de VM
#

echo '## #############################################################################'
echo '## MySQL 5'
echo '## #############################################################################'

# Remove any existence of previous mysql installations
sudo apt-get -y purge mysql*
sudo apt-get -y autoremove
sudo rm -rf /var/log/mysql
sudo rm -rf /var/log/mysql.*
sudo rm -rf /var/lib/mysql
sudo rm -rf /etc/mysql

# (Re)installs it
MYSQL_ROOT_PWD=root

# Setting root password
sudo debconf-set-selections <<< 'mysql-server mysql-server/root_password password root'
sudo debconf-set-selections <<< 'mysql-server mysql-server/root_password_again password root'

sudo apt-get -y install mysql-server

# Needed no more since it comes bundled with the server installation 
# apt-get -y install mysql-client-5.6

# This package includes development libraries and header files.
sudo apt-get -y install libmysqlclient-dev

# Fixes any broken package (just to be sure)
sudo apt-get -y -f install

# Allows root user to access the database from any location in the network
mysql -uroot -p$MYSQL_ROOT_PWD -e "CREATE USER 'root'@'%' IDENTIFIED BY 'root';"
mysql -uroot -p$MYSQL_ROOT_PWD -e "GRANT ALL ON *.* TO 'root'@'%' IDENTIFIED BY 'root' WITH GRANT OPTION;"
mysql -uroot -p$MYSQL_ROOT_PWD -e "FLUSH PRIVILEGES;"

echo '## #############################################################################'
echo '## MySQL settings'
echo '## #############################################################################'

# Creates extra users user
# mysql -uroot -p$MYSQL_ROOT_PWD -e "CREATE USER 'some_user'@'%' IDENTIFIED BY 'some_user';"
# mysql -uroot -p$MYSQL_ROOT_PWD -e "GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON some_db.* TO 'some_user'@'%';"
# mysql -uroot -p$MYSQL_ROOT_PWD -e "FLUSH PRIVILEGES;"

# "my.cnf" (MySQL config file) full path
MY_CNF_PATH=/etc/mysql
MY_CNF_FILE=$MY_CNF_PATH/my.cnf

# Create a backup file from the original one
sudo cp $MY_CNF_FILE $MY_CNF_FILE.bak

# "Make sure MySQL binds to 0.0.0.0 and not 127.0.0.1" so it can be accessed outside the VM
sudo sed -i 's/127\.0\.0\.1/0\.0\.0\.0/g' /etc/mysql/my.cnf

# Set "max_allowed_packet" to "32M"
# sed -i '/max_allowed_packet/ c\max_allowed_packet = 32M' $MY_CNF_FILE

# Creates "character-set-server" with "utf8" value
# sed -i '/\[mysqld\]/ a\character-set-server = utf8' $MY_CNF_FILE

# Creates "sql_mode" with "NO_ENGINE_SUBSTITUTION" value
# sed -i '/\[mysqld\]/ a\sql_mode = NO_ENGINE_SUBSTITUTION' $MY_CNF_FILE

sudo service mysql restart

