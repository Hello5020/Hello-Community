package com.hello.community.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import com.hello.community.exception.CustomizeErrorCode;
import com.hello.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.UUID;

@Component
public class UCloudProvider {
    @Value("${ucloud.us3.public-key}")
    private String publicKey;
    @Value("${ucloud.us3.private-key}")
    private String privateKey;


    @Value("${ucloud.us3.bucket-name}")
    private String bucketName;

    @Value("${ucloud.us3.region}")
    private String region;

    @Value("${ucloud.us3.suffix}")
    private String suffix;

    @Value("${ucloud.us3.expires}")
    private Integer expires;


    public  String upload(InputStream fileStream, String mimeType,String fileName){
        String generatedFileName;
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length > 1) {
            generatedFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        } else {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
        try {
            ObjectAuthorization OBJECT_AUTHORIZER =  new UfileObjectLocalAuthorization(publicKey, privateKey);
            ObjectConfig config = new ObjectConfig(region,suffix);

            PutObjectResultBean response = UfileClient.object(OBJECT_AUTHORIZER, config)
                    .putObject(fileStream, mimeType)
                    .nameAs(generatedFileName)
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
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(long bytesWritten, long contentLength) {

                        }
                    })
                    .execute();
                    if(response != null && response.getRetCode() == 0){
                        String url = UfileClient.object(OBJECT_AUTHORIZER, config)
                                .getDownloadUrlFromPrivateBucket(generatedFileName, bucketName, expires)
                                /**
                                 * 使用Content-Disposition: attachment，并且默认文件名为KeyName
                                 */
//                    .withAttachment()
                                /**
                                 * 使用Content-Disposition: attachment，并且配置文件名
                                 */
//                    .withAttachment("filename")
                                /**
                                 * 图片处理服务
                                 * https://docs.ucloud.cn/ufile/service/pic
                                 */
//                    .withIopCmd("iopcmd=rotate&degree=90")
                                .createUrl();

                                return url;
                    }else {
                        throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
                    }
        } catch (UfileClientException e) {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        } catch (UfileServerException e) {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }
    }
}
