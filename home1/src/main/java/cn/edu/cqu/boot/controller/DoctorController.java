package cn.edu.cqu.boot.controller;

import cn.edu.cqu.boot.config.Result;
import cn.edu.cqu.boot.entity.*;
import cn.edu.cqu.boot.mapper.DcMapper;
import cn.edu.cqu.boot.mapper.DoctorMapper;
import cn.edu.cqu.boot.mapper.PatientMapper;
import cn.edu.cqu.boot.service.IDcService;
import cn.edu.cqu.boot.service.IDoctorService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Resource
    private IDoctorService doctorService;

    @Resource
    private IDcService dcService;

    @Resource
    private DoctorMapper doctorMapper;

    @Resource
    private PatientMapper patientMapper;

    @Resource
    private DcMapper dcMapper;

    /**
     * @Description 新增医生信息，只保存于表doctor
     * @Param [doctor]
     * @return cn.edu.cqu.boot.config.Result<Doctor>
     * @Date 2023/4/17 14:46
     * @Auther WangSanmu
     */
    @PostMapping("/saveData")  //管理员新增医生
    public Result<?> saveData(@RequestBody Doctor doctor) {
        doctorService.save(doctor);
        return Result.success();
    }

    /**
     * @Description 修改更新医生信息，只作用与doctor表
     * @Param [doctor]
     * @return cn.edu.cqu.boot.config.Result<doctor>
     * @Date 2023/4/17 14:50
     * @Auther WangSanmu
     */
    @PutMapping("/updateData")  //管理员更新增加
    public Result<?> updateData(@RequestBody Doctor doctor) {
        doctorService.updateById(doctor);
        return Result.success();
    }

    /**
     * @Description 删除医生信息，只作用于doctor表
     * @Param [id]
     * @return cn.edu.cqu.boot.config.Result<msg>
     * @Date 2023/4/17 14:53
     * @Auther WangSanmu
     */
    @DeleteMapping("/{id}")   //管理员删除医生
    public Result<?> deleteData(@PathVariable Long id) {
        doctorService.removeById(id);
        return Result.success();
    }

    @GetMapping("/findPage")   //查询医生
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "3") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        IPage<Doctor> doctorIPage = doctorMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<Doctor>query().lambda().like(Doctor::getDoctorName, search));
        System.out.println(doctorIPage);
        return Result.success(doctorIPage);
    }

