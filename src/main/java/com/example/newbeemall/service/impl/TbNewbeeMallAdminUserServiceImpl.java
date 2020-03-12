package com.example.newbeemall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.newbeemall.entity.TbNewbeeMallAdminUser;
import com.example.newbeemall.mapper.TbNewbeeMallAdminUserMapper;
import com.example.newbeemall.service.TbNewbeeMallAdminUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newbeemall.utils.Md5;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
@Service
public class TbNewbeeMallAdminUserServiceImpl extends ServiceImpl<TbNewbeeMallAdminUserMapper, TbNewbeeMallAdminUser> implements TbNewbeeMallAdminUserService {

    @Resource
    private TbNewbeeMallAdminUserMapper adminUserMapper;

    /**
     * 登录的方法
     * @param loginName
     * @param password
     * @return
     */
    @Override
    public TbNewbeeMallAdminUser login(String loginName, String password) {
        QueryWrapper<TbNewbeeMallAdminUser> wrapper = new QueryWrapper<>();
        wrapper.eq("login_user_name",loginName).eq("login_password", Md5.getMd5(password));
        return adminUserMapper.selectOne(wrapper);
    }
}
