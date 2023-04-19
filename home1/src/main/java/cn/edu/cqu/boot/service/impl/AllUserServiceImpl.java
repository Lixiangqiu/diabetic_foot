package cn.edu.cqu.boot.service.impl;

import cn.edu.cqu.boot.entity.AllUser;
import cn.edu.cqu.boot.mapper.AllUserMapper;
import cn.edu.cqu.boot.service.IAllUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.base.MPJBaseServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AllUserServiceImpl extends MPJBaseServiceImpl<AllUserMapper, AllUser> implements IAllUserService {

    @Resource
    private AllUserMapper alluserMapper;

    public List<AllUser> searchAll(){
        return alluserMapper.searchAll();
    }
    public AllUser searchUserById(int userId){
        return alluserMapper.searchUserById(userId);
    }
    public int userRegister(AllUser user){
        return alluserMapper.userRegister(user);
    }
    public List<AllUser> userLogin(@Param("username")String username, @Param("password")String password){
        return alluserMapper.userLogin(username,password);
    }
    public int userUpdateUser(AllUser user){
        return alluserMapper.userUpdateUser(user);
    }
    public int userUpdateAdmin(AllUser user){
        return alluserMapper.userUpdateAdmin(user);
    }
}
