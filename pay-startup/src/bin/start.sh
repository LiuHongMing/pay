#!/bin/sh
echo -------------------------------------------
echo start server
echo -------------------------------------------
# JAVA_HOME
export JAVA_HOME=/data/jdk
# 设置项目代码路径
export CODE_HOME="/data/webserver/pay-startup-package"
# 日志路径
export LOG_PATH="/data/webserver/logs/pay.tiger.local"
mkdir -p $LOG_PATH
# 设置依赖路径
export CLASSPATH="$CODE_HOME/classes:$CODE_HOME/lib/*"
# java可执行文件位置
export _EXECJAVA="$JAVA_HOME/bin/java"

# JVM 启动参数
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
MEMORY_OPTS="-Xms4096m -Xmx4096m -Xmn2048m -XX:MaxDirectMemorySize=4096m -XX:PermSize=256m -XX:MaxPermSize=512m"

# JIT 参数
# -XX:ReservedCodeCacheSize
#   设置JIT编译代码的最大代码缓存大小（以字节为单位）。
#   附加字母k或K表示千字节，m或M表示兆字节，g或G表示千兆字节。
#   默认的最大代码缓存大小为240MB; 如果使用-XX:-TieredCompilation选项禁用分层编译，则默认大小为48MB。
#   此选项的限制为2GB; 否则，会产生错误。
#   最大代码缓存大小不应小于初始代码缓存大小；请参阅选项-XX:InitialCodeCacheSize。
#   此选项等效于-Xmaxjitcodesize。
# -XX:+TieredCompilation
#   默认情况下，启用此选项。只有Java HotSpot Server VM支持此选项。
# -XX:-TieredCompilation
#   禁用分层编译。
JIT_OPTS="-XX:ReservedCodeCacheSize=240m"

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

# GC G1参数
# -XX:+UseG1GC
#   允许使用垃圾优先（G1）垃圾收集器。
#   它是一个服务器式垃圾收集器，针对具有大量RAM的多处理器计算机。
#   它以高概率满足GC暂停时间目标，同时保持良好的吞吐量。
#   G1收集器推荐用于需要大堆（大小约为6GB或更大）且GC延迟要求有限的应用（稳定且可预测的暂停时间低于0.5秒）。
G1_GC_OPTS="-XX:+UseG1GC"

# GC 日志参数
# -verbose:gc
#   显示有关每个垃圾回收（GC）事件的信息
# -Xloggc
#   设置记录GC事件信息的日志文件
# -XX:+PrintGCApplicationConcurrentTime
#   允许打印自上次暂停以来经过的时间（例如，GC暂停）。默认情况下，禁用此选项。
# -XX:+PrintGCApplicationStoppedTime
#   允许打印暂停（例如，GC暂停）持续多长时间。默认情况下，禁用此选项。
# -XX:+PrintGCDateStamps
#   允许在每个GC上打印日期戳。默认情况下，禁用此选项。
# -XX:+PrintGCDetails
#   允许在每个GC上打印详细消息。默认情况下，禁用此选项。
GC_OPTS="-verbose:gc -Xloggc:/data/logs/gc/pay-gc.log -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCDateStamps -XX:+PrintGCDetails"

# 异常 日志参数
# -XX:-OmitStackTraceInFastThrow
# -XX:ErrorFile
# -XX:+HeapDumpOnOutOfMemoryError
# -XX:HeapDumpPath
ERROR_OPTS="-XX:+HeapDumpOnOutOfMemoryError -XX:-OmitStackTraceInFastThrow -XX:ErrorFile=${LOG_PATH}/hs_err_%p.log -XX:HeapDumpPath=${LOG_PATH}/hs_err.hprof"

# JMX 参数
JMX_PORT="18888"
JMX_OPTS="-Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=192.168.20.132 -Dcom.sun.management.jmxremote.port=${JMX_PORT} -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"

# 系统属性
# -Djava.net.preferIPv4Stack=true
#   优先使用IPV4，禁用IPV6
SYS_OPTS="-Djava.net.preferIPv4Stack=true"

export JAVA_OPTS="-server $SYS_OPTS $FLAG_OPTS $MEMORY_OPTS $JIT_OPTS $PERFORMANCE_OPTS $CMS_GC_OPTS $GC_OPTS $ERROR_OPTS $JMX_OPTS"

# 启动类
export MAIN_CLASS="com.github.tiger.pay.startup.Bootstrap"

nohup $_EXECJAVA $JAVA_OPTS -classpath $CLASSPATH $MAIN_CLASS >/dev/null 2>&1 &

# tail -f $LOG_PATH/stdout.log