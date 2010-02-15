package com.tikal.amq.server.test;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: shalom
 * Date: Feb 14, 2010
 * Time: 7:58:21 PM
 */
public class DummyTest extends AbstractSpringTests{

    //just a test that will load the context and check for errors
    @Test
    public void dummy(){
        System.out.println("running dummy test");
    }
}