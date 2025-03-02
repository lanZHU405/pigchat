package pig.chat.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pig.chat.springboot.common.Result;
import pig.chat.springboot.exception.ServiceException;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${spring.ip}")
    String ip;

    @Value("${server.port}")
    String port;

    private static final String ROOT_PATH = System.getProperty("user.dir")+ File.separator+"files";

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();  //文件的原始名称
        String mainName = FileUtil.mainName(originalFilename);  //不带后缀的文件名
        String extName = FileUtil.extName(originalFilename);    //文件的后缀名
        if(!FileUtil.exist(ROOT_PATH)){
            FileUtil.mkdir(ROOT_PATH);  //如果文件的父级目录不存在，就创建
        }
        if (FileUtil.exist(ROOT_PATH+File.separator+originalFilename)){
            //如果当前上传的文件已经存在了，那么这个时候我就要重命名一个文件名称
            originalFilename = System.currentTimeMillis()+"_"+mainName+"."+extName;
        }

        File saveFile = new File(ROOT_PATH+File.separator+originalFilename);
        file.transferTo(saveFile);  //存储文件到本地磁盘

        String url = "http://"+ip+":"+port+"/file/download/"+originalFilename;
        return Result.success(url,"");
    }

    @GetMapping("download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        String filePath = ROOT_PATH + File.separator + fileName;

        if (!FileUtil.exist(filePath)) {
            throw new ServiceException("没有该路径：" + filePath);
        }

        // 设置响应头的 Content-Type
        response.setContentType("application/octet-stream"); // 或根据图片格式设置具体的 MIME 类型

        // 设置响应头的 Content-Disposition
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);

        // 设置响应的字符编码
        response.setCharacterEncoding("UTF-8");

        // 读取文件并写入响应流
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }



    /**
     * wang-editor富文本编辑器文件上传接口
     */
    @PostMapping("/wang/upload")
    public Map<String,Object> wangEditorUpload(MultipartFile file){
        String flag = System.currentTimeMillis()+"";
        String filename = file.getOriginalFilename();

        try {
            FileUtil.writeBytes(file.getBytes(),ROOT_PATH+File.separator+flag+"-"+filename);
            System.out.println(filename+"----上传成功");
            Thread.sleep(1L);
        } catch (Exception e) {
            System.out.println(filename+"----文件上传失败");
        }
        String http = "http://"+ip+":"+port+"/file/download/";
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("errno",0);
        resMap.put("data", CollUtil.newArrayList(Dict.create().set("url",http+flag+"-"+filename)));
        return resMap;
    }

}
