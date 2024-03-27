package coffee.website.register.vo.res;

public class SmsRes {
    private SmsSendRes send;
    private SmsCreditRes credit;

    public SmsSendRes getSend() {
        return send;
    }

    public void setSend(SmsSendRes send) {
        this.send = send;
    }

    public SmsCreditRes getCredit() {
        return credit;
    }

    public void setCredit(SmsCreditRes credit) {
        this.credit = credit;
    }



}
