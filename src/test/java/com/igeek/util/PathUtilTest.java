package com.igeek.util;

import com.igeek.BaseTest;
import org.junit.Test;

public class PathUtilTest extends BaseTest {

    @Test
    public void testSeparator(){
        String separator = System.getProperty("file.separator");
        System.out.println(separator);
    }

}
