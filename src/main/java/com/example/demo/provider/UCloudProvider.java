package com.example.demo.provider;


import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Service
public class UCloudProvider {
    @Value("${ucloud.ufile.public-key}")
    private String publicKey;

    @Value("${ucloud.ufile.private-key}")
    private String privateKey;

    @Value("${ucloud.ufile.bucket-name}")
    private String bucketName;

    @Value("${ucloud.ufile.bucket-region}")
    private String region;

    @Value("${ucloud.ufile.expires-duration}")
    private Integer duration;

    public String upload(InputStream fileStream,String mimeType,String fileName){
        String[] fileSplit = fileName.split("\\.");
        String generateFileName;
        if (fileSplit.length>1){
            generateFileName = UUID.randomUUID().toString()+"."+fileSplit[fileSplit.length-1];
        }else{
            return null;
        }

        try {
            // Bucket相关API的授权器
            ObjectAuthorization BUCKET_AUTHORIZER = new UfileObjectLocalAuthorization(publicKey,privateKey);
            // 对象操作需要ObjectConfig来配置您的地区和域名后缀
            ObjectConfig config = new ObjectConfig(region, "ufileos.com");
            PutObjectResultBean response = UfileClient.object(BUCKET_AUTHORIZER, config)
                    .putObject(fileStream, mimeType)
                    .nameAs(generateFileName)
                    .toBucket(bucketName)
                    /**
                     * 是否上传校验MD5, Default = true
                     */
                    //  .withVerifyMd5(false)
                    /**
                     * 指定progress callback的间隔, Default = 每秒回调
                     */
                    //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                    /**
                     * 配置进度监听
                     */
                    .setOnProgressListener((bytesWritten, contentLength) -> {

                    })
                    .execute();

                    if (response!=null&&response.getRetCode()==0){
                        String url = UfileClient.object(BUCKET_AUTHORIZER, config)
                                .getDownloadUrlFromPrivateBucket(generateFileName, bucketName, duration)
                                .createUrl();
                        return url;
                    }else{
                        throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_ERROR);
                    }

        } catch (
                UfileClientException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_ERROR);
        } catch (
                UfileServerException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_ERROR);
        }
    }


}
