package tech.pinto.catel.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import tech.pinto.catel.util.error.Impossible;
import tech.pinto.catel.vo.OssTokenVO;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

/*
用户登录名称 public@dici.onaliyun.com
登录密码 Woshimima
AccessKey ID LTAI4GHW8BiegBw6HgbMQwzy
AccessKey Secret LZtSjG9thJLJb9M7zpWJRv6dDfZASX
 */

@SuppressWarnings("ALL")
@Service
public class OssService {

    final private String accessKeyId;
    final private String accessKeySecret;
    private String regionId;
    private String StsEndpoint;
    private String OssEndpoint;
    private String roleArn;
    private String policy;

    {
        accessKeyId = "LTAI4GHW8BiegBw6HgbMQwzy";
        accessKeySecret = "LZtSjG9thJLJb9M7zpWJRv6dDfZASX";
    }

    public OssService() {init();}

    private void init() {
        regionId = "";
        StsEndpoint = "sts.cn-shanghai.aliyuncs.com";
        OssEndpoint = "oss-cn-shanghai.aliyuncs.com";
        roleArn = "";
        policy = null;
    }

    private AssumeRoleResponse getResponse() {
        DefaultProfile.addEndpoint(regionId, "Sts", StsEndpoint);
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        final AssumeRoleRequest request = new AssumeRoleRequest();
        request.setRoleArn(roleArn);
        request.setPolicy(policy);
        request.setDurationSeconds(1000L);
        request.setRoleSessionName("name");
        AssumeRoleResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            System.err.println("Failed: ");
            System.err.println("Error code: " + e.getErrCode());
            System.err.println("Error message: \n" + e.getMessage());
            System.err.println("\nRequest ID: " + e.getRequestId());
        }
        return response;
    }

    @SneakyThrows
    private OssTokenVO toToken(AssumeRoleResponse response) {
        if (null == response) throw new Impossible();
        OssTokenVO tokenVO = new OssTokenVO();
        tokenVO.setAccessKeyId(response.getCredentials().getAccessKeyId());
        tokenVO.setAccessKeySecret(response.getCredentials().getAccessKeySecret());
        tokenVO.setSecurityToken(response.getCredentials().getSecurityToken());
        tokenVO.setRegion("oss-cn-shanghai");
        return tokenVO;
    }

    @SneakyThrows
    public String savePublic(String bucket, String filename, String base64) {
        roleArn = "acs:ram::1310174151793582:role/hotel-res-img-public";
        AssumeRoleResponse response = getResponse();
        init();
        OssTokenVO tokenVO = toToken(response);
        String accessKeyId = tokenVO.getAccessKeyId();
        String secretAccessKey = tokenVO.getAccessKeySecret();
        String securityToken = tokenVO.getSecurityToken();
        OSS client = new OSSClientBuilder().build(OssEndpoint, accessKeyId, secretAccessKey, securityToken);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(base64);
        InputStream f = new ByteArrayInputStream(bytes);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(f.available());
        PutObjectResult result = client.putObject(bucket, filename, f, metadata);
        String pic = "https://" + bucket + "." + OssEndpoint + "/" + filename;
        return pic;
    }

}
