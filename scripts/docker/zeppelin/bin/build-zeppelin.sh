rm zeppelin*.tar.gz
cp ../../../../zeppelin-distribution/target/zeppelin-0.11.0-SNAPSHOT.tar.gz ./
docker build -t swr.cn-southwest-2.myhuaweicloud.com/dmetasoul-repo/zeppelin:0.11.0-SNAPSHOT-workspace-v6 -f Dockerfile-Zeppelin .
rm zeppelin*.tar.gz