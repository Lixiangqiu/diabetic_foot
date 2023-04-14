package cn.edu.cqu.boot.controller;

import cn.edu.cqu.boot.config.Result;
import cn.edu.cqu.boot.entity.*;
import cn.edu.cqu.boot.mapper.CpMapper;
import cn.edu.cqu.boot.mapper.DcMapper;
import cn.edu.cqu.boot.service.ICpService;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cp")
public class CpController {

    @Value("8887")
    private String port;

    private static final String ip = "http://localhost";

    private String path;

    @Resource
    private CpMapper cpMapper;

    @Resource
    private DcMapper dcMapper;

    @Resource
    private ICpService cpService;

    //查找所有病历
    @RequestMapping(value = "/cpList")
    @ResponseBody
    public Result<?> searchAllData() {
        List<Cp> cpList = cpMapper.searchAll();
        return Result.success(cpList);
    }

    //通过病历ID查找病历
    @GetMapping(value = "/cpByCpId")
    @ResponseBody
    public Result<?> searchByCpId(@RequestParam int cpId) {
        Cp cpList = cpService.getById(cpId);
        return Result.success(cpList);
    }

    //通过医生ID查找病历
    @RequestMapping(value = "/cpByDoctorId")
    @ResponseBody
    public Result<?> searchByDoctorId(@RequestParam int doctorId) {
        List<Cp> cpList = cpMapper.searchByDoctorId(doctorId);
        return Result.success(cpList);
    }

    //通过病人ID查找病历并按时间排序
    @PostMapping(value = "/cpByPatientId")
    public Result<?> searchByPatientId(@RequestBody Patient p) {
        System.err.println("查到了");
        List<Cp> cpList = cpMapper.selectList(
                Wrappers.<Cp>query()
                        .lambda()
                        .eq(Cp::getPatientId, p.getPatientId())
                        .orderByDesc(Cp::getDate));

        System.out.println(cpList);
        System.err.println("查到了1");
        return Result.success(cpList);
    }

    @GetMapping(value = "/cpByPatientId1")
    public Result<?> searchByPatientId1(@RequestParam Integer id) {
        List<Cp> cpList = cpMapper.selectJoinList(Cp.class,
                new MPJLambdaWrapper<Cp>()
                        .selectAll(Cp.class)
                        .select(Doctor::getDoctorName, Doctor::getDoctorPosition)
                        .leftJoin(Doctor.class, Doctor::getDoctorId, Cp::getDoctorId)
                        .eq(Cp::getPatientId, id)
        );
        System.out.println(cpList);
        return Result.success(cpList);
    }

    @GetMapping(value = "/dcByCpId")
    public Result<?> searchByCpId(@RequestParam Integer id) {
        List<Dc> dcList = dcMapper.selectJoinList(Dc.class,
                new MPJLambdaWrapper<Dc>()
                        .selectAll(Dc.class)
                        .select(Doctor::getDoctorName, Doctor::getDoctorPosition)
                        .leftJoin(Doctor.class, Doctor::getDoctorId, Dc::getDoctorId)
                        .eq(Dc::getCpId, id)
//                        .orderByDesc(Dc::getDcDate)
        );
        System.out.println(dcList);
        return Result.success(dcList);
    }

//    //根据cpID在cp表修改诊断报告
//    @PostMapping("/updateCaseDesc")
//    public Result<?> updateCaseDesc(@RequestBody String caseDesc,
//                                    @RequestParam int cpId){
//        UpdateWrapper<Cp> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("cpId",cpId).set("caseDesc", caseDesc);
//        return Result.success(cpMapper.update(null, updateWrapper));
//    }

    //根据cpID在cp表修改诊断报告
    @PostMapping("/updateCaseDesc")
    public Result<?> updateCaseDesc(@RequestBody Cp cp) {
        return Result.success(cpMapper.updateById(cp));
    }


    @PostMapping("/uploadTxt")  //文件上传
    public Result<?> uploadTxt(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  // 获取源文件的名称
        // 定义文件的唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "\\springboot\\src\\main\\resources\\files\\" + flag + "_" + originalFilename;  // 获取上传的路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);  // 把文件写入到上传的路径
        path = rootFilePath;
        return Result.success(ip + ":" + port + "/files/" + flag);  // 返回结果 url
    }

    //通过本地txt文件插入数据
    @RequestMapping(value = "/insertCp")
    public Result<?> insertCp(@RequestParam Integer id, @RequestParam Integer doctorId) throws FileNotFoundException {
        try {
            System.err.print("传进来了");
            Cp cp = new Cp();
            File file = new File(path);
            System.err.print(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            //创建一个字符串对象，来存放读取到的文件内容
            String s = null;
            //创建一个cp对象的链表，用来存放cp对象
            //List<Cp> cpList = new LinkedList<>();
            //记录成功添加的数据数量
            int num = 0;
            //使用while循环来遍历读取的txt文件的内容
            while ((s = reader.readLine()) != null) {
                System.err.println("进循环了吗？");
                //创建一个String字符串数组，用来存放split方法按照“,”逗号分开的字符
                String[] dd = s.split(",");
                //转换字符串为Date格式
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //将字符串数组的值，通过user对象的set方法，来给user的属性赋值，其中id和age需要将字符串String类型的值转换成数值int类型
                cp.setDoctorId(doctorId);
                cp.setPatientId(id);
                cp.setDate(LocalDate.now());
                cp.setParaT1(Float.parseFloat(dd[0]));
                cp.setParaM1(Float.parseFloat(dd[1]));
                cp.setParaM2(Float.parseFloat(dd[2]));
                cp.setParaM3(Float.parseFloat(dd[3]));
                cp.setParaM4(Float.parseFloat(dd[4]));
                cp.setParaM5(Float.parseFloat(dd[5]));
                cp.setParaHL(Float.parseFloat(dd[6]));
                cp.setParaHM(Float.parseFloat(dd[7]));
                cp.setParaRT1(Float.parseFloat(dd[8]));
                cp.setParaRM1(Float.parseFloat(dd[9]));
                cp.setParaRM2(Float.parseFloat(dd[10]));
                cp.setParaRM3(Float.parseFloat(dd[11]));
                cp.setParaRM4(Float.parseFloat(dd[12]));
                cp.setParaRM5(Float.parseFloat(dd[13]));
                cp.setParaRHL(Float.parseFloat(dd[14]));
                cp.setParaRHM(Float.parseFloat(dd[15]));
                //cp.setCaseDesc(dd[8]);
                cpService.save(cp);
                //将属性已经赋值的cp对象，保存到Cp对象数组中
                //cpList.add(cp);
                num++;
            }
            reader.close();
            Result result = new Result<>();
            result.setCode(0);
            result.setMsg("添加成功" + num + "条数据");
            System.err.print("传进来了");
            return result;
        } catch (IOException e) {
            Result result = new Result<>();
            result.setCode(1);
            result.setMsg("添加失败");
            System.err.print("抛出了异常");
            return result;
        }
    }
}
