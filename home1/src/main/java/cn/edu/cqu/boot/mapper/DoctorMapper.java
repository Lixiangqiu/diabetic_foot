package cn.edu.cqu.boot.mapper;

import cn.edu.cqu.boot.entity.Doctor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;

import java.util.List;


public interface DoctorMapper extends MPJBaseMapper<Doctor> {
    public List<Doctor> searchAll();
    public Doctor searchDoctorByDoctorId(int doctorId);
}
