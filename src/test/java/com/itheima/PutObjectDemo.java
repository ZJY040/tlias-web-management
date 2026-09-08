package com.itheima;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.PutObjectResult;
import java.io.ByteArrayInputStream;

public class PutObjectDemo {
    public static void main(String[] args) throws Exception {
        // 1. 从环境变量中获取访问凭证
        EnvironmentVariableCredentialsProvider credentialsProvider = 
                CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        // 2. 配置OSS连接信息
        // 您的Bucket所在地域对应的Endpoint，例如：https://oss-cn-hangzhou.aliyuncs.com[reference:4]
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 您的Bucket名称[reference:5]
        String bucketName = "java-ai-aiiii";
        // 您希望文件在OSS中保存的完整路径，例如 "exampledir/object"[reference:6]
        String objectName = "exampledir/object";

        // 3. 创建OSSClient实例
        // 当OSSClient实例不再使用时，应调用shutdown方法以释放资源[reference:7]
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        try {
            // 4. 执行上传操作
            // 此处以上传字符串为例，您也可以替换为 FileInputStream 来上传本地文件[reference:8]
            String content = "Hello OSS";
            PutObjectResult result = ossClient.putObject(bucketName, objectName, 
                    new ByteArrayInputStream(content.getBytes()));

            // 5. 处理结果
            // 如果Bucket开启了版本控制，可以获取到文件的VersionId[reference:9]
            System.out.println("Upload succeeded. VersionId: " + result.getVersionId());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 6. 关闭OSSClient，释放资源
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}