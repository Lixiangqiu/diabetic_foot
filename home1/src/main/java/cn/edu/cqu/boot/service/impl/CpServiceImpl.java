package cn.edu.cqu.boot.service.impl;

import cn.edu.cqu.boot.entity.AllUser;
import cn.edu.cqu.boot.entity.Cp;
import cn.edu.cqu.boot.mapper.AllUserMapper;
import cn.edu.cqu.boot.mapper.CpMapper;
import cn.edu.cqu.boot.service.IAllUserService;
import cn.edu.cqu.boot.service.ICpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CpServiceImpl extends ServiceImpl<CpMapper, Cp> implements ICpService {

    @Resource
    private CpMapper cpMapper;

    public List<Cp> searchAll(){
        return cpMapper.searchAll();
    }

    public List<Cp> searchByCpId(int cpId){
        return cpMapper.searchByCpId(cpId);
    }

    public List<Cp> searchByDoctorId(int doctorId){
        return cpMapper.searchByDoctorId(doctorId);
    }

    public List<Cp> searchByPatientId(int patientId){
        return cpMapper.searchByPatientId(patientId);
    }

    public int insertCp(Cp cp){
        return cpMapper.insertCp(cp);
    }

}
