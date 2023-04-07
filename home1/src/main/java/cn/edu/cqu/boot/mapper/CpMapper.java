package cn.edu.cqu.boot.mapper;

import cn.edu.cqu.boot.entity.AllUser;
import cn.edu.cqu.boot.entity.Cp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CpMapper extends MPJBaseMapper<Cp> {
    public List<Cp> searchAll();
    public List<Cp> searchByCpId(@RequestParam int cpId);
    public List<Cp> searchByDoctorId(@RequestParam int doctorId);
    public List<Cp> searchByPatientId(@RequestParam int patientId);
    public int insertCp(Cp cp);
}
