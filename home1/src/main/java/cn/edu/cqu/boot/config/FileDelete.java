/**
 * @Classname FileDelete
 * @Description TODO
 * @Date 2023/4/13 17:54
 * @Created by WangSanmu
 */

package cn.edu.cqu.boot.config;

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
import java.nio.file.Files;

//public class FileDelete {
//
//    private String photoUrl;
//    public deletePhoto(String photoUrl) {
//
//    }
//
//}

//public class FileDelete<T> {
//    private Integer code;
//    private String msg;
//    private String photoUrl;
//    private T data;
//
//    public static String deletePhoto(String url) {
//        String originalFilename = file.getOriginalFilename();  // 获取源文件的名称
//        // 定义文件的唯一标识（前缀）
//        String flag = IdUtil.fastSimpleUUID();
//        String rootFilePath = System.getProperty("user.dir") + "/springboot/src/main/resources/files/" + flag + "_" + originalFilename;  // 获取上传的路径
//        FileUtil.writeBytes(file.getBytes(), rootFilePath);  // 把文件写入到上传的路径
//        System.err.println("165165561313");
//        return Result.success(ip + ":" + port + "/files/" + flag);  // 返回结果 url
//    }
//
//    public Integer getCode() {
//        return code;
//    }
//
//    public void setCode(Integer code) {
//        this.code = code;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }
//
//    public Result() {
//    }
//
//    public Result(T data) {
//        this.data = data;
//    }
//
//    public static Result success() {
//        Result result = new Result<>();
//        result.setCode(0);
//        result.setMsg("成功");
//        return result;
//    }
//
//    public static <T> Result<T> success(T data) {
//        Result<T> result = new Result<>(data);
//        result.setCode(0);
//        result.setMsg("成功");
//        return result;
//    }
//
//    public static Result error(Integer code, String msg) {
//        Result result = new Result();
//        result.setCode(code);
//        result.setMsg(msg);
//        return result;
//    }
//}
//
