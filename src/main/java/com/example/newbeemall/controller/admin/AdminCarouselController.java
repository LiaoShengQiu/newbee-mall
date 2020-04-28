package com.example.newbeemall.controller.admin;

import com.example.newbeemall.entity.TbNewbeeMallCarousel;
import com.example.newbeemall.entity.TbNewbeeMallIndexConfig;
import com.example.newbeemall.service.TbNewbeeMallCarouselService;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.utils.ResultGenerator;
import com.example.newbeemall.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminCarouselController {

    @Resource
    private TbNewbeeMallCarouselService carouselService;

    @GetMapping("/carousels")
    public String carousels(@RequestParam Map<String, Object> params){
        return "admin/newbee_mall_carousel";
    }

    @GetMapping("/carousels/list")
    @ResponseBody
    public Object carouselsList(@RequestParam Map<String, Object> params){
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(carouselService.getCarouselPage(pageUtil));
    }

    @GetMapping("/carousels/edit")
    @ResponseBody
    public Object edit(@RequestParam Map<String, Object> params){
        return "";
    }

    @GetMapping("/carousels/info/{id}")
    @ResponseBody
    public Object info(@PathVariable("id") Long id) {
        TbNewbeeMallCarousel entity = carouselService.getById(id);
        if (entity == null) {
            return ResultGenerator.genFailResult("未查询到数据");
        }
        return ResultGenerator.genSuccessResult(entity);
    }

    @PostMapping("/carousels/save")
    @ResponseBody
    public Object save(@RequestBody TbNewbeeMallCarousel entity){
        entity.setCreateTime(LocalDateTime.now());
        return new ResultUtil(carouselService.save(entity));
    }

    @PostMapping("/carousels/update")
    @ResponseBody
    public Object update(@RequestBody TbNewbeeMallCarousel entity){
        entity.setUpdateTime(LocalDateTime.now());
        return new ResultUtil(carouselService.updateById(entity));
    }

    @PostMapping("/carousels/delete")
    @ResponseBody
    public Object delete(@RequestBody Integer[] ids){
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (carouselService.deleteIds(ids)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }
}
