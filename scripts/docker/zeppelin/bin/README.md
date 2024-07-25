# Zeppelin 镜像打包

1. 编译 Zeppelin：mvn clean package -DskipTests -Pbuild-distr -Pscala-2.12 -Pspark-3.3 -Phadoop3 -Pspark-scala-2.12 -Pflink-117  -Pweb-angular  -pl '!groovy,!submarine,!livy,!hbase,!file,!sparql,!cassandra,!alluxio,!elasticsearch,!influxdb,!kotlin,!bigquery,!mongodb,!zeppelin-plugins/launcher/cluster,!zeppelin-plugins/launcher/docker,!zeppelin-plugins/notebookrepo/azure,!zeppelin-plugins/notebookrepo/gcs,!zeppelin-plugins/notebookrepo/mongo,!zeppelin-plugins/notebookrepo/s3,!neo4j,!rlang,!spark/scala-2.11,!spark/scala-2.13,!zeppelin-jupyter,!jdbc' -am -Dmaven.gitcommitid.skip=true -Dcheckstyle.skip
1. 修改 build-zeppelin.sh 中的 tag ，如 dev-28
2. bash build-zeppelin.sh
3. docker-squash -t swr.cn-southwest-2.myhuaweicloud.com/dmetasoul-repo/zeppelin:prod-v28  swr.cn-southwest-2.myhuaweicloud.com/dmetasoul-repo/zeppelin:dev-v28
