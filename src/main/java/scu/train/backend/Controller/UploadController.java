package scu.train.backend.Controller;

import io.netty.util.internal.ResourcesUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scu.train.backend.Common.Result;
import scu.train.backend.Common.StatusCode;
import scu.train.backend.Dao.TbUserDao;
import scu.train.backend.Entity.TbUser;

import java.io.*;
import java.util.UUID;


@RestController
public class UploadController {
    @Autowired
    TbUserDao tbUserDao;


    //upload
    private  final String URL = "http://localhost:8080/";

    @ApiOperation("上传用户头像")
    @PostMapping("/user/uploadImage")
    @PreAuthorize("hasAnyAuthority('user','expert','admin')")
    public Result<String> userImageUpload(@RequestPart("avatar") MultipartFile file) {
        String str = saveFile(file);
        TbUser tbUser = (TbUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        tbUser.setAvatar(str);
        tbUserDao.updateByPrimaryKeySelective(tbUser);
        return new Result<>(true,StatusCode.OK,"上传成功","");
    }

    public String getSavePath() {
        // 这里需要注意的是ApplicationHome是属于SpringBoot的类
        // 获取项目下resources/static/img路径
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        // 保存目录位置根据项目需求可随意更改
        return applicationHome.getDir().getParentFile()
                .getParentFile().getAbsolutePath() + "\\src\\main\\resources\\static\\img\\";
    }
    public String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空！";
        }
        // 给文件重命名
        String fileName = UUID.randomUUID() + "." + file.getContentType()
                .substring(file.getContentType().lastIndexOf("/") + 1);
        try {
            // 获取保存路径
            String path = getSavePath();
            File files = new File(path, fileName);
            File parentFile = files.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdir();
            }
            file.transferTo(files);
            return URL+fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // 返回重命名后的文件名
    }
}



