package coffee.website.register.vo.res;

import lombok.Data;

import java.util.Date;

@Data
public class GetOtpRes {
//	String refCode
    private boolean validateNumber;
    private boolean duplicateNumber;
    private String refCode;
    private String nextRequestAble;
}
