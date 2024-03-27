package framework.utils;

import framework.model.ImgUploadReq;
import framework.model.ImgUploadRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ImgUploadUtils {

    @Value("${path.img.api}")
    String imgApi;

    @Value("${path.img.defaultPath}")
    String path;

    @Transactional
    public ImgUploadRes uploadImg(String prefix, String imgBase64 , String path) throws Exception {
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null, null, null);

        CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLContext(context).build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);

        String url = imgApi + "/api/img/upload";
        HttpHeaders headers = new HttpHeaders();
        // prepare entity req
        ImgUploadReq imgUploadReq = new ImgUploadReq();
        imgUploadReq.setData(imgBase64);
        imgUploadReq.setPrefix(prefix);
        imgUploadReq.setPath(this.path+'/'+path);

        System.out.println(url);
        HttpEntity<ImgUploadReq> reqEntity = new HttpEntity<ImgUploadReq>(imgUploadReq, headers);

        ResponseEntity<ImgUploadRes> result = null;
        try {
            result = restTemplate.exchange(url, HttpMethod.POST, reqEntity, ImgUploadRes.class);
        } catch (Exception e) {
            throw new Exception("REQUEST ERROR");
        }
        if (result.getStatusCode() != HttpStatus.OK) {
            log.info("================== IMG UPLOAD =================");
            log.info("STATUS : " + result.getStatusCode().toString());
            throw new Exception("REQUEST ERROR");
        }
        return result.getBody();
    }

    @Transactional
    public ResponseEntity<ImgUploadRes> delete(String filename) throws Exception {
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null, null, null);

        CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLContext(context).build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);

        String url = imgApi + "/api/img/delete?path=" + filename;
        HttpHeaders headers = new HttpHeaders();
        // prepare entity req
        ImgUploadReq imgUploadReq = new ImgUploadReq();

        HttpEntity<ImgUploadReq> reqEntity = new HttpEntity<ImgUploadReq>(imgUploadReq, headers);

        ResponseEntity<ImgUploadRes> result = null;
        try {
            result = restTemplate.exchange(url, HttpMethod.GET, reqEntity, ImgUploadRes.class);
        } catch (Exception e) {
            throw new Exception("REQUEST ERROR");
        }
        if (result.getStatusCode() != HttpStatus.OK) {
            log.info("================== IMG UPLOAD =================");
            log.info("STATUS : " + result.getStatusCode().toString());
            throw new Exception("REQUEST ERROR");
        }
        return result;
    }

    @Transactional
    public ResponseEntity<ImgUploadRes> uploadImg(String prefix, List<String> imgBase64) throws Exception {
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null, null, null);

        CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLContext(context).build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);

        String url = imgApi + "/api/img/upload-multiple";
        HttpHeaders headers = new HttpHeaders();
        // prepare entity req
        List<ImgUploadReq> imgUploadReqs = new ArrayList<>();
        for (String i : imgBase64
        ) {
            ImgUploadReq imgUploadReq = new ImgUploadReq();
            imgUploadReq.setData(i);
            imgUploadReq.setPrefix(prefix);
            imgUploadReqs.add(imgUploadReq);
        }

        HttpEntity<List<ImgUploadReq>> reqEntity = new HttpEntity<List<ImgUploadReq>>(imgUploadReqs, headers);

        ResponseEntity<ImgUploadRes> result = null;
        try {
            result = restTemplate.exchange(url, HttpMethod.POST, reqEntity, ImgUploadRes.class);
        } catch (Exception e) {
            throw new Exception("REQUEST ERROR");
        }
        if (result.getStatusCode() != HttpStatus.OK) {
            log.info("================== IMG UPLOAD =================");
            log.info("STATUS : " + result.getStatusCode().toString());
            throw new Exception("REQUEST ERROR");
        }
        return result;
    }
}
