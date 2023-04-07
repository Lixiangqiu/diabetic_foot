package cn.edu.cqu.boot.mapper;

import cn.edu.cqu.boot.entity.AllUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AllUserMapper extends MPJBaseMapper<AllUser> {
    public List<AllUser> searchAll(); //查询所有用户信息
    public AllUser searchUserById(int userId);//根据用户ID查询用户数据
    public int userRegister(AllUser user); //用户注册
    public List<AllUser> userLogin(//用户登录
                                @Param("username")String username,
                                @Param("password")String password
    );
    public int userUpdateUser(AllUser user); //用户数据自更新
    public int userUpdateAdmin(AllUser user); //管理员更新用户数据


}
