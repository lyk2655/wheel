package com.linyk3.wheel.controller;

import com.linyk3.wheel.pojo.dto.ImageUploadRes;
import com.linyk3.wheel.pojo.dto.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/image")
public class ImageController {

    @RequestMapping("/upload")
    public WebResponse<ImageUploadRes> uploadImage(HttpServletResponse response, @RequestParam MultipartFile file) {
        if (file.isEmpty()) {
            return new WebResponse().fail("上传失败，请选择文件");
        }

        String fileName = file.getOriginalFilename();
        String filePath = "C:\\Users\\linyk3\\Desktop\\imageFile\\";
        String uuid = UUID.randomUUID().toString();
        File dest = new File(filePath + uuid + fileName);
        try {
            file.transferTo(dest);
            log.info("上传成功");
            ImageUploadRes res = new ImageUploadRes();
            res.setUrl(dest.toString());
            response.setHeader("Content-Type","text/html");
            response.setHeader("Access-Control-Allow-Origin","*");
            return new WebResponse().success(res, "上传成功");

        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        return new WebResponse().fail("上传失败！");
    }

}
