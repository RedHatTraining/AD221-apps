import subprocess
from pathlib import Path

cwd = Path(__file__).parent.resolve()

print("\033[1;32mBuild FTP server image\033[0m\n")
subprocess.Popen(
    "podman build -f Containerfile -t ad221-vsftpd container",
    cwd=cwd,
    shell=True
).communicate()

print("\n\n\033[1;32mStart FTP server\033[0m\n")
subprocess.Popen(
    "podman run --rm -ti"
    "  --name ad221-ftp-server"
    "  -p 21720:20 -p 21721:21 -p 21100-21110:21100-21110"
    "  -v $(pwd)/data:/tmp/data:z"
    "  ad221-vsftpd",
    cwd=cwd,
    shell=True
).communicate()