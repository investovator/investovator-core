investovator-core
=================

core features of the investovator

[![Build Status](https://drone.io/github.com/investovator/investovator-core/status.png)](https://drone.io/github.com/investovator/investovator-core/latest)

 **Guidelines to setup OpenLdap for testing**

 1. first install slapd and ldap utils

   Ubuntu - sudo apt-get install slapd ldap-utils

   It will prompt to setup your admin user password automatically. This will install slapd with the domain name of 127.0.1.1 in /etc/hosts. In there you can edit domain name before installation or you can reconfigure it after installation by giving the command sudo dpkg-reconfigure slapd

 example: dc=investovator,dc=org

               
 2. add the given sample ldif (/investovator-core/src/test/resources/samples/ldifsample.ldif) by giving the command

 ldapadd -f ldifsample.ldif -x -D "cn=admin,dc=investovator,dc=org" -w secret
