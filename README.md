# Apache Zeppelin

## 打包镜像
1.首先mvn编译（需要 jdk-8）：

(

注：如果是全新的环境，要先本地编译安装 maven 依赖：
```shell
wget -O rlang/target/spark-2.4.6-bin-without-hadoop.tgz https://archive.apache.org/dist/spark/spark-2.4.6/spark-2.4.6-bin-without-hadoop.tgz
mvn package -DskipTests -Pbuild-distr -Pscala-2.12 -Pspark-3.3 -Phadoop3 -Pspark-scala-2.12 -Pflink-117  -Pweb-angular  -pl '!groovy,!submarine,!livy,!hbase,!file,!sparql,!cassandra,!alluxio,!elasticsearch,!influxdb,!kotlin,!bigquery,!mongodb,!zeppelin-plugins/launcher/cluster,!zeppelin-plugins/launcher/docker,!zeppelin-plugins/notebookrepo/azure,!zeppelin-plugins/notebookrepo/gcs,!zeppelin-plugins/notebookrepo/mongo,!zeppelin-plugins/notebookrepo/s3,!neo4j,!rlang,!spark/scala-2.11,!spark/scala-2.13,!zeppelin-jupyter,!jdbc' -am -Dmaven.gitcommitid.skip=true -Dcheckstyle.skip
```

)

然后执行 Zeppelin 编译，这里屏蔽了大部分无用的解释器：

```shell
mvn clean package -DskipTests -Pbuild-distr -Pscala-2.12 -Pspark-3.3 -Phadoop3 -Pspark-scala-2.12 -Pflink-117  -Pweb-angular  -pl '!groovy,!submarine,!livy,!hbase,!file,!sparql,!cassandra,!alluxio,!elasticsearch,!influxdb,!kotlin,!bigquery,!mongodb,!zeppelin-plugins/launcher/docker,!zeppelin-plugins/launcher/cluster,!zeppelin-plugins/notebookrepo/azure,!zeppelin-plugins/notebookrepo/gcs,!zeppelin-plugins/notebookrepo/mongo,!zeppelin-plugins/notebookrepo/s3,!neo4j,!rlang,!spark/scala-2.11,!spark/scala-2.13,!zeppelin
-jupyter,!jdbc' -am -Dmaven.gitcommitid.skip=true -Dcheckstyle.skip
```
2.进入打包脚本目录
cd scripts/docker/zeppelin/bin
修改build-zeppelin.sh 镜像tag
3. bash build-zeppelin.sh
4. docker-squash -t swr.cn-southwest-2.myhuaweicloud.com/dmetasoul-repo/zeppelin:prod-v24  swr.cn-southwest-2.myhuaweicloud.com/dmetasoul-repo/zeppelin:dev-v24
5. docker push swr.cn-southwest-2.myhuaweicloud.com/dmetasoul-repo/zeppelin:prod-v24

==========================================================================

**Documentation:** [User Guide](https://zeppelin.apache.org/docs/latest/index.html)<br/>
**Mailing Lists:** [User and Dev mailing list](https://zeppelin.apache.org/community.html)<br/>
**Continuous Integration:** ![core](https://github.com/apache/zeppelin/workflows/core/badge.svg) ![frontend](https://github.com/apache/zeppelin/workflows/frontend/badge.svg) ![rat](https://github.com/apache/zeppelin/workflows/rat/badge.svg) <br/>
**Contributing:** [Contribution Guide](https://zeppelin.apache.org/contribution/contributions.html)<br/>
**Issue Tracker:** [Jira](https://issues.apache.org/jira/browse/ZEPPELIN)<br/>
**License:** [Apache 2.0](https://github.com/apache/zeppelin/blob/master/LICENSE)


**Zeppelin**, a web-based notebook that enables interactive data analytics. You can make beautiful data-driven, interactive and collaborative documents with SQL, Scala and more.

Core features:
   * Web based notebook style editor.
   * Built-in Apache Spark support


To know more about Zeppelin, visit our web site [https://zeppelin.apache.org](https://zeppelin.apache.org)


## Getting Started

### Install binary package
Please go to [install](https://zeppelin.apache.org/docs/latest/quickstart/install.html) to install Apache Zeppelin from binary package.

### Build from source
Please check [Build from source](https://zeppelin.apache.org/docs/latest/setup/basics/how_to_build.html) to build Zeppelin from source.
