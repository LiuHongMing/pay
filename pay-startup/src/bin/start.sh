#!/bin/sh
echo -------------------------------------------
echo start server
echo -------------------------------------------
# JAVA_HOME
export JAVA_HOME=/data/jdk
# 设置项目代码路径
export CODE_HOME="/data/webserver/pay-startup-package"
# 日志路径
export LOG_PATH="/data/webserver/logs/pay.senyint.local"
mkdir -p $LOG_PATH
# 设置依赖路径
export CLASSPATH="$CODE_HOME/classes:$CODE_HOME/lib/*"
# java可执行文件位置
export _EXECJAVA="$JAVA_HOME/bin/java"
# JVM启动参数
# 打印参数
FLAG_OPTS="-XX:+PrintCommandLineFlags"
# 内存参数
MEMORY_OPTS="-Xms2048m -Xmx2048m -Xmn1024m -XX:MaxDirectMemorySize=2048m -XX:PermSize=256m -XX:MaxPermSize=512m"
# 性能参数
# -XX:+PerfDisableSharedMem: 解决: JVM statistics cause garbage collection pauses, 导致: jps, jstat不可用
PERFORMANCE_OPTS="-XX:-UseBiasedLocking -XX:-UseCounterDecay -XX:+AlwaysPreTouch -XX:AutoBoxCacheMax=20000 -XX:ReservedCodeCacheSize=240m"
# GC CMS参数
CMS_GC_OPTS="-XX:+UseConcMarkSweepGC -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=75 -XX:MaxTenuringThreshold=6 -XX:+ExplicitGCInvokesConcurrent -XX:+ParallelRefProcEnabled"
# GC 日志参数
GC_OPTS="-Xloggc:/dev/shm/pay-gc.log -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCDateStamps -XX:+PrintGCDetails"
# 异常参数
ERROR_OPTS="-XX:-OmitStackTraceInFastThrow -XX:ErrorFile=${LOG_PATH}/hs_err_%p.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${LOG_PATH}/"
# JMX
JMX_PORT="18888"
JMX_OPTS="-Djava.rmi.server.hostname=192.168.20.132 -Dcom.sun.management.jmxremote.port=${JMX_PORT} -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
export JAVA_OPTS="-server $FLAG_OPTS $MEMORY_OPTS $PERFORMANCE_OPTS $CMS_GC_OPTS $GC_OPTS $ERROR_OPTS $JMX_OPTS"
# 启动类
export MAIN_CLASS="com.senyint.startup.Bootstrap"

nohup $_EXECJAVA $JAVA_OPTS -classpath $CLASSPATH $MAIN_CLASS >/dev/null 2>&1 &
#tail -f $LOG_PATH/stdout.log