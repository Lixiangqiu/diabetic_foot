package cn.edu.cqu.boot.mapper;

import cn.edu.cqu.boot.entity.AllUser;
import cn.edu.cqu.boot.entity.Problem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProblemMapper extends MPJBaseMapper<Problem> {
    public List<Problem> searchAll();
    public Problem searchProblemById(int problemId);
    public int addProblem(Problem problem);
    public int updateProblemById(Problem problem);
    public int deleteProblemById(int problemId);
}
