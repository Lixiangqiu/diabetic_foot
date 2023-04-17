/**
 * @Classname DcMapper
 * @Description TODO
 * @Date 2023/4/8 15:04
 * @Created by WangSanmu
 */
package cn.edu.cqu.boot.mapper;

import cn.edu.cqu.boot.entity.AllUser;
import cn.edu.cqu.boot.entity.Cp;
import com.github.yulichang.base.MPJBaseMapper;
import cn.edu.cqu.boot.entity.Dc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface DcMapper extends MPJBaseMapper<Dc> {
    public List<Dc> searchAll(); //查询所有用户信息

    public List<Dc> searchByCpId(@RequestParam int cpId);
}
