package coffee.website.register.service;

import coffee.website.register.vo.res.SmsRes;
import coffee.website.register.vo.res.SmsSendRes;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import org.springframework.http.HttpHeaders;


@Service
public class SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    private String smsUser ="baiwa2020";

    private String smsPassword = "513c31";

    private String urlSms = "http://www.thsms.com/api/rest";
//	?method=send&username=watcharapong50&password=4dcb2a&from=0000&to=0947209566&message=ทดสอบข้อความ

    JAXBContext jaxbContext;

    public SmsSendRes sendSms(String message, String phone) {
        StringBuilder urlSend = new StringBuilder(urlSms);
        urlSend.append("?method=send");
        urlSend.append("&username=" + smsUser);
        urlSend.append("&password=" + smsPassword);
        urlSend.append("&from=OTP");
        urlSend.append("&to=" + phone);
        urlSend.append("&message=" + message);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String dataResStr = restTemplate.getForObject(urlSend.toString(), String.class);
        System.out.println("----------Res Send---------------" + dataResStr);
        SmsRes smsRes = new SmsRes();
        try {
            JAXBContext jc = JAXBContext.newInstance(SmsRes.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            StreamSource streamSource = new StreamSource(new StringReader(dataResStr));
            JAXBElement<SmsRes> je = unmarshaller.unmarshal(streamSource, SmsRes.class);

            smsRes = (SmsRes) je.getValue();
        } catch (Exception e) {
            logger.error("SmsService:: sendSms", e);
        }
        return smsRes.getSend();
    }

    public int checkSms() {
        int dataRes = 0;
        StringBuilder urlSend = new StringBuilder(urlSms);
        urlSend.append("?method=credit");
        urlSend.append("&username=" + smsUser);
        urlSend.append("&password=" + smsPassword);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String dataResStr = restTemplate.getForObject(urlSend.toString(), String.class);
        System.out.println("----------Res Credit---------------" + dataResStr);
        try {
            JAXBContext jc = JAXBContext.newInstance(SmsRes.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            StreamSource streamSource = new StreamSource(new StringReader(dataResStr));
            JAXBElement<SmsRes> je = unmarshaller.unmarshal(streamSource, SmsRes.class);

            SmsRes smsRes = (SmsRes) je.getValue();
            dataRes = smsRes.getCredit().getAmount();

        } catch (Exception e) {
            logger.error("SmsService:: sendSms", e);
        }
        return dataRes;
    }
}
