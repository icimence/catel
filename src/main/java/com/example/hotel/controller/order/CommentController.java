package com.example.hotel.controller.order;

import com.example.hotel.blImpl.CommentService;
import com.example.hotel.dto.DtoPublishComment;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.ResponseVO;
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
    public ResponseVO comment(@RequestBody DtoPublishComment dtoPublishComment) {
        commentService.comment(dtoPublishComment);
        return ResponseVO.buildSuccess().setMessage(22);
    }

    /**
     * get hotel's comments
     * _in hotel id
     * _out comments
     */
    @GetMapping("/by-hotel")
    public ResponseVO selectByHotel(@RequestParam int id) {
        List<CommentVO> comments = commentService.getComment(id);
        return ResponseVO.buildSuccess(comments);
    }

}
