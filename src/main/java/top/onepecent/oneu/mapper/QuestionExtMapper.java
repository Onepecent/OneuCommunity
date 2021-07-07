package top.onepecent.oneu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;
import top.onepecent.oneu.dto.QuestionQueryDTO;
import top.onepecent.oneu.model.Question;
import top.onepecent.oneu.model.QuestionExample;

import java.util.List;
@Repository
@Mapper
public interface QuestionExtMapper {
   int incView(Question record);
   int incCommentCount(Question record);
   List<Question> selectRelated(Question question);

   Integer countBySearch(QuestionQueryDTO questionQueryDTO);

   List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}