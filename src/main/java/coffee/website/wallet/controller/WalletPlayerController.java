package coffee.website.wallet.controller;

import coffee.website.wallet.service.WalletPlayerService;
import framework.constant.ResponseConstant;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="player", havingValue = "true")
@RequestMapping("api/web-player/wallet/")
@Slf4j
public class WalletPlayerController {

    @Autowired
    WalletPlayerService walletPlayerService;


    @Value("${path.lottoProvider.api}")
    private String lottoProvider;

    @GetMapping("get-balance/{username}")
    public ResponseData<BigDecimal> getBalance(@PathVariable("username") String username) {
        ResponseData<BigDecimal> response = new ResponseData<BigDecimal>();
        try {
            response.setData(walletPlayerService.findBalanceWithBonus(username));
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API WalletController => getBalance");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API WalletController => getBalance :" + e);
        }
        return response;
    }

    @GetMapping("get-balance-lotto/{username}")
    public ResponseData<BigDecimal> getBalanceWithoutBonus(@PathVariable("username") String username) {
        ResponseData<BigDecimal> response = new ResponseData<BigDecimal>();
        try {
            if (walletPlayerService.checkUseBonus(username, lottoProvider)) {
                response.setData(walletPlayerService.findBalanceWithBonus(username));
            } else {
                response.setData(walletPlayerService.findBalanceWithoutBonus(username));
            }
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API WalletController => getBalance");
        } catch (Exception e) {
            response.setMessage(ResponseConstant.RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(ResponseConstant.RESPONSE_STATUS.FAILED);
            log.error("Error Calling API WalletController => getBalance :" + e);
        }
        return response;
    }



}
