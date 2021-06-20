package top.onepecent.oneu.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.onepecent.oneu.dto.AccessTokenDTO;
import top.onepecent.oneu.dto.GithubUser;
import top.onepecent.oneu.provider.GithubProvider;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setRedirect_uri(clientUri);
        accessTokenDTO.setClient_secret(clientSecret);

        String token = githubProvider.getAccessToken(accessTokenDTO);
        githubProvider.getUser(token);
        return "index";
    }
}
