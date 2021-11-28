package com.example.hotel.blImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.hotel.bl.CommentServiceI;
import com.example.hotel.data.order.CommentMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.dto.DtoPublishComment;
import com.example.hotel.model.Comment;
import com.example.hotel.model.Order;
import com.example.hotel.model.User;
import com.example.hotel.util.AllMapper;
import com.example.hotel.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    final private CommentMapper commentMapper;
    final private OrderMapper orderMapper;
    final private AccountMapper accountMapper;

    @Autowired
    public CommentService(CommentMapper commentMapper, OrderMapper orderMapper, AccountMapper accountMapper) {
        this.commentMapper = commentMapper;
        this.orderMapper = orderMapper;
        this.accountMapper = accountMapper;
    }

    public void comment(DtoPublishComment dtoPublishComment) {
        Comment comment = AllMapper.X.toComment(dtoPublishComment);
        commentMapper.insert(comment);
    }

    public List<CommentVO> getComment(int hotelId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<Comment>().eq(Comment::getHotelId, hotelId);
        return commentMapper.selectList(queryWrapper).stream().map(comment -> {
            CommentVO commentVO = new CommentVO();
            BeanUtil.copyProperties(comment, commentVO, CopyOptions.create().ignoreNullValue());
            User user = accountMapper.select(comment.getUserId());
            commentVO.setUsername(user.getUsername());
            commentVO.setAvatar(user.getAvatar());
            return commentVO;
        }).collect(Collectors.toList());
    }

}
