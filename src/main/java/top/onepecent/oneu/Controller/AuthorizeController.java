package top.onepecent.oneu.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.onepecent.oneu.dto.AccessTokenDTO;
import top.onepecent.oneu.dto.GithubUser;
import top.onepecent.oneu.model.User;
import top.onepecent.oneu.provider.GithubProvider;
import top.onepecent.oneu.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    //    @Value("${github.client.id}")
    private String clientID = "dff6e34bfb612c86793b6264be6ba60a8bb3511d16cd47d52e06632a7aa0cc29";
    //    @Value("${github.client.uri}")
    private String clientUri = "http://101.132.149.20:8886/callback";
//    private String clientUri = "http://101.132.149.20:8080/callback";
    //    @Value("${github.client.secret}")
    private String clientSecret = "3c39e87ec18424d90822cf7700a0accf67adbb51fb76b650ec50436c43999ee8";

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state, HttpServletRequest request, HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setRedirect_uri(clientUri);
        accessTokenDTO.setClient_secret(clientSecret);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        //{"id":7687367,"login":"onepecent","name":"OnePecent",
        // "avatar_url":"https://portrait.gitee.com/uploads/avatars/user/2562/7687367_onepecent_1624536058.png",
        // "url":"https://gitee.com/api/v5/users/onepecent",
        // "html_url":"https://gitee.com/onepecent",
        // "followers_url":"https://gitee.com/api/v5/users/onepecent/followers",
        // "following_url":"https://gitee.com/api/v5/users/onepecent/following_url{/other_user}",
        // "gists_url":"https://gitee.com/api/v5/users/onepecent/gists{/gist_id}",
        // "starred_url":"https://gitee.com/api/v5/users/onepecent/starred{/owner}{/repo}",
        // "subscriptions_url":"https://gitee.com/api/v5/users/onepecent/subscriptions",
        // "organizations_url":"https://gitee.com/api/v5/users/onepecent/orgs",
        // "repos_url":"https://gitee.com/api/v5/users/onepecent/repos",
        // "events_url":"https://gitee.com/api/v5/users/onepecent/events{/privacy}",
        // "received_events_url":"https://gitee.com/api/v5/users/onepecent/received_events",
        // "type":"User","blog":null,"weibo":null,"bio":"","public_repos":0,"public_gists":0,"followers":0,
        // "following":0,"stared":0,"watched":3,"created_at":"2020-06-13T20:34:48+08:00",
        // "updated_at":"2021-06-24T20:00:58+08:00","email":null}
        if (githubUser != null) {
            User user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);

            user.setAvatarUrl(githubUser.getAvatar_url());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
