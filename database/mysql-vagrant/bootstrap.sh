#!/usr/bin/env bash

# Check:
# http://docs.vagrantup.com/v2/getting-started/provisioning.html

## #############################################################################
## #############################################################################
##
## Start installation process
##
## #############################################################################
## #############################################################################

echo "## ######################################################################"
echo "## Language Pack & Build Essentials"
echo "## ######################################################################"

apt-get update
apt-get -y install language-pack-en
apt-get -y install build-essential
apt-get -y -f install

## #############################################################################
## LOCALE settings (or it won't be able to use "debconf-set-selections")
## #############################################################################

locale-gen en_US.UTF-8

LOCALE_PATH='/etc/default/locale'
bash -c "echo 'LC_ALL=\"en_US.UTF-8\"' >> $LOCALE_PATH"

dpkg-reconfigure locales

