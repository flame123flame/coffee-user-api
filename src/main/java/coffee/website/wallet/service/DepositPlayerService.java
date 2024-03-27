package coffee.website.wallet.service;

import coffee.backoffice.cashback.service.CashbackBalanceService;
import coffee.backoffice.finance.model.Deposit;
import coffee.backoffice.finance.model.TransactionList;
import coffee.backoffice.finance.model.Wallet;
import coffee.backoffice.finance.model.WithdrawCondition;
import coffee.backoffice.finance.repository.dao.DepositDao;
import coffee.backoffice.finance.repository.jpa.DepositRepository;
import coffee.backoffice.finance.service.AllTransactionService;
import coffee.backoffice.finance.service.CompanyAccountService;
import coffee.backoffice.finance.service.WalletService;
import coffee.backoffice.finance.service.WithdrawConditionService;
import coffee.backoffice.finance.vo.model.ObjectString;
import coffee.backoffice.finance.vo.req.DepositReq;
import coffee.backoffice.finance.vo.res.CompanyAccountRes;
import coffee.backoffice.finance.vo.res.DepositDetailRes;
import coffee.backoffice.finance.vo.res.DepositMainRes;
import coffee.backoffice.player.model.Customer;
import coffee.backoffice.player.model.GroupLevel;
import coffee.backoffice.player.service.CustomerService;
import coffee.backoffice.player.service.DepositInformationService;
import coffee.backoffice.player.service.GroupLevelService;
import coffee.backoffice.player.vo.res.CustomerRes;
import coffee.backoffice.promotion.service.PromotionService;
import framework.constant.ProjectConstant;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.WebSocketNotificationRes;
import framework.utils.ConvertDateUtils;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DepositPlayerService {

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private WalletService walletService;

    @Autowired
    private CompanyAccountService companyAccountService;
    
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void createDeposit(DepositReq form) {
        log.info("=== DepositService => createDeposit() on Started ===");
        Deposit temp = new Deposit();
        temp.setDepositOrder(form.getDepositOrder());
        temp.setUsername(form.getUsername());
        temp.setAmount(form.getAmount());
        temp.setCompanyAccountCode(form.getCompanyAccountCode());
        temp.setDepositDate(ConvertDateUtils.parseStringToDate(form.getDepositDate(), ConvertDateUtils.DD_MM_YYYY_HHMMSS));
        Customer customer = customerService.getByUsername(form.getUsername());
        BigDecimal wallet = walletService.findBalanceWithoutBonus(form.getUsername());
        int count = customer.getDepositCount()+1;
        customer.setDepositCount(count);
        temp.setDepositRemark("");
        temp.setStatus(ProjectConstant.STATUS.PENDING);
        temp.setSlip(form.getSlip());
        temp.setCreatedBy(form.getUsername());
        temp.setSystemType(ProjectConstant.DEPOSIT.BO);
        temp.setBeforeBalance(wallet);
        temp.setAfterBalance(wallet.add(form.getAmount()));
        customerService.saveCustomer(customer);
        depositRepository.save(temp);

        WebSocketNotificationRes responseString = new WebSocketNotificationRes();
        responseString.setType("DEPOSIT");
        responseString.setMessage(form.getUsername() + " Deposit Amount : " + form.getAmount().toString());
        responseString.setUsername(form.getUsername());
        this.simpMessagingTemplate.convertAndSend("/admin/notification/deposit", responseString);
        log.info("=== DepositService => createDeposit() Ending ===");
    }



    public List<DepositMainRes> getDepositByUsername(String username) {
        log.info("=== DepositService => getDepositByUsername() on Started ===");
        log.info("Calling depositRepository.findByUsername()");
        List<Deposit> resultList = depositRepository.findByUsername(username);
        log.info("depositRepository.findByUsername() : "+resultList.size());
        List<DepositMainRes> res = new ArrayList<DepositMainRes>();
        DepositMainRes temp;
        int index = 0;
        for(Deposit result :resultList) {
            index++;

            log.info("DepositOrder : "+ result.getDepositOrder());
            CompanyAccountRes company = companyAccountService.getOne(result.getCompanyAccountCode());
            temp = new DepositMainRes();
            temp.setDepositOrder(result.getDepositOrder());
            temp.setDepositType(result.getDepositType());
            temp.setDepositDate(result.getDepositDate());
            temp.setDepositRemark(result.getDepositRemark());
            temp.setAmount(result.getAmount().toString());
            temp.setCompanyBankCode(company.getBankCode());
            temp.setCompanyAccountName(company.getAccountName());
            temp.setStatus(result.getStatus());
            temp.setAuditor(result.getAuditor());
            temp.setAuditorDate(result.getAuditorDate());

            res.add(temp);
        }

        log.info("=== DepositService => getDepositByUsername() Ending ===");
        return res;
    }

}
