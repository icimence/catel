package tech.pinto.catel.comment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import tech.pinto.catel.domain.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    @Select(value = "select * from hotel.Comment where hotel_id=#{hotelId} ")
    List<Comment> select(@Param("hotelId") int hotelId);

}
