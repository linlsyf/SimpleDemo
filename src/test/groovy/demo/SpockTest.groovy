package com

import com.sun.xml.internal.ws.transport.http.server.PortableHttpHandler
import io.netty.handler.codec.http.HttpRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification;
import spock.lang.Unroll

import javax.xml.ws.spi.http.HttpHandler

/**
 * Spock 单元测试
 * @version 0.0.0
 * @since 0.0.0
 */
@SpringBootTest
class SpockTest extends Specification {
//    @Autowired
//    private LambdaTest lambdaTest
    @Autowired
    private JdbcService jdbcService;
//    @Autowired
//    private TimeHandler timeHandler;
    @Unroll
    def "readFold"() {
        jdbcService.test();
//        given:
//        println("given-----")
//
//        expect:
//        println("expect-----")
////        lambdaTest.findByName(name) == result
//        timeHandler.readFold();
//        cleanup:
//        println("cleanup-----")
//
//        where:
//        name | result
//        "a"  | "a"
//        "b"  | "b"
//        "kk" | null

    }
    @Unroll
    def "writeFilePerSec"() {
        given:
        println("given-----")

        expect:
        println("expect-----")
        timeHandler.writeFilePerSec();
        cleanup:
        println("cleanup-----")

        where:
        name | result
        "a"  | "a"
        "b"  | "b"
        "kk" | null

    }
}