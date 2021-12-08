package tech.pinto.catel.comment;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import tech.pinto.catel.order.MapperOrder;
import tech.pinto.catel.user.AccountMapper;
import tech.pinto.catel.comment.dto.DtoPublishComment;
import tech.pinto.catel.model.Comment;
import tech.pinto.catel.user.User;
import tech.pinto.catel.util.AllMapper;
import tech.pinto.catel.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    final private CommentMapper commentMapper;
    final private MapperOrder mapperOrder;
    final private AccountMapper accountMapper;

    @Autowired
    public CommentService(CommentMapper commentMapper, MapperOrder mapperOrder, AccountMapper accountMapper) {
        this.commentMapper = commentMapper;
        this.mapperOrder = mapperOrder;
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