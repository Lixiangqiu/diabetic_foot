package cn.edu.cqu.boot.controller;

import cn.edu.cqu.boot.config.Result;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    @Value("8887")
    private String port;

    private static final String ip = "http://localhost";

    /**
     * @Description 上传图片
     * @Param [file]
     * @return cn.edu.cqu.boot.config.Result<url>
     * @Date 2023/4/18 15:22
     * @Auther WangSanmu
     */
    @PostMapping("/upload")  //图片上传
    public Result<?> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 获取源文件的名称
        // 定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/springboot/src/main/resources/files/" + flag + "_" + originalFilename;  // 获取上传的路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);  // 把文件写入到上传的路径
        System.err.println("165165561313");
        return Result.success(ip + ":" + port + "/files/" + flag);  // 返回结果 url
    }

    @PostMapping("/uploadImg")  //富文本图片上传
    public Object uploadImg(MultipartFile file) throws IOException {
        String Filename = file.getOriginalFilename();  // 获取源文件的名称
        // 定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/springboot/src/main/resources/files/" + flag + "_" + Filename;  // 获取上传的路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);  // 把文件写入到上传的路径
        System.err.println("165165561313");
        String url = ip + ":" + port + "/files/" + flag;
        return JSONUtil.parseObj("{\"errno\":0,\"data\":[{\"url\":\"" + url + "\"}]}");
    }

    /**
     * 下载接口
     * @param flag
     * @param response
     */
    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os;  // 新建一个输出流对象
        String basePath = System.getProperty("user.dir") + "/springboot/src/main/resources/files/";  // 定于文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到跟参数一致的文件
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);  // 通过文件的路径读取文件字节流
                os = response.getOutputStream();   // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }
}
