package top.onepecent.oneu.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import top.onepecent.oneu.dto.AccessTokenDTO;
import top.onepecent.oneu.dto.GithubUser;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token?grant_type=authorization_code")
                //https://github.com/login/oauth/access_token
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            System.out.println(str);
            String token = str.split(",")[0].split(":")[1];
//            char[] c = token.toCharArray();
            String token1 = token.substring(1,token.length()-1);
            System.out.println(token1);
//            String token = str.split("&")[0].split("=")[1];
            return token1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token="+accessToken)
                //https://gitee.com/oauth/token?grant_type=authorization_code&code={code}&client_id={client_id}&redirect_uri={redirect_uri}&client_secret={client_secret}
                //https://api.github.com/user?access_token=
                .build();
        try {
            Response response = client.newCall(request).execute();
            String u = response.body().string();
            System.out.println(u);
            GithubUser githubUser = JSON.parseObject(u,GithubUser.class);
            System.out.println(githubUser.getName());
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
