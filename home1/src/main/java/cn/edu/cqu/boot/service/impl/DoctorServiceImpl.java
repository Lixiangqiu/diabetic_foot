package cn.edu.cqu.boot.service.impl;

import cn.edu.cqu.boot.entity.Doctor;
import cn.edu.cqu.boot.mapper.DoctorMapper;
import cn.edu.cqu.boot.service.IDoctorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements IDoctorService {

    @Resource
    private  DoctorMapper doctorMapper;

    //查询所有医生数据
    public List<Doctor> searchAll(){
        return doctorMapper.searchAll();
    }
    //根据doctorId查询医生数据
    public Doctor searchDoctorByDoctorId(int doctorId){
        return doctorMapper.searchDoctorByDoctorId(doctorId);
    }
}
