package com.exam.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.base.RestResponse;
import com.exam.base.SystemCode;
import com.exam.entity.TUser;
import com.exam.mapper.TUserMapper;
import com.exam.service.TUserService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName : Utils
 * @Description :
 * @Author : y
 * @Date: 2021-07-26 19:19
 */

@Component
public class Utils {

    public static String logTypeTrans(Integer type){
        String logType = "";
        switch (type){
            case 1:
                logType = "登录了上弦月在线考试系统";
                break;
            case 2:
                logType = "退出上弦月在线考试系统";
                break;
            case 3:
                logType = "进行了快速开始训练";
                break;
            case 4:
                logType = "进行了专项训练";
                break;
            case 5:
                logType = "进行了随机组卷训练";
                break;
            case 6:
                logType = "";
                break;
        }
        return logType;
    }

    public static String changeLevelName(Integer level){
        String levelName = "";
        switch (level){
            case 1:
                levelName = "初一";
                break;
            case 2:
                levelName = "初二";
                break;
            case 3:
                levelName = "初三";
                break;
            case 4:
                levelName = "高一";
                break;
            case 5:
                levelName = "高二";
                break;
            case 6:
                levelName = "高三";
                break;
        }
        return levelName;
    }

    public static String getCurrentUserId(TUserMapper userMapper){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();

        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", principal);

        TUser user = userMapper.selectOne(wrapper);

        return user.getId();
    }

    public static TUser getCurrentUser(TUserMapper userMapper) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();

        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", principal);

        TUser user = userMapper.selectOne(wrapper);
        return user;
    }

        /**
         * 专门负责上传文件到 OSS 服务器的工具方法
         *
         * @param endpoint        OSS 参数
         * @param accessKeyId     OSS 参数
         * @param accessKeySecret OSS 参数
         * @param inputStream     要上传的文件的输入流
         * @param bucketName      OSS 参数
         * @param bucketDomain    OSS 参数
         * @param originalName    要上传的文件的原始文件名
         * @return 包含上传结果以及上传的文件在 OSS 上的访问路径
         */
    public static RestResponse<String> uploadFileToOss(
            String endpoint,
            String accessKeyId,
            String accessKeySecret,
            InputStream inputStream,
            String bucketName,
            String bucketDomain,
            String originalName) {

        // 创建 OSSClient 实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 生成上传文件的目录
        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());
        // 生成上传文件在 OSS 服务器上保存时的文件名
        // 原始文件名：beautfulgirl.jpg
        // 生成文件名：wer234234efwer235346457dfswet346235.jpg
        // 使用 UUID 生成文件主体名称
        String fileMainName = UUID.randomUUID().toString().replace("-", "");
        // 从原始文件名中获取文件扩展名
        String extensionName = originalName.substring(originalName.lastIndexOf("."));
        // 使用目录、文件主体名称、文件扩展名称拼接得到对象名称
        String objectName = folderName + "/" + fileMainName + extensionName;
        try {
            // 调用 OSS 客户端对象的方法上传文件并获取响应结果数据
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName,
                    inputStream);
            // 从响应结果中获取具体响应消息
            ResponseMessage responseMessage = putObjectResult.getResponse();
            // 根据响应状态码判断请求是否成功
            if (responseMessage == null) {
                // 拼接访问刚刚上传的文件的路径
                String ossFileAccessPath = bucketDomain + "/" + objectName;
                // 当前方法返回成功
                return RestResponse.ok(ossFileAccessPath);
            } else {
                // 获取响应状态码
                int statusCode = responseMessage.getStatusCode();
                // 如果请求没有成功，获取错误消息
                String errorMessage = responseMessage.getErrorResponseAsString();
                // 当前方法返回失败
                return RestResponse.fail(SystemCode.UPLOADERR.getCode(),SystemCode.UPLOADERR.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 当前方法返回失败
            return RestResponse.fail(SystemCode.InnerError.getCode(),SystemCode.InnerError.getMessage());
        } finally {
            if (ossClient != null) {
                // 关闭 OSSClient。
                ossClient.shutdown();
            }
        }

    }

}
