package framework.security.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import framework.security.vo.UserDetailResponse;
import framework.utils.UserLoginUtil;

@CrossOrigin
@RestController
@RequestMapping(value = "api")
public class UserDataController {

    @GetMapping("web-player/user/get-user-data")
    public UserDetailResponse getDataWebPlayer() {
        return new UserDetailResponse(UserLoginUtil.getUsername());
    }

    @GetMapping("user/get-user-data")
    public UserDetailResponse getData() {
        return new UserDetailResponse(UserLoginUtil.getUsername());
    }

}
