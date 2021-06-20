package top.onepecent.oneu.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.onepecent.oneu.dto.AccessTokenDTO;
import top.onepecent.oneu.dto.GithubUser;
import top.onepecent.oneu.mapper.UserMapper;
import top.onepecent.oneu.model.User;
import top.onepecent.oneu.provider.GithubProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientID;
    @Value("${github.client.uri}")
    private String clientUri;
    @Value("${github.client.secret}")
    private String clientSecret;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state, HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setRedirect_uri(clientUri);
        accessTokenDTO.setClient_secret(clientSecret);

        String token = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(token);
//网络不好时测试用
//        githubUser = new GithubUser();
//        githubUser.setName("名字"+code);
//        githubUser.setId(1066961902L);

        if(githubUser!=null) {
            User user = new User();
            user.setAccount_id(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);

            request.getSession().setAttribute("user", githubUser);
            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }
}
