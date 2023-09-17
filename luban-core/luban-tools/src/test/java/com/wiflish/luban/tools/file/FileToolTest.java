package com.wiflish.luban.tools.file;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author wiflish
 * @since 2023-09-17
 */
public class FileToolTest {

    @Test
    public void testGetSubdirectoriesStr() {
        String path = "/tmp";

        List<String> subdirectoriesStr = FileTool.getSubdirectoriesStr(path);

        assertNotNull(subdirectoriesStr);
        assertTrue(subdirectoriesStr.size() > 0);
    }
}