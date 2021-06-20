package top.onepecent.oneu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.onepecent.oneu.model.User;

@Mapper
@Repository
public interface UserMapper {

    public void insert(User user);
}
