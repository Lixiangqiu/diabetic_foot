package cn.edu.cqu.boot.controller;

import cn.edu.cqu.boot.config.Result;
import cn.edu.cqu.boot.entity.*;
import cn.edu.cqu.boot.mapper.CommentMapper;
import cn.edu.cqu.boot.mapper.MessageMapper;
import cn.edu.cqu.boot.service.IAllUserService;
import cn.edu.cqu.boot.service.ICommentService;
import cn.edu.cqu.boot.service.IDoctorService;
import cn.edu.cqu.boot.service.IMessageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import jdk.vm.ci.meta.Local;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@RestController
//@Controller
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private ICommentService commentService;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private IAllUserService userService;

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private IMessageService messageService;

    Integer uid;
    String uname;
    String headpic;
    Comment ct = new Comment();

    @GetMapping("/ctInfo")
    public Result<?> ctInfo(){
        return Result.success(ct);
    }

    @PostMapping("/saveUser")  //保存用户信息
    public Result<?> saveUser(@RequestBody AllUser user){
        uid = user.getId();
        uname = user.getName();
        headpic = user.getPhoto();
        System.err.println("获取用户信息");
        return Result.success();
    }

    @PostMapping("/saveComment")  //增加帖子
    public Result<?> saveComment(@RequestBody Comment comment){
        comment.setCommentGood(0);
        comment.setCommentNum(0);
        comment.setCommentPersonId(uid);
        comment.setCommentPersonName(uname);
        comment.setCommentPersonPic(headpic);
        comment.setCommentTime(LocalDate.now());
        commentService.save(comment);
        System.err.println("存储最新评论");
        return Result.success();
    }

    @PostMapping("/saveCommentStatus")  //保存帖子内容
    public Result<?> saveCommentStatus(@RequestBody Comment comment){
        ct = comment;
        return Result.success();
    }

    @DeleteMapping("/delete{commentId}")
    public Result<?> deleteComment(@PathVariable Integer commentId){
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("commentId",commentId);
        messageMapper.delete(queryWrapper);
        commentService.removeById(commentId);
        return Result.success();
    }

    @PostMapping("/zan")   //点赞
    public Result<?> zan(@RequestBody Comment comment){
        comment.setCommentGood(comment.getCommentGood()+1);
        commentService.updateById(comment);
        return Result.success();
    }

    @GetMapping("/findPage")   //查询帖子
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "3") Integer pageSize,
                              @RequestParam(defaultValue = "") String search)
    {
        IPage<Comment> commentIPage = commentMapper.selectPage(new Page<>(pageNum,pageSize),Wrappers.<Comment>query().lambda().like(Comment::getCommentTitle,search));
        System.out.println(commentIPage);
        return Result.success(commentIPage);
    }

    @GetMapping("/findList")   //查询帖子order.setOrderDate(LocalDate.now());
    public Result<?> findList(@RequestParam(defaultValue = "") String search)
    {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("commentTitle",search);
        List<Comment> commentList = commentService.list(queryWrapper);
        return Result.success(commentList);
    }

}
