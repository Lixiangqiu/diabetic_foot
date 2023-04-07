package cn.edu.cqu.boot.service.impl;

import cn.edu.cqu.boot.entity.AllUser;
import cn.edu.cqu.boot.entity.Comment;
import cn.edu.cqu.boot.mapper.AllUserMapper;
import cn.edu.cqu.boot.mapper.CommentMapper;
import cn.edu.cqu.boot.service.IAllUserService;
import cn.edu.cqu.boot.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

}
