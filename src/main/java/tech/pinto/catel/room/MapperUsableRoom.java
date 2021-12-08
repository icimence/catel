package tech.pinto.catel.room;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import tech.pinto.catel.enums.UsableRoom;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MapperUsableRoom extends BaseMapper<UsableRoom> {
}
