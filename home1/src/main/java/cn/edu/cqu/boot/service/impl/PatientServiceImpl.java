package cn.edu.cqu.boot.service.impl;

import cn.edu.cqu.boot.entity.Doctor;
import cn.edu.cqu.boot.entity.Patient;
import cn.edu.cqu.boot.mapper.DoctorMapper;
import cn.edu.cqu.boot.mapper.PatientMapper;
import cn.edu.cqu.boot.service.IDoctorService;
import cn.edu.cqu.boot.service.IPatientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements IPatientService {

    @Resource
    private PatientMapper patientMapper;

    public List<Patient> searchAll(){
        return patientMapper.searchAll();
    }

    public int addPatient(Patient patient){
        return patientMapper.addPatient(patient);
    }
}
