package tech.pinto.catel.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pinto.catel.comment.dto.DtoComment;
import tech.pinto.catel.comment.dto.DtoPublishComment;
import tech.pinto.catel.util.MapX;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    final private RepoComment repoComment;
    final private MapX mapX;

    @Autowired
    public CommentService(RepoComment repoComment, MapX mapX) {
        this.repoComment = repoComment;
        this.mapX = mapX;
    }

    public void comment(DtoPublishComment dtoPublishComment) {
        var comment = mapX.toComment(dtoPublishComment);
        repoComment.save(comment);
    }

    public List<DtoComment> getComment(int hotelId) {
        var comments = repoComment.findByHotelId(hotelId);
        return comments.stream().map(mapX::toDtoComment).collect(Collectors.toList());
    }

}
