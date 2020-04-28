package com.example.newbeemall.controller.admin;


import com.example.newbeemall.entity.TbNewbeeMallGoodsCategory;
import com.example.newbeemall.entity.TbNewbeeMallGoodsInfo;
import com.example.newbeemall.service.TbNewbeeMallGoodsCategoryService;
import com.example.newbeemall.service.TbNewbeeMallGoodsInfoService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
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

    @RequestMapping("/goods")
    public String goodsInfo(){
        return "admin/newbee_mall_goods.html";
    }

/*    @RequestMapping("/goods/edit")
    public String toGoodsEdit(){
        return "admin/newbee_mall_goods_edit";
    }*/

    @RequestMapping("/goods/list")
    @ResponseBody
    public Object goodsList(@RequestParam Map<String,Object> map, HttpSession session){
        System.out.println("进入list++++++++++++++++++++++++++++++++++++++++++++++++++++");
        Integer page = Integer.parseInt(map.get("page").toString());
        Integer limit = Integer.parseInt(map.get("limit").toString());

        Map mp = new HashMap();
        mp = goodsInfoService.getList(page,limit);

        List<TbNewbeeMallGoodsInfo> data = new ArrayList<TbNewbeeMallGoodsInfo>();
        data = (List<TbNewbeeMallGoodsInfo>) mp.get("list");
        map.put("totalPage",mp.get("totalPage"));
        map.put("currPage",mp.get("currPage"));
        map.put("totalCount",mp.get("totalCount"));
//                goodsInfoService.getList(page,limit);
        return data;
    }

    @RequestMapping("/goods/edit")
    public String goodsEdit(HttpServletRequest request){
        List<TbNewbeeMallGoodsCategory> firstLevelCategories = new ArrayList();
        firstLevelCategories = goodsCategoryService.getFirstLevelCate();
        request.setAttribute("firstLevelCategories",firstLevelCategories);
        System.out.println(firstLevelCategories.toString());
        return "admin/newbee_mall_goods_edit.html";
    }

    @RequestMapping("/categories/listForSelect")
    @ResponseBody
    public Object listForSelect(@RequestParam("categoryId") Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    @RequestMapping("goods/save")
    @ResponseBody
    public Object save(HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        System.out.println("进入保存");
        Map<String, String[]> params = new HashMap<String, String[]>();
        params = (Map<String, String[]>) request.getParameterMap();
        System.out.println(request.getParameter("goodsName"));


        String path="C:\\Users\\25600\\Desktop\\结业项目\\newbee-mall\\src\\main\\resources\\static\\goods-img";

        //创建文件
        File dir=new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }

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
        return null;
    }
}

