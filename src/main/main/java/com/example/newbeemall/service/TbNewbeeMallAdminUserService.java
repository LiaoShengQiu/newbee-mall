package com.example.newbeemall.service;

import com.example.newbeemall.entity.TbNewbeeMallAdminUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public interface TbNewbeeMallAdminUserService extends IService<TbNewbeeMallAdminUser> {

    TbNewbeeMallAdminUser login(String loginName,String password);

}
