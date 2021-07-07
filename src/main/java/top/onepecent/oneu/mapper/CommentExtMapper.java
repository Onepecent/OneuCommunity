package top.onepecent.oneu.mapper;

import top.onepecent.oneu.model.Comment;
import top.onepecent.oneu.model.Question;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}
