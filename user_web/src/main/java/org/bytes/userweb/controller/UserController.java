package org.bytes.userweb.controller;

import org.bytes.supermallpojo.pojo.user.User;
import org.bytes.supermallpojo.response.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response<Integer> login(@RequestBody User user) {
        try {
            System.out.println(user);
            return Response.data(1);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("登录失败");
        }
    }
}
