package tech.pinto.catel.comment;

import tech.pinto.catel.comment.dto.DtoPublishComment;
import tech.pinto.catel.vo.CommentVO;
import tech.pinto.catel.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {this.commentService = commentService;}

    /**
     * comment a finished order
     * _in comment content, commented hotel id, score for service, finished order id
     */
    @PostMapping("/")
    public Response comment(@RequestBody DtoPublishComment dtoPublishComment) {
        commentService.comment(dtoPublishComment);
        return Response.buildSuccess().setMessage(22);
    }

    /**
     * get hotel's comments
     * _in hotel id
     * _out comments
     */
    @GetMapping("/by-hotel")
    public Response selectByHotel(@RequestParam int id) {
        var comments = commentService.getComment(id);
        return Response.buildSuccess(comments);
    }
    
}
