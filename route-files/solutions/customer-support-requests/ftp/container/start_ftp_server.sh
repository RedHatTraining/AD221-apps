#!/bin/sh
useradd -M -d /home/${USERNAME} -u 1001 $USERNAME
echo "${USERNAME}:${PASSWORD}" | chpasswd

chown ${USERNAME}:${USERNAME} /home/${USERNAME}
chmod 777 -R /home/${USERNAME}/*

echo "FTP server running..."

/usr/sbin/vsftpd /etc/vsftpd/vsftpd.conf
