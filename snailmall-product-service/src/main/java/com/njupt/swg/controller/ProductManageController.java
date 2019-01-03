package com.njupt.swg.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.njupt.swg.common.Parameters;
import com.njupt.swg.common.resp.ServerResponse;
import com.njupt.swg.entity.Product;
import com.njupt.swg.service.IProductService;
import com.njupt.swg.vo.ProductDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author swg.
 * @Date 2019/1/2 17:32
 * @CONTACT 317758022@qq.com
 * @DESC 后台商品服务
 */
@RestController
@RequestMapping("/manage/product")
public class ProductManageController {
    @Autowired
    private IProductService productService;
    @Autowired
    private Parameters parameters;

    /**
     * 产品list
     */
    @RequestMapping("/list.do")
    public ServerResponse list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        return productService.list(pageNum,pageSize);
    }

    /**
     * 产品搜索
     */
    @RequestMapping("search.do")
    public ServerResponse<PageInfo> search(String productName,
                                           Integer productId,
                                           @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                           @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){

        return productService.search(productName,productId,pageNum,pageSize);
    }

    /**
     * 图片上传
     */
    @RequestMapping("upload.do")
    public ServerResponse upload(@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath("upload");
//        String targetFileName = fileService.upload(file,path);
//        String url = parameters.getImageHost()+targetFileName;

        Map fileMap = Maps.newHashMap();
//        fileMap.put("uri",targetFileName);
//        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);

    }

    /**
     * 产品详情
     */
    @RequestMapping("detail.do")
    public ServerResponse<ProductDetailVo> detail(Integer productId){
        return productService.detail(productId);
    }

    /**
     * 产品上下架
     */
    @RequestMapping("set_sale_status.do")
    public ServerResponse<String> set_sale_status(Integer productId,Integer status){
        return productService.set_sale_status(productId,status);
    }

    /**
     * 新增OR更新产品
     */
    @RequestMapping("save.do")
    public ServerResponse<String> productSave(Product product){
        return productService.saveOrUpdateProduct(product);
    }

    /**
     * 富文本上传图片
     */
    @RequestMapping("richtext_img_upload.do")
    public Map richtextImgUpload(@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Map resultMap = Maps.newHashMap();
        String path = request.getSession().getServletContext().getRealPath("upload");
//        String targetFileName = fileService.upload(file, path);
//        if (StringUtils.isBlank(targetFileName)) {
//            resultMap.put("success", false);
//            resultMap.put("msg", "上传失败");
//            return resultMap;
//        }
//        String url = parameters.getImageHost() + targetFileName;
        resultMap.put("success", true);
        resultMap.put("msg", "上传成功");
//        resultMap.put("file_path", url);
        response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
        return resultMap;
    }



}
