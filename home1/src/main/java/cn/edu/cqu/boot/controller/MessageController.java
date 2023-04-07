package cn.edu.cqu.boot.controller;

import cn.edu.cqu.boot.config.Result;
import cn.edu.cqu.boot.entity.AllUser;
import cn.edu.cqu.boot.entity.Comment;
import cn.edu.cqu.boot.entity.Message;
import cn.edu.cqu.boot.mapper.AllUserMapper;
import cn.edu.cqu.boot.mapper.CommentMapper;
import cn.edu.cqu.boot.mapper.MessageMapper;
import cn.edu.cqu.boot.service.IAllUserService;
import cn.edu.cqu.boot.service.ICommentService;
import cn.edu.cqu.boot.service.IMessageService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController{
    @Resource
    private MessageMapper messageMapper;

    @Resource
    private IMessageService messageService;

    @Resource
    private AllUserMapper allUserMapper;

    @Resource
    private IAllUserService userService;

    @Resource
    private ICommentService commentService;

    @Resource
    private CommentMapper commentMapper;

    @PostMapping("/saveMessage")
    public Result<?> saveMessage(@RequestBody Message Message) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("commentId",Message.getCommentId());
        Comment c = commentMapper.selectOne(queryWrapper);
        c.setCommentNum(c.getCommentNum()+1);
        commentMapper.updateById(c);
        Message.setMessageTime(DateUtil.formatDateTime(new Date()));
        return Result.success(messageMapper.insert(Message));
    }

    @DeleteMapping("/delete{id}")
    public Result<?> delete(@PathVariable Integer id) {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parentId",id); //查找子级评论
        List<Message> messageList = messageMapper.selectList(queryWrapper);
        int count = 0;
        for(Message m :messageList){
            count++;
        }
        QueryWrapper<Message> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("messageId",id); //找到本条评论
        Message m = messageMapper.selectOne(queryWrapper2);
        QueryWrapper<Comment> queryWrapper1 = new QueryWrapper<>(); //找到本条帖子
        queryWrapper1.eq("commentId",m.getCommentId());
        Comment c = commentMapper.selectOne(queryWrapper1);
        c.setCommentNum(c.getCommentNum()-1-count);
        commentMapper.updateById(c);
        messageMapper.delete(queryWrapper);
        messageMapper.deleteById(id);
        return Result.success();
    }

    // 查询所有数据
    @GetMapping("/messageList{id}")
    public Result<?> foreign(@PathVariable Integer id) {
        return Result.success(findByComment(id));
    }

    private List<Message> findByComment(Integer commentId) {
        LambdaQueryWrapper<Message> queryWrapper = Wrappers.<Message>lambdaQuery().eq(Message::getCommentId, commentId);
        List<Message> list = messageMapper.selectList(queryWrapper);
        // 循环所有留言数据
        for (Message Message : list) {
            AllUser one = allUserMapper.selectOne(Wrappers.<AllUser>lambdaQuery().eq(AllUser::getName, Message.getUserName()));
            if (StrUtil.isNotBlank(one.getPhoto())){
                Message.setPhoto(one.getPhoto());
            } else {
                // 默认一个头像
                Message.setPhoto("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
            }
            Integer parentId = Message.getParentId();
            // 判断当前的留言是否有父级，如果有，则返回父级留言的信息
            // 原理：遍历所有留言数据，如果id跟当前留言信息的parentId相等，则将其设置为父级评论信息，也就是Message::setParentMessage
            list.stream().filter(c -> c.getMessageId().equals(parentId)).findFirst().ifPresent(Message::setParentMessage);
        }
        return list;
    }

}
