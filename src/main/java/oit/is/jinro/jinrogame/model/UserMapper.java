package oit.is.jinro.jinrogame.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Select("SELECT * from users")
  ArrayList<Users> selectAll();

  @Select("SELECT * from users where id = #{id}")
  Users selectById(int id);

  @Delete("DELETE FROM users WHERE ID =#{id}")
  boolean deleteById(int id);
}
