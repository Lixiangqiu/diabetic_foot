package cn.edu.cqu.boot.service.impl;

import cn.edu.cqu.boot.entity.AllUser;
import cn.edu.cqu.boot.entity.Problem;
import cn.edu.cqu.boot.mapper.AllUserMapper;
import cn.edu.cqu.boot.mapper.ProblemMapper;
import cn.edu.cqu.boot.service.IAllUserService;
import cn.edu.cqu.boot.service.IProblemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements IProblemService {

    @Resource
    private ProblemMapper problemMapper;

    //查询所有问题
    public List<Problem> searchAll(){
        return problemMapper.searchAll();
    }
    //根据problemId查询问题
    public Problem searchProblemById(int problemId){
        return problemMapper.searchProblemById(problemId);
    }
    //添加问题
    public int addProblem(Problem problem){
        return problemMapper.addProblem(problem);
    }
    //修改问题
    public int updateProblemById(Problem problem){
        return problemMapper.updateProblemById(problem);
    }
    //删除问题
    public int deleteProblemById(int problemId){
        return problemMapper.deleteProblemById(problemId);
    }
}
