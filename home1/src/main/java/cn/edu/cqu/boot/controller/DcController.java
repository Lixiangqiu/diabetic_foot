/**
 * @Classname DcController
 * @Description TODO
 * @Date 2023/4/8 17:37
 * @Created by WangSanmu
 */
package cn.edu.cqu.boot.controller;

import cn.edu.cqu.boot.config.Result;
import cn.edu.cqu.boot.entity.*;
import cn.edu.cqu.boot.service.IDcService;
import cn.edu.cqu.boot.service.IPatientService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dc")
public class DcController {

    @Resource
    private IDcService dcService;

    @Resource
    private IPatientService patientService;

    /**
     * @return cn.edu.cqu.boot.config.Result<doctorCon>
     * @Description 医生建议展示，根据doctorId展示
     * @Param [doctorId]
     * @Date 2023/4/18 16:23
     * @Auther WangSanmu
     */
    @GetMapping(value = "/findDcPage")
    public Result<?> findDcPage(@RequestParam Integer doctorId) {
        List<DoctorCon> doctorConList = dcService.selectJoinList(DoctorCon.class,
                new MPJLambdaWrapper<Dc>()
                        .select(Dc::getDcId, Dc::getCpId, Dc::getDcDate, Dc::getDoctorCon)
                        .eq(Dc::getDoctorId, doctorId)
                        .select(Cp::getDate, Cp::getCaseDesc, Cp::getIsPublic)
                        .selectAll(Patient.class)
                        .select(Doctor::getDoctorName)
                        .innerJoin(Cp.class, Cp::getCpId, Dc::getCpId)
                        .innerJoin(Patient.class, Patient::getPatientId, Cp::getPatientId)
                        .innerJoin(Doctor.class, Doctor::getDoctorId, Cp::getDoctorId));

        if (doctorConList != null && !doctorConList.isEmpty()) {
            return Result.success(doctorConList);
        } else {
            return Result.error(1, "没有数据");
        }
    }


    /**
     * @return cn.edu.cqu.boot.config.Result<Dc>
     * @Description 新增dc，保存到数据库
     * @Param [dc]
     * @Date 2023/4/18 17:09
     * @Auther WangSanmu
     */
    @PostMapping(value = "newDoctorCon")
    public Result<?> createDc(@RequestBody Dc dc) {
        Dc localDc = dcService.getOne(
                Wrappers.<Dc>query().lambda().select()
                        .eq(Dc::getDoctorId, dc.getDoctorId())
                        .eq(Dc::getCpId, dc.getCpId())
        );
        if (localDc == null && dc.getDcId() == null) {
            dc.setDcDate(LocalDate.now());
            dcService.save(dc);
            return Result.success(dc);
        } else {
            dc.setDcDate(LocalDate.now());
            dcService.updateById(dc);
            return Result.success(dc);
        }
    }

}