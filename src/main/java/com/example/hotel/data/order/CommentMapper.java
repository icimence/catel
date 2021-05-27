package com.example.hotel.data.order;

import com.example.hotel.po.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert(value = "insert into hotel.Comment(user_id, hotel_id, score, title, content) values (#{comment.userId} ," +
            "#{comment.hotelId} ,#{comment.score} ,#{comment.title} ,#{comment.content} )")
    @Options(useGeneratedKeys = true, keyProperty = "comment.id", keyColumn = "id")
    void insert(@Param("comment") Comment comment);

    @Select(value = "select * from hotel.Comment where hotel_id=#{hotelId} ")
    List<Comment> select(@Param("hotelId") int hotelId);

}
