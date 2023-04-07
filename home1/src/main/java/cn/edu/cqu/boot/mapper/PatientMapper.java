package cn.edu.cqu.boot.mapper;

import cn.edu.cqu.boot.entity.Doctor;
import cn.edu.cqu.boot.entity.Patient;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;

import java.util.List;


public interface PatientMapper extends MPJBaseMapper<Patient> {
    public List<Patient> searchAll();
    public int addPatient(Patient patient);
}
