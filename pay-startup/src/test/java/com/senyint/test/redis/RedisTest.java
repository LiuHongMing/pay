package com.senyint.test.redis;

import com.senyint.common.redis.JedisTemplate;
import com.senyint.common.redis.RedisClient;
import com.senyint.pay.dto.OutTradeNoDTO;
import com.senyint.pay.dto.TradeOrderDTO;
import com.senyint.pay.dto.TradeRecordDTO;
import com.senyint.pay.service.TradeService;
import com.senyint.test.ServiceJunitTest;
import com.senyint.test.jmh.FirstBenchmarkTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPoolConfig;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Fork(2)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
public class RedisTest {

    RedisClient redisClient;

    @Setup
    public void setup() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisTemplate jedisTemplate = new JedisTemplate(jedisPoolConfig, "redis-server", 19000);
        redisClient = new RedisClient(jedisTemplate);
    }

    @Benchmark
    public void testSet() throws Exception {
        redisClient.set("lhm", "hello world");
    }

    @Benchmark
    public void testGet() throws Exception {
        redisClient.get("lhm");
    }

    @Benchmark
    public void testKeys() throws Exception {
        redisClient.keys("mr:*");
    }

    @Benchmark
    public void testDel() throws Exception {
        redisClient.del("mr:id:NA5Xq-29", "mr:id:V9DBL-37");
    }

}
