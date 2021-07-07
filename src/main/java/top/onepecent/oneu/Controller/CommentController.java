package top.onepecent.oneu.Controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.onepecent.oneu.dto.CommentCreateDTO;
import top.onepecent.oneu.dto.CommentDTO;
import top.onepecent.oneu.dto.ResultDTO;
import top.onepecent.oneu.enums.CommentTypeEnum;
import top.onepecent.oneu.exception.CustomizeErrorCode;
import top.onepecent.oneu.model.Comment;
import top.onepecent.oneu.model.User;
import top.onepecent.oneu.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    @ResponseBody
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
//            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

//        if (commentCreateDTO==null||commentCreateDTO.getContent()==null ||commentCreateDTO.getContent() == ""){
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment, user);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDTO<List<CommentDTO>> comments(@PathVariable("id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
