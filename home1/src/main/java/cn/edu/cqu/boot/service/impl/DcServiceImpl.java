/**
 * @Classname DcServiceImpl
 * @Description TODO
 * @Date 2023/4/8 17:32
 * @Created by WangSanmu
 */
package cn.edu.cqu.boot.service.impl;


import cn.edu.cqu.boot.entity.Cp;
import cn.edu.cqu.boot.entity.Dc;
import cn.edu.cqu.boot.mapper.CpMapper;
import cn.edu.cqu.boot.mapper.DcMapper;
import cn.edu.cqu.boot.service.IDcService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.base.MPJBaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DcServiceImpl extends MPJBaseServiceImpl<DcMapper, Dc> implements IDcService {
    @Resource
    private DcMapper DcMapper;

    public List<Dc> searchAll() {
        return DcMapper.searchAll();
    }

    public List<Dc> searchByCpId(int cpId) {
        return DcMapper.searchByCpId(cpId);
    }
}
