package org.bytes.userserver;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.bytes.supermallpojo.pojo.user.User;
import org.bytes.userserver.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserTest{

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testQueryUser() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", 1);
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }
}
