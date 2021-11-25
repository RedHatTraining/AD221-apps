#!/bin/sh

# Make sure the current working dir is ftp/
cd "$(dirname "$0")"

printf "\033[1;32mBuild FTP server image...\033[0m\n"

podman build -f Containerfile -t ad221-vsftpd container

printf "\n\n\033[1;32mStart FTP server...\033[0m\n"

podman run --rm -ti \
    --name ad221-ftp-server \
    --user "$(id -u):$(id -g)" \
    -p 21720:20 -p 21721:21 -p 21100-21110:21100-21110 \
    -v $(pwd)/data:/home/datauser \
    ad221-vsftpd
