package com.example.newbeemall.mapper;

import com.example.newbeemall.entity.TbNewbeeMallUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.newbeemall.utils.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
public interface TbNewbeeMallUserMapper extends BaseMapper<TbNewbeeMallUser> {

    List<TbNewbeeMallUser> findMallUserList(PageQueryUtil pageUtil);

    int getTotalMallUsers(PageQueryUtil pageUtil);

    int lockUserBatch(@Param("ids") Integer[] ids, @Param("lockStatus") int lockStatus);
}
