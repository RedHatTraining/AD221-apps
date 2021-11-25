#!/bin/sh
useradd -M -d /home/${USERNAME} -u 1001 $USERNAME

chown ${USERNAME}:${USERNAME} /home/${USERNAME}

echo "${USERNAME}:${PASSWORD}" | chpasswd

# /usr/bin/ln -sf /dev/stdout /var/log/xferlog

echo "Running FTP server..."

/usr/sbin/vsftpd /etc/vsftpd/vsftpd.conf
