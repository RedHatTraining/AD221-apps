#!/bin/sh
useradd -d /home/${USERNAME} -u 1001 $USERNAME
echo "${USERNAME}:${PASSWORD}" | chpasswd

cp /tmp/data/* /home/${USERNAME}
chmod 777 -R /home/${USERNAME}/*

echo "FTP server running..."

/usr/sbin/vsftpd /etc/vsftpd/vsftpd.conf
