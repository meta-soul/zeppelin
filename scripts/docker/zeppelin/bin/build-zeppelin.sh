rm zeppelin*.tar.gz
cp ../../../../zeppelin-distribution/target/zeppelin-0.11.0-SNAPSHOT.tar.gz ./
tar -xvf zeppelin-0.11.0-SNAPSHOT.tar.gz
cd zeppelin-0.11.0-SNAPSHOT

# remove META-INF
find . -iname 'zeppelin-flink-0.11.0-SNAPSHOT-2.11.jar' -delete
find . -iname '*.jar' -exec bash -c "zip -d {} 'META-INF/maven/*'" \;
# rename jar
find . -iname '*.jar' ! -iname '*-0.11.0-SNAPSHOT*' -exec bash -c  "rename 's/\-\d+(\.\d+)*.*\.jar/.jar/' {}" \;
cd ../
rm zeppelin*.tar.gz
tar -zcvf zeppelin-0.11.0-SNAPSHOT.tar.gz zeppelin-0.11.0-SNAPSHOT

docker build -t swr.cn-southwest-2.myhuaweicloud.com/dmetasoul-repo/zeppelin:0.11.0-SNAPSHOT-workspace-v15 -f Dockerfile-Zeppelin --no-cache .

rm -rf zeppelin-0.11.0-SNAPSHOT*
