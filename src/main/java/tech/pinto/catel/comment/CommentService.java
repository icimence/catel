package tech.pinto.catel.comment;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import tech.pinto.catel.order.MapperOrder;
import tech.pinto.catel.user.AccountMapper;
import tech.pinto.catel.comment.dto.DtoPublishComment;
import tech.pinto.catel.domain.Comment;
import tech.pinto.catel.domain.User;
import tech.pinto.catel.util.MapX;
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
    final private RepoComment repoComment;
    final private MapX mapX;

    @Autowired
    public CommentService(CommentMapper commentMapper, MapperOrder mapperOrder, AccountMapper accountMapper, RepoComment repoComment, MapX mapX) {
        this.commentMapper = commentMapper;
        this.mapperOrder = mapperOrder;
        this.accountMapper = accountMapper;
        this.repoComment = repoComment;
        this.mapX = mapX;
    }

    public void comment(DtoPublishComment dtoPublishComment) {
        Comment comment = mapX.toComment(dtoPublishComment);
        commentMapper.insert(comment);
    }

    public List<CommentVO> getComment(int hotelId) {

        var comments = repoComment.findByHotelId(hotelId);
        return comments.stream().map(comment -> {
            CommentVO commentVO = new CommentVO();
            BeanUtil.copyProperties(comment, commentVO, CopyOptions.create().ignoreNullValue());
            User user = accountMapper.select(comment.getUser().getId());
            commentVO.setUsername(user.getUsername());
            commentVO.setAvatar(user.getAvatar());
            return commentVO;
        }).collect(Collectors.toList());
    }

}
