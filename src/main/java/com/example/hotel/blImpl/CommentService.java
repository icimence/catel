package com.example.hotel.blImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.hotel.bl.CommentServiceI;
import com.example.hotel.data.order.CommentMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.po.Comment;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService implements CommentServiceI {

    final private CommentMapper commentMapper;
    final private OrderMapper orderMapper;
    final private AccountMapper accountMapper;

    @Autowired
    public CommentService(CommentMapper commentMapper, OrderMapper orderMapper, AccountMapper accountMapper) {
        this.commentMapper = commentMapper;
        this.orderMapper = orderMapper;
        this.accountMapper = accountMapper;
    }

    @Override
    public void comment(CommentVO commentVO) {
        Comment comment = new Comment();
        BeanUtil.copyProperties(commentVO, comment, CopyOptions.create().ignoreNullValue());
        Order order = orderMapper.getOrderById(commentVO.getOrderId());
        commentMapper.insert(comment);
        order.setCommentId(comment.getId());
        orderMapper.update(order);
    }

    @Override
    public List<CommentVO> getComment(int hotelId) {
        return commentMapper.select(hotelId).stream().map(comment -> {
            CommentVO commentVO = new CommentVO();
            BeanUtil.copyProperties(comment, commentVO, CopyOptions.create().ignoreNullValue());
            User user = accountMapper.select(comment.getUserId());
            commentVO.setUsername(user.getUsername());
            commentVO.setAvatar(user.getAvatar());
            return commentVO;
        }).collect(Collectors.toList());
    }

}
