package cn.edu.cqu.boot.controller;

import cn.edu.cqu.boot.config.Result;
import cn.edu.cqu.boot.entity.*;
import cn.edu.cqu.boot.mapper.DoctorMapper;
import cn.edu.cqu.boot.service.IDoctorService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
//@Controller
@RequestMapping("/doctor")
public class DoctorController {
    @Resource
    private IDoctorService doctorService;

    @Resource
    private DoctorMapper doctorMapper;

    @PostMapping("/saveData")  //管理员增加医生
    public Result<?> saveData(@RequestBody Doctor doctor){
        doctorService.save(doctor);
        return Result.success();
    }

    @PutMapping("/updateData")  //管理员更新增加
    public Result<?> updateData(@RequestBody Doctor doctor){
        doctorService.updateById(doctor);
        return Result.success();
    }

    @DeleteMapping("/{id}")   //管理员删除医生
    public Result<?> deleteData(@PathVariable Long id){
        doctorService.removeById(id);
        return Result.success();
    }

    @GetMapping("/findPage")   //查询医生
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "3") Integer pageSize,
                              @RequestParam(defaultValue = "") String search)
    {
        IPage<Doctor> doctorIPage = doctorMapper.selectPage(new Page<>(pageNum,pageSize),Wrappers.<Doctor>query().lambda().like(Doctor::getDoctorName,search));
        System.out.println(doctorIPage);
        return Result.success(doctorIPage);
    }

    @PostMapping("/showPatientByDoctorId") //根据医生ID选所属病人
    public Result<?> showPatientByDoctorId(@RequestParam int doctorId){
        List<PatientCase> patientCaseList = doctorMapper.selectJoinList(PatientCase.class,
                new MPJLambdaWrapper<Patient>()
                        .selectAll(Patient.class)
                        .select(Cp::getCpId,Cp::getDate,Cp::getParaT1,Cp::getParaM1,Cp::getParaM2,Cp::getParaM3,
                                Cp::getParaM4, Cp::getParaM5,Cp::getParaHL,Cp::getParaHM,Cp::getCaseDesc)
                        .leftJoin(Cp.class,Cp::getDoctorId,Doctor::getDoctorId)
                        .leftJoin(Patient.class,Patient::getPatientId,Cp::getPatientId)
                        .eq(Doctor::getDoctorId,doctorId));
        if(patientCaseList!=null){

//            for(PatientCase p : patientCaseList){
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH");
//                // 商户订单号，商户网站订单系统中唯一订单号，必填
//                String out_trade_no = sdf.format(p.getDate());
//                p.setDate(sdf.parse());
//            }
            return Result.success(patientCaseList);
        }
        else {
            return Result.error(1,"没有数据");
        }
    }

    @PostMapping("/showCaseByDoctorIdPatientId") //根据病人ID和医生ID查所有病历
    public Result<?> showCaseByDPId(@RequestParam int doctorId,@RequestParam int patientId){
        List<PatientCase> patientCaseList = doctorMapper.selectJoinList(PatientCase.class,
                new MPJLambdaWrapper<Patient>()
                        .selectAll(Patient.class)
                        .select(Cp::getCpId,Cp::getDate,Cp::getParaT1,Cp::getParaM1,Cp::getParaM2,Cp::getParaM3,
                                Cp::getParaM4, Cp::getParaM5,Cp::getParaHL,Cp::getParaHM,Cp::getCaseDesc)
                        .leftJoin(Cp.class,Cp::getDoctorId,Doctor::getDoctorId)
                        .leftJoin(Patient.class,Patient::getPatientId,Cp::getPatientId)
                        .eq(Doctor::getDoctorId,doctorId)
                        .eq(Patient::getPatientId,patientId));
        if(patientCaseList!=null){
            return Result.success(patientCaseList);
        }
        else {
            return Result.error(1,"没有数据");
        }
    }

    @GetMapping("/showCaseByDoctorIdPatientName") //根据医生ID和病人名字搜索某个病人的所有病历
    public Result<?> showCaseByDIdPName(@RequestParam int doctorId,
                                        @RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "5") Integer pageSize,
                                        @RequestParam String patientName){
        System.err.println("chabingren");
        IPage<PatientCase> patientCaseIPage = doctorMapper.selectJoinPage(new Page<>(pageNum,pageSize),PatientCase.class,
                new MPJLambdaWrapper<Patient>()
                        .selectAll(Patient.class)
                        .select(Cp::getCpId,Cp::getDate,Cp::getParaT1,Cp::getParaM1,Cp::getParaM2,Cp::getParaM3,
                                Cp::getParaM4, Cp::getParaM5,Cp::getParaHL,Cp::getParaHM,Cp::getCaseDesc)
                        .leftJoin(Cp.class,Cp::getDoctorId,Doctor::getDoctorId)
                        .leftJoin(Patient.class,Patient::getPatientId,Cp::getPatientId)
                        .eq(Doctor::getDoctorId,doctorId)
                        .like(Patient::getPatientName,patientName)
                        .orderByDesc(Cp::getDate));
        if(patientCaseIPage!=null){
            return Result.success(patientCaseIPage);
        }
        else {
            return Result.error(1,"没有数据");
        }
    }
}
