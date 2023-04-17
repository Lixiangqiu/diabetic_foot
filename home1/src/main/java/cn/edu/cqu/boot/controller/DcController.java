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
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/dc")
public class DcController {

    @Resource
    private IDcService dcService;

    @Resource
    private IPatientService patientService;

    @GetMapping(value = "/findDcPage")
    public Result<?> findDcPage(@RequestParam Integer doctorId) {
        List<DoctorCon> doctorConList = dcService.selectJoinList(DoctorCon.class,
                new MPJLambdaWrapper<Dc>()
                        .select(Dc::getDcId, Dc::getCpId, Dc::getDcDate, Dc::getDoctorCon)
                        .eq(Dc::getDoctorId, doctorId)
                        .select(Cp::getDate, Cp::getCaseDesc)
                        .selectAll(Patient.class)
                        .select(Doctor::getDoctorName)
                        .innerJoin(Cp.class, Cp::getCpId, Dc::getCpId)
                        .innerJoin(Patient.class, Patient::getPatientId, Cp::getPatientId)
                        .innerJoin(Doctor.class, Doctor::getDoctorId, Dc::getDoctorId)
        );

        if (doctorConList != null) {
            return Result.success(doctorConList);
        } else {
            return Result.error(1, "没有数据");
        }
    }

}