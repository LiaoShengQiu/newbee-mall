package com.example.newbeemall.controller.admin;

import com.example.newbeemall.common.IndexConfigTypeEnum;
import com.example.newbeemall.entity.TbNewbeeMallIndexConfig;
import com.example.newbeemall.service.TbNewbeeMallCarouselService;
import com.example.newbeemall.service.TbNewbeeMallIndexConfigService;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.utils.Result;
import com.example.newbeemall.utils.ResultGenerator;
import com.example.newbeemall.utils.ResultUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Ids;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminGoodsConfigController {

    @Resource
    private TbNewbeeMallIndexConfigService indexConfigService;

    @GetMapping("/indexConfigs")
    public String indexConfigsPage(HttpServletRequest request, @RequestParam("configType") int configType) {
        IndexConfigTypeEnum indexConfigTypeEnum = IndexConfigTypeEnum.getIndexConfigTypeEnumByType(configType);
        if (indexConfigTypeEnum.equals(IndexConfigTypeEnum.DEFAULT)) {
            return "error/error_5xx";
        }

        request.setAttribute("path", indexConfigTypeEnum.getName());
        request.setAttribute("configType", configType);
        return "admin/newbee_mall_index_config";
    }

    @GetMapping("/indexConfigs/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> params){
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(indexConfigService.getConfigsPage(pageUtil));
        //return indexConfigService.getConfigsPage(pageUtil);
    }

    @GetMapping("/indexConfigs/edit")
    @ResponseBody
    public Object edit(@RequestParam Map<String, Object> params){
        return "";
    }

    @PostMapping("/indexConfigs/save")
    @ResponseBody
    public Object save(@RequestBody TbNewbeeMallIndexConfig entity){
        entity.setCreateTime(LocalDateTime.now());
        return new ResultUtil(indexConfigService.save(entity));
    }

    @PostMapping("/indexConfigs/update")
    @ResponseBody
    public Object update(@RequestBody TbNewbeeMallIndexConfig entity){
        entity.setUpdateTime(LocalDateTime.now());
        return new ResultUtil(indexConfigService.updateById(entity));
    }

    @PostMapping("/indexConfigs/delete")
    @ResponseBody
    public Object delete(@RequestBody Integer[] ids){

        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (indexConfigService.deleteIds(ids)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

    @GetMapping("/indexConfigs/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        TbNewbeeMallIndexConfig config = indexConfigService.getById(id);
        if (config == null) {
            return ResultGenerator.genFailResult("未查询到数据");
        }
        return ResultGenerator.genSuccessResult(config);
    }
}
