package com.example.newbeemall.service;

import com.example.newbeemall.entity.TbNewbeeMallUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.utils.PageResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public interface TbNewbeeMallUserService extends IService<TbNewbeeMallUser> {

    PageResult getNewBeeMallUsersPage(PageQueryUtil pageUtil);

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     */
    Boolean lockUsers(Integer[] ids, int lockStatus);
}