//    @GetMapping("/findPage")   //查询医生
//    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
//                              @RequestParam(defaultValue = "3") Integer pageSize,
//                              @RequestParam(defaultValue = "") String search) {
//        IPage< >
//        IPage<Doctor> doctorIPage = doctorMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<Doctor>query().lambda().like(Doctor::getDoctorName, search));
//        System.out.println(doctorIPage);
//        return Result.success(doctorIPage);
//    }

    @PostMapping("/showPatientByDoctorId") //根据医生ID选所属病人
    public Result<?> showPatientByDoctorId(@RequestParam int doctorId) {
        List<PatientCase> patientCaseList = doctorMapper.selectJoinList(PatientCase.class,
                new MPJLambdaWrapper<Patient>()
                        .selectAll(Patient.class)
                        .select(Cp::getCpId, Cp::getDate, Cp::getParaT1, Cp::getParaM1, Cp::getParaM2, Cp::getParaM3,
                                Cp::getParaM4, Cp::getParaM5, Cp::getParaHL, Cp::getParaHM, Cp::getCaseDesc)
                        .leftJoin(Cp.class, Cp::getDoctorId, Doctor::getDoctorId)
                        .leftJoin(Patient.class, Patient::getPatientId, Cp::getPatientId)
                        .eq(Doctor::getDoctorId, doctorId));
        if (patientCaseList != null) {

//            for(PatientCase p : patientCaseList){
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:HH");
//                // 商户订单号，商户网站订单系统中唯一订单号，必填
//                String out_trade_no = sdf.format(p.getDate());
//                p.setDate(sdf.parse());
//            }
            return Result.success(patientCaseList);
        } else {
            return Result.error(1, "没有数据");
        }
    }

    @GetMapping(value = "/showPublicCaseByDoctorId")
    public Result<?> showPublicCase(@RequestParam Integer doctorId) {
        List<PatientCase> patientCaseList = patientMapper.selectJoinList(PatientCase.class,
                new MPJLambdaWrapper<Patient>()
                        .selectAll(Patient.class)
                        .select(Cp::getCpId, Cp::getDate, Cp::getParaT1, Cp::getParaM1, Cp::getParaM2, Cp::getParaM3,
                                Cp::getParaM4, Cp::getParaM5, Cp::getParaHL, Cp::getParaHM, Cp::getCaseDesc)
                        .select(Doctor::getDoctorName)
                        .leftJoin(Cp.class, Cp::getPatientId, Patient::getPatientId)
                        .leftJoin(Doctor.class, Doctor::getDoctorId ,Cp::getDoctorId)
                        .eq(Cp::getIsPublic, true)
                        .ne(Cp::getDoctorId, doctorId)
//                        .notInSql(Cp::getCpId, "select cpId from dc where doctorId=3")

        );
        Iterator<PatientCase> iterator = patientCaseList.iterator();
        List<Dc> DcList = dcMapper.selectList(Wrappers.<Dc>query().lambda().eq(Dc::getDoctorId, doctorId));
        Iterator<Dc> iterator1 = DcList.iterator();
        while(iterator.hasNext()) {
            PatientCase next = iterator.next();
            Integer cpId = next.getCpId();
            while(iterator1.hasNext()) {
                Dc next1 = iterator1.next();
                Integer dc_cpId = next1.getCpId();
                if (Objects.equals(cpId, dc_cpId)) {
                    iterator.remove();
                }
            }
        }
        if (patientCaseList != null) {
            return Result.success(patientCaseList);
        } else {
            return Result.error(1, "没有数据");
        }
    }


    @PostMapping("/showCaseByDoctorIdPatientId") //根据病人ID和医生ID查所有病历
    public Result<?> showCaseByDPId(@RequestParam int doctorId, @RequestParam int patientId) {
        List<PatientCase> patientCaseList = doctorMapper.selectJoinList(PatientCase.class,
                new MPJLambdaWrapper<Patient>()
                        .selectAll(Patient.class)
                        .select(Cp::getCpId, Cp::getDate, Cp::getParaT1, Cp::getParaM1, Cp::getParaM2, Cp::getParaM3,
                                Cp::getParaM4, Cp::getParaM5, Cp::getParaHL, Cp::getParaHM, Cp::getCaseDesc)
                        .leftJoin(Cp.class, Cp::getDoctorId, Doctor::getDoctorId)
                        .leftJoin(Patient.class, Patient::getPatientId, Cp::getPatientId)
                        .eq(Doctor::getDoctorId, doctorId)
                        .eq(Patient::getPatientId, patientId));
        if (patientCaseList != null) {
            return Result.success(patientCaseList);
        } else {
            return Result.error(1, "没有数据");
        }
    }

    @GetMapping("/showCaseByDoctorIdPatientName") //根据医生ID和病人名字搜索某个病人的所有病历
    public Result<?> showCaseByDIdPName(@RequestParam int doctorId,
                                        @RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "5") Integer pageSize,
                                        @RequestParam String patientName) {
        System.err.println("chabingren");
        IPage<PatientCase> patientCaseIPage = doctorMapper.selectJoinPage(new Page<>(pageNum, pageSize), PatientCase.class,
                new MPJLambdaWrapper<Patient>()
                        .selectAll(Patient.class)
                        .select(Cp::getCpId, Cp::getDate, Cp::getParaT1, Cp::getParaM1, Cp::getParaM2, Cp::getParaM3,
                                Cp::getParaM4, Cp::getParaM5, Cp::getParaHL, Cp::getParaHM, Cp::getCaseDesc)
                        .leftJoin(Cp.class, Cp::getDoctorId, Doctor::getDoctorId)
                        .leftJoin(Patient.class, Patient::getPatientId, Cp::getPatientId)
                        .eq(Doctor::getDoctorId, doctorId)
                        .like(Patient::getPatientName, patientName)
                        .orderByDesc(Cp::getDate));
        if (patientCaseIPage != null) {
            return Result.success(patientCaseIPage);
        } else {
            return Result.error(1, "没有数据");
        }
    }
}
