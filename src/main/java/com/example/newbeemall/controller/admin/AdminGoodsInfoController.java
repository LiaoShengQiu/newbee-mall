package com.example.newbeemall.controller.admin;


import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import com.example.newbeemall.entity.TbNewbeeMallGoodsInfo;
import com.example.newbeemall.mapper.TbNewbeeMallGoodsInfoMapper;
import com.example.newbeemall.service.TbNewbeeMallGoodsCategoryService;
import com.example.newbeemall.service.TbNewbeeMallGoodsInfoService;
import com.example.newbeemall.utils.PageQueryUtil;
import com.example.newbeemall.utils.PageResult;
import com.example.newbeemall.utils.ResultGenerator;
import com.example.newbeemall.utils.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ***
 * @since 2020-02-07
 */
@Controller
@RequestMapping("/admin")
public class AdminGoodsInfoController {

    @Resource
    private TbNewbeeMallGoodsInfoService goodsInfoService;
    @Resource
    private TbNewbeeMallGoodsCategoryService goodsCategoryService;
    @Resource
    private TbNewbeeMallGoodsInfoMapper tbNewbeeMallGoodsInfoMapper;

    @RequestMapping("/goods")
    public String goodsInfo(){
        return "admin/newbee_mall_goods.html";
    }

    @RequestMapping("/goods/edit")
    public String toGoodsEdit(HttpServletRequest request){
        //商品分类-一级分类
        List<TbNewbeeMallGoodsCategory> firstLevelCate = goodsCategoryService.getFirstLevelCate();
        //第一个一级分类下面的二级分类
        List<TbNewbeeMallGoodsCategory> secondLevelCategories = goodsCategoryService.getlistForSelect(firstLevelCate.get(0).getCategoryId());
        //第一个二级分类下的三级分类
        List<TbNewbeeMallGoodsCategory> thirdLevelCategories = goodsCategoryService.getlistForSelect(secondLevelCategories.get(0).getCategoryId());
        request.setAttribute("firstLevelCategories",firstLevelCate);
        request.setAttribute("secondLevelCategories",secondLevelCategories);
        request.setAttribute("thirdLevelCategories",thirdLevelCategories);
        return "admin/newbee_mall_goods_edit";
    }

    //异步返回所有商品数据-分页查询
    @RequestMapping("/goods/list")
    @ResponseBody
    public Object goodsList(@RequestParam Map<String,Object> map, HttpSession session){
        System.out.println(map.toString()+"===========");
        Integer page = Integer.parseInt(map.get("page").toString());
        Integer limit = Integer.parseInt(map.get("limit").toString());

       /* System.out.println(page+"\t aaa"+limit);
        Map mp = new HashMap();
        mp = goodsInfoService.getList(page,limit);
        System.out.println("============="+mp.size());
        List<TbNewbeeMallGoodsInfo> data = new ArrayList<TbNewbeeMallGoodsInfo>();
        data = (List<TbNewbeeMallGoodsInfo>) mp.get("list");
        map.put("totalPage",mp.get("totalPage"));
        map.put("currPage",mp.get("currPage"));
        map.put("totalCount",mp.get("totalCount"));
//                goodsInfoService.getList(page,limit);
        return data;*/

        map.put("start",(page-1)*limit);
        PageQueryUtil pageUtil = new PageQueryUtil(map);
        //查询条件page=页数，limit=每页记录数，sidx=按什么排序，order=降序还是升序
        PageResult data = goodsInfoService.searchsp(pageUtil);
        System.out.println("=="+data.getTotalCount()+"总记录=======");
        return ResultGenerator.genSuccessResult(data);
    }
    //修改商品-查询商品信息到页面
    @RequestMapping("/goods/edit/{goodsId}")
    public String goodsEdit(@PathVariable("goodsId")Integer goodsId,HttpServletRequest request){
        //商品分类-一级分类
        List<TbNewbeeMallGoodsCategory> firstLevelCate = goodsCategoryService.getFirstLevelCate();
        //要修改的商品
        TbNewbeeMallGoodsInfo info = goodsInfoService.getById(goodsId);
        //所属2级分类
        Long three = info.getGoodsCategoryId();
        //所属2级分类
        Long two = goodsCategoryService.findParentId(three);
        //所属1级分类
        Long one = goodsCategoryService.findParentId(two);
        //查询所属一级分类下的二级分类
        List<TbNewbeeMallGoodsCategory> secondLevelCategories = goodsCategoryService.getlistForSelect(one);
        //查询所属二级分类下的三级分类
        List<TbNewbeeMallGoodsCategory> thirdLevelCategories = goodsCategoryService.getlistForSelect(two);
        request.setAttribute("firstLevelCategoryId",one);
        request.setAttribute("secondLevelCategoryId",two);
        request.setAttribute("thirdLevelCategoryId",three);
        request.setAttribute("firstLevelCategories",firstLevelCate);
        request.setAttribute("secondLevelCategories",secondLevelCategories);
        request.setAttribute("thirdLevelCategories",thirdLevelCategories);
        request.setAttribute("goods",info);
        return "admin/newbee_mall_goods_edit.html";
    }

