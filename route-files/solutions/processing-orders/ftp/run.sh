#!/bin/sh

# Make sure the current working dir is ftp/
cd "$(dirname "$0")"

podman build -f Containerfile -t ad221-vsftpd
podman run  --rm -ti -p 21720:20 -p 21721:21 -p 21100-21110:21100-21110  -v $(pwd)/data:/home/datauser --name ad221-ftp-server ad221-vsftpd
