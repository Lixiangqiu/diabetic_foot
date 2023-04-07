package cn.edu.cqu.boot.controller;

import cn.edu.cqu.boot.config.Result;
import cn.edu.cqu.boot.entity.Problem;
import cn.edu.cqu.boot.mapper.CommentMapper;
import cn.edu.cqu.boot.mapper.ProblemMapper;
import cn.edu.cqu.boot.service.ICommentService;
import cn.edu.cqu.boot.service.IProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Resource
    private IProblemService problemService;

    @Resource
    private ProblemMapper problemMapper;

    //查询所有问题
    @RequestMapping(value = "/problemList")
    public Result<?> searchAllData(){
        List<Problem> problemList = problemService.list();
        return Result.success(problemList);
    }

    //根据problemID查询问题
    @RequestMapping(value = "/problemById")
    public Result<?> searchUserById(@RequestParam int problemId){
        return Result.success(problemService.getById(problemId));
    }

    //新增问题
    @RequestMapping(value = "/addProblem")
    public Result<?> addProblem(@RequestBody Problem problem){
        try {
            problemService.save(problem);
//            problemMapper.addProblem(problem);
            Result<Problem> result = new Result<>();
            result.setCode(0);
            result.setMsg("添加成功");
            result.setData(problem);
            return result;
        }
        catch (Exception e){
            Result result = new Result<>();
            result.setCode(1);
            result.setMsg("添加失败");
            return result;
        }
    }

    //修改问题
    @RequestMapping(value = "/updateProblemById")
    public Result<?> updateProblemById(@RequestBody Problem problem){
        try {
            problemMapper.updateById(problem);
            Result<Problem> result = new Result<>();
            result.setCode(0);
            result.setMsg("添加成功");
            result.setData(problem);
            return result;
        }
        catch (Exception e){
            Result result = new Result<>();
            result.setCode(1);
            result.setMsg("添加失败");
            return result;
        }
    }
    //删除问题
    @RequestMapping(value = "deleteProblemById")
    public Result<?> deleteProblemById(@RequestParam Integer problemId){
        try {
            problemMapper.deleteById(problemId);
            Result result = new Result<>();
            result.setCode(0);
            result.setMsg("删除成功");
            return result;
        } catch (Exception e) {
            Result result = new Result<>();
            result.setCode(1);
            result.setMsg("删除失败");
            return result;
        }
    }
}
