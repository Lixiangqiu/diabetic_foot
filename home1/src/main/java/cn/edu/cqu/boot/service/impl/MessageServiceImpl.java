package cn.edu.cqu.boot.service.impl;

import cn.edu.cqu.boot.entity.AllUser;
import cn.edu.cqu.boot.entity.Message;
import cn.edu.cqu.boot.mapper.AllUserMapper;
import cn.edu.cqu.boot.mapper.MessageMapper;
import cn.edu.cqu.boot.service.IAllUserService;
import cn.edu.cqu.boot.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Resource
    private MessageMapper messageMapper;

}
