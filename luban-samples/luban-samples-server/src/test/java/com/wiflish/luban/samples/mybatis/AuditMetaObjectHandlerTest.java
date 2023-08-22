package com.wiflish.luban.samples.mybatis;

import com.wiflish.luban.core.dto.CurrentUser;
import com.wiflish.luban.core.infra.po.FeatureBit;
import com.wiflish.luban.core.infra.po.FeatureJson;
import com.wiflish.luban.samples.BaseTests;
import com.wiflish.luban.samples.mybatis.domain.vo.UserAddress;
import com.wiflish.luban.samples.mybatis.infra.dao.UserDao;
import com.wiflish.luban.samples.mybatis.infra.po.UserPO;
import lombok.Data;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.Assert.*;

/**
 * @author wiflish
 * @since 2023-08-22
 */
public class AuditMetaObjectHandlerTest extends BaseTests {
    @Autowired
    private UserDao userDao;

    @Test
    public void insertAndUpdateFill() {
        //insert
        UserPO userPO = new UserPO();

        userPO.setName("Just for test");
        userPO.setEmail("lisi@qq.com");
        userPO.setFeatureBit(new FeatureBit(5L));

        UserAddress userAddress = new UserAddress();
        userAddress.setAddress("福田区 for test");
        userAddress.setZipcode("528000");

        userPO.setFeatureJson(new FeatureJson(userAddress));

        //模拟用户登录.
        LogonUser logonUser = new LogonUser();
        logonUser.setId(1024L);
        TestingAuthenticationToken auth = new TestingAuthenticationToken(logonUser, logonUser);
        SecurityContextHolder.getContext().setAuthentication(auth);

        userDao.insert(userPO);

        UserPO userPO1 = userDao.selectById(userPO.getId());
        assertNotNull(userPO1);
        assertNull(userPO1.getAge());
        assertEquals(1024, userPO1.getCreateId().longValue());
        assertEquals(1024, userPO1.getUpdateId().longValue());

        //模拟用户登录.
        logonUser = new LogonUser();
        logonUser.setId(2024L);
        auth = new TestingAuthenticationToken(logonUser, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        userPO1.setAge(100);
        userPO1.setCreateId(null);
        userPO1.setUpdateId(null);

        userDao.updateById(userPO1);

        userPO1 = userDao.selectById(userPO.getId());
        assertNotNull(userPO1);
        assertEquals(100, userPO1.getAge().intValue());
        assertEquals(1024, userPO1.getCreateId().longValue());
        assertEquals(2024, userPO1.getUpdateId().longValue());
    }

    @Data
    private static class LogonUser implements CurrentUser {
        private Long id;

        @Override
        public Long getUserId() {
            return id;
        }
    }
}