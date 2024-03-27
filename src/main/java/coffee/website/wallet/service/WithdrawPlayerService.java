package coffee.website.wallet.service;

import coffee.backoffice.finance.model.Withdraw;
import coffee.backoffice.finance.model.WithdrawCondition;
import coffee.backoffice.finance.repository.jpa.WithdrawRepository;
import coffee.backoffice.finance.service.WalletService;
import coffee.backoffice.finance.service.WithdrawConditionService;
import coffee.backoffice.finance.vo.req.WithdrawReq;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.service.CustomerService;
import framework.constant.ProjectConstant;
import framework.model.WebSocketNotificationRes;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class WithdrawPlayerService {

    @Autowired
    private WithdrawRepository withdrawRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private WithdrawConditionService withdrawConditionService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    public void newPlayerRequest(WithdrawReq req) throws Exception {
        List<WithdrawCondition> list = withdrawConditionService.getWithdrawConditionByUsername(UserLoginUtil.getUsername(), ProjectConstant.WITHDRAW_CONDITION.NOT_PASS);
        Withdraw entity = new Withdraw();
        entity.setOrderWithdraw(GenerateRandomString.generateUUID());
        entity.setUsername(UserLoginUtil.getUsername());
        entity.setAmount(req.getAmount());
        entity.setUserRemark(req.getUserRemark());
//				BO ACCOUNT
        entity.setAccountName(req.getAccountName());
        entity.setBankAccount(req.getBankAccount());
        entity.setBankName(req.getBankName());
//				CUSTOMER ACCOUNT
        Customer customer = customerService.getByUsername(UserLoginUtil.getUsername());
        BigDecimal wallet = walletService.findBalanceWithoutBonus(UserLoginUtil.getUsername());
        entity.setBeforeBalance(wallet);
        entity.setAfterBalance(wallet.subtract(req.getAmount()));
        entity.setToAccountName(customer.getRealName());
        entity.setToBankAccount(customer.getBankAccount());
        entity.setToBankName(customer.getBankCode());

        entity.setWithdrawDate(new Date());
        entity.setCreatedBy(UserLoginUtil.getUsername());
        entity.setCreatedDate(new Date());
        if (list.size() == 0) {
            walletService.reduceBalance(UserLoginUtil.getUsername(), req.getAmount());
            walletService.addPendingBalance(UserLoginUtil.getUsername(), req.getAmount());
            entity.setWithdrawStatus(ProjectConstant.WITHDRAW.PENDING);
            withdrawRepository.save(entity);
            WebSocketNotificationRes responseString = new WebSocketNotificationRes();
            responseString.setType("WITHDRAW");
            responseString.setMessage(UserLoginUtil.getUsername() + " withdraw Amount : " + req.getAmount().toString());
            responseString.setUsername(UserLoginUtil.getUsername());
            this.simpMessagingTemplate.convertAndSend("/admin/notification/withdraw", responseString);
        } else {
            entity.setWithdrawStatus(ProjectConstant.WITHDRAW.REJECT);
            withdrawRepository.save(entity);
            throw new Exception("NOT PASS WITHDRAW CONDITION");
        }
    }

}
