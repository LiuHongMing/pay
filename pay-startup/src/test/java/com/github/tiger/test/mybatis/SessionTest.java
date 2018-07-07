package com.github.tiger.test.mybatis;

import com.github.tiger.test.mybatis.dao.ActorMapper;
import com.github.tiger.test.mybatis.model.ActorDO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.testng.annotations.Test;

import java.io.InputStream;

/**
 * @author liuhongming
 */
public class SessionTest {

    static SqlSessionFactory sessionFactory;

    String environment = "dev";

    static {

        InputStream configInStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream("mybatis-config.xml");

        sessionFactory = new SqlSessionFactoryBuilder()
                .build(configInStream);
    }

    @Test
    public void testMapper() {

        SqlSession sqlSession = sessionFactory.openSession();

        ActorMapper mapper = sqlSession.getMapper(ActorMapper.class);

        ActorDO res = mapper.getActor(1);

        System.out.println(res);

        sqlSession.close();
    }

}
