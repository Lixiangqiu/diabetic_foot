/**
 * @Classname FileDelete
 * @Description TODO
 * @Date 2023/4/13 17:54
 * @Created by WangSanmu
 */
package cn.edu.cqu.boot.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.nio.file.Files;
import java.util.Objects;

public class FileDelete {

    private static String getLast(String[] str) {
        return str[str.length - 1];
    }

    public static void deleteFile(String url) {

        // 获取文件flag
//        String url = "http://localhost:8887/files/7e4e6b7f5763477f9dda789a5c200a88";
        String[] urlArray;
        urlArray = url.split("/");
        String flag = getLast(urlArray);
        System.out.println(flag);

        if (Objects.equals(flag, "cae603dda1974bc6bf347e4e2be2b703"))
            System.out.println("cae603dda1974bc6bf347e4e2be2b703不可删");
        else {

            // 获取文件name
            String basePath = System.getProperty("user.dir") + "/springboot/src/main/resources/files/"; // 文件目录
            List<String> fileNames = FileUtil.listFileNames(basePath); // 获取所有文件name
            String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
            System.out.println(fileName);

            // 尝试删除文件
            try {
                if (StrUtil.isNotEmpty(fileName)) {
                    Path filePath = Paths.get(basePath, fileName);
                    System.out.println(filePath);
                    Files.delete(filePath);
                }
            } catch (Exception e) {
                System.out.println("文件删除失败");
            }
        }
    }
}

