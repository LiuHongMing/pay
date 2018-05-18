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
# -XX:+PrintCommandLineFlags
#   打印参数
FLAG_OPTS="-XX:+PrintCommandLineFlags"
# 内存参数
# -Xms -Xmx
#   堆内存大小
# -Xmn or -XX:NewSize and -XX:MaxNewSize or -XX:NewRatio
#   新生代大小
# -XX:MaxDirectMemorySize
#   直接内存最大值
# -XX:PermSize -XX:MaxPermSize
#   永久代大小
MEMORY_OPTS="-Xms4096m -Xmx4096m -Xmn2048m -XX:MaxDirectMemorySize=4096m -XX:PermSize=256m -XX:MaxPermSize=512m -XX:ReservedCodeCacheSize=240m"
# -XX:ReservedCodeCacheSize
#   即时编译器编译的代码缓冲的最大值
JIT_OPTS="-XX:ReservedCodeCacheSize"
# 性能参数
# -XX:-UseBiasedLocking
#   关闭偏向锁
# -XX:-UseCounterDecay
#   关闭热度衰减
# -XX:AutoBoxCacheMax
#   设置Integer的自动缓存区间
# -XX:+PerfDisableSharedMem
#   禁止JVM写statistics数据，代价是jps，jstat 用不了，只能用JMX取数据。
#   解决: JVM statistics cause garbage collection pauses, 导致: jps, jstat不可用。
# -XX:+AlwaysPreTouch
#   启动应用时访问并置零所有的内存页面
PERFORMANCE_OPTS="-XX:-UseBiasedLocking -XX:-UseCounterDecay -XX:AutoBoxCacheMax=20000 -XX:+AlwaysPreTouch"
# GC CMS参数
# -XX:+UseConcMarkSweepGC
#   默认开启:
#     -XX:+UseParNewGC
#       使用 ParNew + CMS + Serial Old 的收集器组合进行内存回收
#     -XX:+CMSConcurrentMTEnabled
#       开启并发的CMS阶段将以多线程执行
# -XX:CMSInitiatingOccupancyFraction=75
#   设置CMS收集器在老年代空间被使用多少后时触发垃圾收集，仅在使用CMS收集器时生效
# -XX:+UseCMSInitiatingOccupancyOnly
#   命令JVM不基于运行时收集的数据来启动CMS垃圾收集周期，仅在使用CMS收集器时生效
#   当该标志被开启时，JVM通过CMSInitiatingOccupancyFraction的值进行每一次CMS收集
# -XX:MaxTenuringThreshold=6
#   晋升到老年代的对象年龄
# -XX:+ExplicitGCInvokesConcurrent
#   当收到System.gc()方法提交的垃圾回收申请时，使用CMS垃圾回收器进行收集
# -XX:+ParallelRefProcEnabled
#   并行的处理Reference对象
CMS_GC_OPTS="-XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -XX:MaxTenuringThreshold=6 -XX:+ExplicitGCInvokesConcurrent -XX:+ParallelRefProcEnabled"
# GC 日志参数
# -Xloggc
# -XX:+PrintGCApplicationStoppedTime
# -XX:+PrintGCDateStamps
# -XX:+PrintGCDetails
GC_OPTS="-Xloggc:/dev/shm/pay-gc.log -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCDateStamps -XX:+PrintGCDetails"
# 异常 日志参数
# -XX:-OmitStackTraceInFastThrow
# -XX:ErrorFile
# -XX:+HeapDumpOnOutOfMemoryError
# -XX:HeapDumpPath
ERROR_OPTS="-XX:-OmitStackTraceInFastThrow -XX:ErrorFile=${LOG_PATH}/hs_err_%p.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${LOG_PATH}/"
# JMX 参数
JMX_PORT="18888"
JMX_OPTS="-Djava.rmi.server.hostname=192.168.20.132 -Dcom.sun.management.jmxremote.port=${JMX_PORT} -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
export JAVA_OPTS="-server $FLAG_OPTS $MEMORY_OPTS $JIT_OPTS $PERFORMANCE_OPTS $CMS_GC_OPTS $GC_OPTS $ERROR_OPTS $JMX_OPTS"
# 启动类
export MAIN_CLASS="com.senyint.startup.Bootstrap"

nohup $_EXECJAVA $JAVA_OPTS -classpath $CLASSPATH $MAIN_CLASS >/dev/null 2>&1 &
#tail -f $LOG_PATH/stdout.log