package coffee.backoffice.finance.controller;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.finance.model.Wallet;
import coffee.backoffice.finance.service.WalletService;
import coffee.backoffice.finance.vo.req.ManualAndSubtractReq;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/wallet/")
@Slf4j
public class WalletController {

    @Autowired
    WalletService walletService;


    @Value("${path.lottoProvider.api}")
    private String lottoProvider;

    @GetMapping("get-balance/{username}")
    public ResponseData<BigDecimal> getBalance(@PathVariable("username") String username) {
        ResponseData<BigDecimal> response = new ResponseData<BigDecimal>();
        try {
            response.setData(walletService.findBalanceWithBonus(username));
            response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API WalletController => getBalance");
        } catch (Exception e) {
            response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(RESPONSE_STATUS.FAILED);
            log.error("Error Calling API WalletController => getBalance :" + e);
        }
        return response;
    }

    @GetMapping("get-balance-lotto/{username}")
    public ResponseData<BigDecimal> getBalanceWithoutBonus(@PathVariable("username") String username) {
        ResponseData<BigDecimal> response = new ResponseData<BigDecimal>();
        try {
            if (walletService.checkUseBonus(username, lottoProvider)) {
                response.setData(walletService.findBalanceWithBonus(username));
            } else {
                response.setData(walletService.findBalanceWithoutBonus(username));
            }
            response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API WalletController => getBalance");
        } catch (Exception e) {
            response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(RESPONSE_STATUS.FAILED);
            log.error("Error Calling API WalletController => getBalance :" + e);
        }
        return response;
    }

    @GetMapping("get-wallet/{username}")
    public ResponseData<Wallet> getWallet(@PathVariable("username") String username) {
        ResponseData<Wallet> response = new ResponseData<Wallet>();
        try {
            response.setData(walletService.findWalletData(username));
            response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API WalletController => getWallet");
        } catch (Exception e) {
            response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(RESPONSE_STATUS.FAILED);
            log.error("Error Calling API WalletController => getWallet :" + e);
        }
        return response;
    }

    @GetMapping("get-all-wallet/{username}")
    public ResponseData<List<Wallet>> getAllWallet(@PathVariable("username") String username) {
        ResponseData<List<Wallet>> response = new ResponseData<List<Wallet>>();
        try {
            response.setData(walletService.findAllWalletData(username));
            response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API WalletController => getWallet");
        } catch (Exception e) {
            response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(RESPONSE_STATUS.FAILED);
            log.error("Error Calling API WalletController => getWallet :" + e);
        }
        return response;
    }

  //add and subtract เก่า ลบได้
    @GetMapping("manual-add-subtract/{username}/{amount}/{wallet}/{walletType}/{add}/{subtract}")
    public ResponseData<?> manualAddSubtract(
            @PathVariable("username") String username, @PathVariable("amount") BigDecimal amount,
            @PathVariable("wallet") String wallet, @PathVariable("walletType") String walletType,
            @PathVariable("add") Boolean add, @PathVariable("subtract") Boolean subtract) {
        ResponseData<?> response = new ResponseData<>();
        try {
            walletService.manualAddSubtract(username, amount, wallet, walletType, add, subtract);
            response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API WalletController => subtractBonusWallet");
        } catch (Exception e) {
            response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(RESPONSE_STATUS.FAILED);
            log.error("Error Calling API WalletController => subtractBonusWallet :" + e);
        }
        return response;
    }
    
    @PostMapping("manual-adjust-add-subtract")
    public ResponseData<?> manualAdjustAddSubtract(@RequestBody ManualAndSubtractReq req) {
        ResponseData<?> response = new ResponseData<>();
        try {
            walletService.manualAdjustAddSubtract(req);
            response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
            response.setStatus(RESPONSE_STATUS.SUCCESS);
            log.info("Success Calling API WalletController => subtractBonusWallet");
        } catch (Exception e) {
            response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
            response.setStatus(RESPONSE_STATUS.FAILED);
            log.error("Error Calling API WalletController => subtractBonusWallet :" + e);
        }
        return response;
    }
}
