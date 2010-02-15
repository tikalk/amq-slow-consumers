package com.tikal.amq.server.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created by IntelliJ IDEA.
 * User: shalom
 * Date: Feb 14, 2010
 * Time: 7:54:47 PM
 */
@ContextConfiguration(locations = {"classpath:/spring/spring-config-main.xml"})
public class AbstractSpringTests extends AbstractJUnit4SpringContextTests {
}