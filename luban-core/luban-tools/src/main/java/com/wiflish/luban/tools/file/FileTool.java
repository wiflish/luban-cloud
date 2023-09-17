package com.wiflish.luban.tools.file;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author wiflish
 * @since 2023-09-17
 */
@Slf4j
public class FileTool {
    public static List<Path> getSubdirectories(String pathStr) {
        Path path = Path.of(pathStr);
        try {
            if (!Files.exists(path)) {
                return Collections.emptyList();
            }
            if (!Files.isDirectory(path)) {
                return Collections.emptyList();
            }

            List<Path> result = CollectionUtil.newArrayList();
            getSubdirectories(path, result);
            return result;
        } catch (Exception e) {
            log.warn("获取文件夹下的子目录出现异常, 目录: {}", pathStr, e);
        }
        return Collections.emptyList();
    }

    public static List<String> getSubdirectoriesStr(String pathStr) {
        List<Path> subdirectories = getSubdirectories(pathStr);
        return subdirectories.stream().map(Path::toString).toList();
    }

    private static void getSubdirectories(Path path, List<Path> result) {
        if (result.size() > 50000) {
            throw new RuntimeException("超过上限50000. 请缩小范围。");
        }
        try {
            try (Stream<Path> list = Files.list(path)) {
                List<Path> paths = list.filter(Files::isDirectory).toList();
                for (Path subdir : paths) {
                    result.add(subdir);
                    getSubdirectories(subdir, result);
                }
            }
        } catch (Exception e) {
            log.warn("获取文件夹下的子目录出现异常, 目录: {}", path, e);
        }
    }
}