    @RequestMapping("/categories/listForSelect")
    @ResponseBody
    public Object listForSelect(@RequestParam("categoryId") Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<TbNewbeeMallGoodsCategory> list = new ArrayList<TbNewbeeMallGoodsCategory>();
        list = goodsCategoryService.getlistForSelect(id);
        System.out.println(list.toString());
        Map<String,Object> map = new HashMap();

        Map data = new HashMap();
        if(goodsCategoryService.isLevelTwo(id)){
            data.put("secondLevelCategories",list);
        }else {
            data.put("thirdLevelCategories", list);
        }
        map.put("data",data);
        map.put("resultCode",200);

        request.setAttribute("secondLevelCategories",list);
        request.setAttribute("resultCode",200);

        return map;
    }

    @RequestMapping("goods/status/0")
    @ResponseBody
    public Object upGoods(@RequestBody Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("显示+++++++++" + ids[0].toString());
        Map map = new HashMap();
        try{
            goodsInfoService.upGoods(ids);
            map.put("resultCode",200);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("goods/status/1")
    @ResponseBody
    public Object downGoods(@RequestBody Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("显示+++++++++" + ids[0].toString());
        Map map = new HashMap();
        try{
            goodsInfoService.downGoods(ids);
            map.put("resultCode",200);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("goods/update")
    @ResponseBody
    public Object update(@RequestParam TbNewbeeMallGoodsInfo entity){
        boolean save = goodsInfoService.updateById(entity);
        return new ResultUtil(save,save?"修改成功":"修改失败");
    }
    @RequestMapping("goods/save")
    @ResponseBody
    public Object save(@RequestParam TbNewbeeMallGoodsInfo entity
    ) throws IOException {
        boolean save = goodsInfoService.save(entity);
        return new ResultUtil(save,save?"保存成功":"保存失败");
//        String id = SysUtil.getUUID();


//        String fileName=file.getOriginalFilename();
//
//
//        String img=fileName.substring(fileName.lastIndexOf("."));
//        FileOutputStream imgOut=new FileOutputStream(new File(dir,img));//根据 dir 抽象路径名和 img 路径名字符串创建一个新 File 实例。
//        /* System.out.println(file.getBytes());*/
//        imgOut.write(file.getBytes());//返回一个字节数组文件的内容
//        imgOut.close();
//        System.out.println(fileName);




//        Map<String, String> map=new HashMap<String, String>();
//        map.put("path",img);
//        return JSON.toJSONString(map);



/**----------------------------------------------------------------------
 CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
 //获取前台提交图片
 if (multipartResolver.isMultipart(request)){
 MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
 String filepath = "C:\Users\25600\Desktop\结业项目\newbee-mall\src\main\resources\static\goods-img";
 Map<String, MultipartFile> fileMap = multiRequest.getFileMap();
 String fileName = null;
 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
 // 获取单个文件
 MultipartFile mf = entity.getValue(); // 获得原始文件名
 fileName = mf.getOriginalFilename(); // 截取文件类型; 这里可以根据文件类型进行判断
 String fileType = fileName.substring(fileName.lastIndexOf('.'));
 try {
 // 截取上传的文件名称
 String newFileName = fileName.substring(0, fileName.lastIndexOf('.'));
 //图片转换成io，存到数据库
 //                    byte[] image = mf.getBytes();
 // 拼接上传文件位置
 //                    System.out.println(image);
 System.out.println(newFileName);
 System.out.println(fileType);


 //保存到服务器
 String newfilePath = filepath + File.separatorChar + newFileName + fileType;
 System.out.println("拼接好的文件路径地址------------->>>>>>>>" + newfilePath);
 // 重新组装文件路径，用于保存在list集合中
 File dest = new File(filepath);
 // 判断文件夹不存在就创建
 if (!dest.exists()) {
 dest.mkdirs();
 }
 // 创建文件实例
 File uploadFile = new File(newfilePath);
 // 判断文件已经存在，则删除该文件
 //                    if (uploadFile.exists()) {
 //                        uploadFile.delete();
 //                    }
 } catch (Exception e) {
 e.printStackTrace();
 }
 }
 }
 -----------------------------------------------------------------
 **/


        /**-----------------------------------------------------------
         if (multipartRequest != null) {
         Iterator<String> iterator = multipartRequest.getFileNames();
         while (iterator.hasNext()) {
         MultipartFile file = multipartRequest.getFile((String) iterator.next());
         if (!file.isEmpty()) {
         System.out.println("获取文件MIME类型-" + file.getContentType());// 获取文件MIME类型

         System.out.println("获取表单中文件组件的名字-" + file.getName());// 获取表单中文件组件的名字

         System.out.println("获取上传文件的原名-" + file.getOriginalFilename());// 获取上传文件的原名

         System.out.println("获取文件的字节大小，单位byte-" + file.getSize());// 获取文件的字节大小，单位byte

         String fileName = UUID.randomUUID().toString().replaceAll("-", "")
         + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));// 保存后的文件名
         System.out.println(fileName);

         try {
         // 文件保存路径
         String filePath = request.getServletContext().getRealPath("/") + "goods-img" + File.separator;
         File uploadFile = new File(filePath + fileName);
         uploadFile.mkdirs();
         file.transferTo(uploadFile);// 保存到一个目标文件中。
         //                        Map<String, Object> m = new HashMap<String, Object>();
         //                        m.put("fileUrl", "/attached/" + fileName);
         } catch (Exception e) {
         e.printStackTrace();
         }
         }
         }
         }
         -------------------------------------------------------------------------------------
         **/
    }
}

