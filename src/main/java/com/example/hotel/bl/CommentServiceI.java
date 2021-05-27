package com.example.hotel.bl;

import com.example.hotel.vo.CommentVO;

import java.util.List;

public interface CommentServiceI {

    List<CommentVO> getComment(int id);

    void comment(CommentVO commentVO);

}
