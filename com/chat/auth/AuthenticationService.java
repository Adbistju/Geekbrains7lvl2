package com.chat.auth;

import com.chat.entity.User;
import java.util.Optional;

public interface AuthenticationService {
    Optional<User> doAuth(String login, String password);
    int doRegist(String login, String password, String nickName);
    int doChangeNick(String nickName,String password);
}
