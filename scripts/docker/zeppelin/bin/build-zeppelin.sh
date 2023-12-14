# mvn clean install -Pbuild-distr -DskipTests -Dcheckstyle.skip=true
rm zeppelin*.tar.gz
cp ../../../../zeppelin-distribution/target/zeppelin-0.11.0-SNAPSHOT.tar.gz ./
SELF_DIR="$(cd "$(dirname "$0")"; pwd)"
source "${SELF_DIR}/.env"
docker build \
   --build-arg DOWNLOAD_OBJECT_URL=${DOWNLOAD_OBJECT_URL} \
   --build-arg APACHE_MIRROR=${APACHE_MIRROR} \
   --build-arg MAVEN_MIRROR=${MAVEN_MIRROR} \
   --build-arg AWS_JAVA_SDK_VERSION=${AWS_JAVA_SDK_VERSION} \
   --build-arg HADOOP_VERSION=${HADOOP_VERSION} \
   --build-arg SPARK_HADOOP_VERSION=${SPARK_HADOOP_VERSION} \
   --build-arg FLINK_VERSION=${FLINK_VERSION} \
   --build-arg SCALA_BINARY_VERSION=${SCALA_BINARY_VERSION} \
   --build-arg SPARK_VERSION=${SPARK_VERSION} \
   --build-arg SPARK_BINARY_VERSION=${SPARK_BINARY_VERSION} \
   --file "${SELF_DIR}/Dockerfile-Zeppelin" \
   --no-cache \
   --tag "${IMAGE_REPO}/zeppelin:0.11.0-SNAPSHOT-workspace-v9-k8s" \
   "${SELF_DIR}/"
rm zeppelin*.tar.gz
