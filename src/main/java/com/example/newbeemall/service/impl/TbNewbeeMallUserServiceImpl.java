package com.example.newbeemall.service.impl;

import com.example.newbeemall.entity.TbNewbeeMallUser;
import com.example.newbeemall.mapper.TbNewbeeMallUserMapper;
import com.example.newbeemall.service.TbNewbeeMallUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.utils.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
@Service
public class TbNewbeeMallUserServiceImpl extends ServiceImpl<TbNewbeeMallUserMapper, TbNewbeeMallUser> implements TbNewbeeMallUserService {

    @Resource
    private TbNewbeeMallUserMapper userMapper;

    @Override
    public PageResult getNewBeeMallUsersPage(PageQueryUtil pageUtil) {
        List<TbNewbeeMallUser> mallUsers = userMapper.findMallUserList(pageUtil);
        int total = userMapper.getTotalMallUsers(pageUtil);
        PageResult pageResult = new PageResult(mallUsers, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     */
    @Override
    public Boolean lockUsers(Integer[] ids, int lockStatus) {
        if (ids.length < 1) {
            return false;
        }
        return userMapper.lockUserBatch(ids, lockStatus) > 0;
    }
}
