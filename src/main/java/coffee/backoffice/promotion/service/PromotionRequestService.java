package coffee.backoffice.promotion.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import coffee.backoffice.player.service.BonusInformationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.AccessOptions.GetOptions.GetNulls;
import org.springframework.stereotype.Service;

import coffee.backoffice.affiliate.service.AffiliateService;
import coffee.backoffice.finance.model.TransactionGame;
import coffee.backoffice.finance.model.TransactionList;
import coffee.backoffice.finance.model.Wallet;
import coffee.backoffice.finance.model.WithdrawCondition;
import coffee.backoffice.finance.service.AllTransactionService;
import coffee.backoffice.finance.service.DepositService;
import coffee.backoffice.finance.service.WalletService;
import coffee.backoffice.finance.service.WithdrawConditionService;
import coffee.backoffice.finance.vo.res.DepositMainRes;
import coffee.backoffice.player.service.CustomerService;
import coffee.backoffice.player.vo.res.CustomerRes;
import coffee.backoffice.promotion.constatnt.PromotionConstant;
import coffee.backoffice.promotion.model.AppSetting;
import coffee.backoffice.promotion.model.BonusLevelSetting;
import coffee.backoffice.promotion.model.Promotion;
import coffee.backoffice.promotion.model.PromotionMapping;
import coffee.backoffice.promotion.model.PromotionRequest;
import coffee.backoffice.promotion.model.RuleSetting;
import coffee.backoffice.promotion.repository.jpa.PromotionRequestJpa;
import coffee.backoffice.promotion.vo.model.StatusDetail;
import coffee.backoffice.promotion.vo.req.PromotionRequestReq;
import coffee.backoffice.promotion.vo.res.PromotionRequestRes;
import framework.constant.ProjectConstant;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;

import javax.transaction.Transactional;

@Service
public class PromotionRequestService {

    @Autowired
    private PromotionRequestJpa promotionRequestJpa;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DepositService depositService;

    @Autowired
    private AffiliateService affiliateService;

    @Autowired
    private AllTransactionService allTransactionService;

    @Autowired
    private WalletService walletService;
    @Autowired
    private BonusInformationService bonusInformationService;

    @Autowired
    private WithdrawConditionService withdrawConditionService;

    public List<PromotionRequestRes> getAll() throws Exception {
        List<PromotionRequest> data = promotionRequestJpa.findAll();
        if (data.isEmpty())
            return null;
        List<PromotionRequestRes> returnData = new ArrayList<>();
        for (PromotionRequest i : data) {
            PromotionRequestRes oneData = new PromotionRequestRes();
            oneData.setEntityToRes(i);
            returnData.add(oneData);
        }
        return returnData;
    }

    public PromotionRequest getByRequestId(String requestId) {
        PromotionRequest data = promotionRequestJpa.findByRequestId(requestId);
        return data;
    }

    public StatusDetail rejectCondition(Promotion promotion, PromotionRequestReq req, CustomerRes customer, AppSetting appSetting, RuleSetting ruleSetting, List<DepositMainRes> deposit) {
        StatusDetail res = new StatusDetail();
        Date now = new Date();
        Date registerDate = affiliateService.getAffiliateNetworkByUsername(req.getUsername()).getRegisterDate();
        String remark = " ";
        boolean checkTransaction = true;
        boolean checkHaveRequest = true;
        boolean checkDate = false;
        boolean checkDateRecive = false;
        boolean checkGroup = false;
        boolean checkPromotion = false;
        boolean checkDeposit = false;
        boolean checkNewPromotion = false;
        boolean checkReciveLimit = true;
        boolean checkLimitApproveCampiagn = false;
        boolean checkLimitBonusCampiagn = false;
        boolean checkAboveDeposit = false;

        // check Limit Bonus
        List<PromotionRequest> requestList = promotionRequestJpa.findByPromoCodeAndStatusRequest(req.getPromoCode(), ProjectConstant.STATUS.APPROVE);
        BigDecimal sumRequestList = sumLimitBonusPromotion(requestList);
        if (ruleSetting.getMaxBonusLimit().compareTo(sumRequestList) > 0) {
            checkLimitBonusCampiagn = true;
        } else {
            remark += " โบนัสสำหรับโปรโมชั่นนี้ถึงจำนวนจำกัดแแล้ว  ";
        }

        // check Have Request
        List<PromotionRequest> request = promotionRequestJpa.findByUsernameAndPromoCodeAndStatusRequest(req.getUsername(), req.getPromoCode(), ProjectConstant.STATUS.PENDING);
        if (request != null && request.size() > 0) {
            checkHaveRequest = false;
            remark += " มีโปรโมชั่นกำลังรออนุมัติ ";
        }
        
        // check Recive Limit
        List<PromotionRequest> requestLimit = promotionRequestJpa.findByUsernameAndPromoCodeAndStatusRequest(req.getUsername(), req.getPromoCode(), ProjectConstant.STATUS.APPROVE);
        if (requestLimit != null && requestLimit.size() > appSetting.getReciveLimit()) {
        	checkReciveLimit = false;
            remark += " รับโปรโมชั่นนี้เกินจำนวนครั้งที่กำนหด ";
        }
        
        // check deposit above config
        if (PromotionConstant.Type.firstAndSecondDeposit.equals(promotion.getPromoType())) {
            BigDecimal amount = new BigDecimal(deposit.get(0).getAmount());
            BonusLevelSetting bonusSetting = promotionService.findLevelBonus(req.getPromoCode(), amount);
            if (bonusSetting != null) {
                checkAboveDeposit = true;
            } else {
                remark += " ยอดเครดิตที่ฝากน้อยกว่าที่โปรโมชั่นกำหนด  ";
            }
        } else {
            checkAboveDeposit = true;
        }


        // check Approveral and Limit Bonus
        if (ruleSetting.getMaxApprovalLimit().compareTo(new BigDecimal(requestList.size())) > 0) {
            checkLimitApproveCampiagn = true;
        } else {
            remark += " โปรโมชั่นนี้ถึงจำนวนจำกัดแแล้ว  ";
        }

        // check date in range promotion ?
        if (PromotionConstant.PromoPeriodType.datePeriod.equals(promotion.getPromoPeriodType())) {
            checkDate = promotion.getStartDate().compareTo(new Date()) <= 0 && promotion.getEndDate().compareTo(new Date()) >= 0;
        } else {
            checkDate = promotion.getStartDate().compareTo(new Date()) <= 0;
        }
        if (!checkDate) {
            remark += " ไม่อยู่ในช่วงเวลาโปรโมชั่น  ";
        }

        if (appSetting != null) {

            // check recive promotion in date range
            if (appSetting.getValidPeriod() != null) {
                Integer dayPlus = Integer.valueOf(appSetting.getValidPeriod().toString());
                Calendar calendar = Calendar.getInstance();
                if (PromotionConstant.Type.registration.equals(promotion.getPromoType())) {
                    calendar.setTime(registerDate);
                } else {
                    if (deposit.size() > 0) {
                        calendar.setTime(deposit.get(0).getCreatedDate());
                    } else {
                        checkDeposit = false;
                        remark += " ไม่มีประวัติการฝากเงิน ";
                    }
                }
                calendar.add(Calendar.DAY_OF_MONTH, dayPlus);

                if (now.compareTo(calendar.getTime()) <= 0) {
                    checkDateRecive = true;
                } else {
                    remark += " เลยกำหนดรับโปรโมชั่น  ";
                }
            } else {
                checkDateRecive = true;
            }

            //check allow group on promotion
            if (StringUtils.isNoneBlank(appSetting.getGroupList())) {
                String[] groupList = appSetting.getGroupList().split(",");
                for (String group : groupList) {
                    if (group.trim().equals(customer.getGroupCode())) {
                        checkGroup = true;
                        break;
                    }
                }

                if (!checkGroup) {
                    remark += " กลุ่มนี้ไม่ได้รับสิทธฺิ์โปรโมชั่น  ";
                }
            } else {
                checkGroup = true;
            }
        }

        // check not have promotion ?
        List<PromotionMapping> mapping = promotionService.findMappingByUsernameAndStatus(req.getUsername(), ProjectConstant.STATUS.ACTIVE);
        if (mapping == null || mapping.size() == 0) {
            checkPromotion = true;
        } else {
            remark += " มีโปรโมชั่นอื่นและติดยอดเทินโอเวอร์ ";
        }

        mapping = promotionService.findMappingByUsernameAndStatus(req.getUsername(), ProjectConstant.STATUS.INACTIVE);
        // check new regis ? && check have deposit ?
        if (PromotionConstant.Type.registration.equals(promotion.getPromoType())) {
            if (deposit.size() == 0) {
                checkDeposit = true;
                checkNewPromotion = true;
            } else {
                remark += " โปรโมชั่นสำหรับสมาชิกใหม่เท่านั้น ";
            }
        } else {
            if (deposit.size() > 0) {
                checkDeposit = true;
                checkNewPromotion = true;

                DepositMainRes targetBalance = filterTargetDeposit(promotion, appSetting, deposit, mapping);
                if (targetBalance == null) {
                    checkDeposit = false;
                    remark += " ไม่มียอดฝากตรงตามเงื่อนไขรับโปรโมชั่น ";
                }

                // check transaction
                List<TransactionGame> transactionGame = allTransactionService.getGameTransactionByUsername(req.getUsername());
                if (transactionGame.size() != 0) {
                    if (targetBalance != null) {
                        for (TransactionGame tran : transactionGame) {
                            if (tran.getCreatedDate().compareTo(targetBalance.getCreatedDate()) > 0) {
                                checkTransaction = false;
                                remark += " เคยใช้เครดิตในการเล่นเกมแล้ว ";
                                break;
                            }
                        }
                    }
                }

            } else {
                remark += " ยังไม่เคยฝากเงิน ";
            }
        }

        res.setCheckCondition(checkDate && checkDateRecive && checkPromotion && checkGroup && checkDeposit && checkNewPromotion && checkTransaction && checkLimitApproveCampiagn && checkLimitBonusCampiagn && checkAboveDeposit && checkHaveRequest && checkReciveLimit);
        res.setRemark(remark);

        return res;

    }

    public BigDecimal sumLimitBonusPromotion(List<PromotionRequest> requestList) {
        BigDecimal sum = BigDecimal.ZERO;
        for (PromotionRequest temp : requestList) {
            BigDecimal cal = temp.getBalanceAmount().add(temp.getBonusAmount());
            sum = sum.add(cal);
        }
        return sum;

    }

    public DepositMainRes filterTargetDeposit(Promotion promotion, AppSetting appSetting, List<DepositMainRes> deposit, List<PromotionMapping> mapping) {
        DepositMainRes targetBalance = new DepositMainRes();
        if (PromotionConstant.Type.firstAndSecondDeposit.equals(promotion.getPromoType())) {
            if (PromotionConstant.FirstAndSecondDepositType.first.equals(appSetting.getDepositSequence())) {
                targetBalance = deposit.get(0);
            } else {
                targetBalance = deposit.get(1);
            }
        } else {
            if (mapping.size() > 0) {
                Date dateAfterPass = mapping.get(0).getUpdatedDate();
                Date dateDeposit = deposit.get(0).getCreatedDate();
                if (dateDeposit.compareTo(dateAfterPass) > 0) {
                    targetBalance = deposit.get(0);
                }
            } else {
                targetBalance = deposit.get(0);
            }

        }
        return targetBalance;
    }

    public BigDecimal calculateByRegistration(Promotion promotion, String username, RuleSetting ruleSetting, String requestId) {

        BigDecimal fixedAmount = ruleSetting.getBonusCalculation();
        // Create mapp promotion
        PromotionMapping mapping = new PromotionMapping();
        mapping.setUsername(username);
        mapping.setPromoCode(ruleSetting.getPromoCode());
        mapping.setRequestId(requestId);
        mapping.setStatus(ProjectConstant.STATUS.INACTIVE);
        mapping.setDateActive(new Date());
        promotionService.saveMapping(mapping);

        // Create turnover amount
        WithdrawCondition withdraw = new WithdrawCondition();
        withdraw.setUsername(username);
        withdraw.setConditionType(ProjectConstant.WITHDRAW_CONDITION.PROMOTION);
        withdraw.setConditionStatus(ProjectConstant.WITHDRAW_CONDITION.PENDING);
        withdraw.setCurrentTurnover(BigDecimal.ZERO);
        withdraw.setOperatorValue(ruleSetting.getWithdrawCondition());
        withdraw.setCreatedBy(UserLoginUtil.getUsername());
        withdraw.setPromoCode(ruleSetting.getPromoCode());

        // Calculate balance
        if (PromotionConstant.ConditionType.multiplierCondition.equals(ruleSetting.getConditionType())) {
            BigDecimal turnover = fixedAmount.multiply(ruleSetting.getWithdrawCondition());
            withdraw.setAmount(fixedAmount);
            withdraw.setCalculateType(ProjectConstant.WITHDRAW_CONDITION.MULTIPLIER);
            withdraw.setTargetTurnover(turnover);

        } else {
            withdraw.setAmount(BigDecimal.ZERO);
            withdraw.setCalculateType(ProjectConstant.WITHDRAW_CONDITION.FIXED);
            withdraw.setTargetTurnover(ruleSetting.getWithdrawCondition());
        }

        withdrawConditionService.saveWithdrawCondition(withdraw);
        return fixedAmount;

    }

    public BigDecimal calculateByGeneralDeposit(Promotion promotion, String username, AppSetting appSetting, RuleSetting ruleSetting, BonusLevelSetting bonusSetting, BigDecimal depositBalance, String requestId) {

        BigDecimal result = BigDecimal.ZERO;
        // Create mapp promotion
        PromotionMapping mapping = new PromotionMapping();
        mapping.setUsername(username);
        mapping.setPromoCode(ruleSetting.getPromoCode());
        mapping.setRequestId(requestId);
        mapping.setStatus(ProjectConstant.STATUS.INACTIVE);
        mapping.setDateActive(new Date());
        promotionService.saveMapping(mapping);

        // Create turnover amount
        WithdrawCondition withdraw = new WithdrawCondition();
        withdraw.setUsername(username);
        withdraw.setConditionType(ProjectConstant.WITHDRAW_CONDITION.PROMOTION);
        withdraw.setConditionStatus(ProjectConstant.WITHDRAW_CONDITION.PENDING);
        withdraw.setCurrentTurnover(BigDecimal.ZERO);
        withdraw.setOperatorValue(ruleSetting.getWithdrawCondition());
        withdraw.setCreatedBy(UserLoginUtil.getUsername());
        withdraw.setPromoCode(ruleSetting.getPromoCode());

        // Calculate balance
        if (PromotionConstant.BonusType.fixedAmount.equals(ruleSetting.getBonusType())) {
            BigDecimal fixedAmount = bonusSetting.getBonusCalculation();
            result = fixedAmount;
        } else {
            BigDecimal ratioAmount = depositBalance.multiply(bonusSetting.getBonusCalculation().divide(new BigDecimal(100)));
            result = ratioAmount;
        }

        if (PromotionConstant.ConditionType.multiplierCondition.equals(ruleSetting.getConditionType())) {
            BigDecimal balance = depositBalance.add(result);
            BigDecimal turnover = balance.multiply(bonusSetting.getWithdrawCondition());
            withdraw.setAmount(result);
            withdraw.setCalculateType(ProjectConstant.WITHDRAW_CONDITION.MULTIPLIER);
            withdraw.setTargetTurnover(turnover);
        } else {
            withdraw.setAmount(result);
            withdraw.setCalculateType(ProjectConstant.WITHDRAW_CONDITION.FIXED);
            withdraw.setTargetTurnover(ruleSetting.getWithdrawCondition());
        }

        withdrawConditionService.saveWithdrawCondition(withdraw);
        return result;
    }

    public BigDecimal calculateByFisrtAndSecond(Promotion promotion, String username, AppSetting appSetting, RuleSetting ruleSetting, BonusLevelSetting bonusSetting, BigDecimal depositBalance, String requestId) {

        BigDecimal result = BigDecimal.ZERO;
        // Create mapp promotion
        PromotionMapping mapping = new PromotionMapping();
        mapping.setUsername(username);
        mapping.setPromoCode(ruleSetting.getPromoCode());
        mapping.setRequestId(requestId);
        mapping.setStatus(ProjectConstant.STATUS.INACTIVE);
        mapping.setDateActive(new Date());
        promotionService.saveMapping(mapping);

        // Create turnover amount
        WithdrawCondition withdraw = new WithdrawCondition();
        withdraw.setUsername(username);
        withdraw.setConditionType(ProjectConstant.WITHDRAW_CONDITION.PROMOTION);
        withdraw.setConditionStatus(ProjectConstant.WITHDRAW_CONDITION.PENDING);
        withdraw.setCurrentTurnover(BigDecimal.ZERO);
        withdraw.setOperatorValue(ruleSetting.getWithdrawCondition());
        withdraw.setCreatedBy(UserLoginUtil.getUsername());
        withdraw.setPromoCode(ruleSetting.getPromoCode());

        // Calculate balance
        if (PromotionConstant.BonusType.fixedAmount.equals(ruleSetting.getBonusType())) {
            BigDecimal fixedAmount = bonusSetting.getBonusCalculation();
            result = fixedAmount;
        } else {
            BigDecimal ratioAmount = depositBalance.multiply(bonusSetting.getBonusCalculation().divide(new BigDecimal(100)));
            result = ratioAmount;
        }

        if (PromotionConstant.ConditionType.multiplierCondition.equals(ruleSetting.getConditionType())) {
            BigDecimal balance = depositBalance.add(result);
            BigDecimal turnover = balance.multiply(bonusSetting.getWithdrawCondition());
            withdraw.setAmount(result);
            withdraw.setCalculateType(ProjectConstant.WITHDRAW_CONDITION.MULTIPLIER);
            withdraw.setTargetTurnover(turnover);
        } else {
            withdraw.setAmount(result);
            withdraw.setCalculateType(ProjectConstant.WITHDRAW_CONDITION.FIXED);
            withdraw.setTargetTurnover(ruleSetting.getWithdrawCondition());
        }

        withdrawConditionService.saveWithdrawCondition(withdraw);
        return result;
    }

    @Transactional
    public void changeStatusPromotion(PromotionRequestReq req) {
        PromotionRequest data = promotionRequestJpa.findByRequestId(req.getRequestId());
        WithdrawCondition withdraw = withdrawConditionService.getWithdrawConditionByUsernameAndConditionAndPromoCode(req.getUsername(), ProjectConstant.WITHDRAW_CONDITION.PENDING, req.getPromoCode());

        if (PromotionConstant.Status.APPROVE.equals(req.getStatusRequest())) {
            String username = req.getUsername();
            Wallet wallet = walletService.findWalletData(username);
            RuleSetting rule = promotionService.getRuleSettingByPromoCode(req.getPromoCode());

            // update mapping
            PromotionMapping mapping = promotionService.findMappingByUsernameAndStatusAndRequestId(req.getUsername(), ProjectConstant.STATUS.INACTIVE, req.getRequestId());
            mapping.setStatus(ProjectConstant.STATUS.ACTIVE);
            mapping.setUpdatedDate(new Date());
            promotionService.editMapping(mapping);

            // calculate amount transaction
            TransactionList transaction = new TransactionList();
            BigDecimal amount = BigDecimal.ZERO;
            BigDecimal walletAmount = BigDecimal.ZERO;

            if (PromotionConstant.WalletType.BALANCE.equals(rule.getReceiveBonusWallet())) {
                amount = data.getBalanceAmount();
                walletAmount = wallet.getBalance().add(amount);
                transaction.setBeforeBalance(wallet.getBalance());
                transaction.setTotalBalance(walletAmount);
                transaction.setTransactionType(ProjectConstant.TRANSACTION_TYPE.PROMOTION_BALANCE);
                transaction.setToRecive(ProjectConstant.WALLET_TYPE.BALANCE);
                walletService.addBalanceWallet(username, amount);
            } else {
                amount = data.getBonusAmount();
                walletAmount = wallet.getBonus().add(amount);
                transaction.setBeforeBalance(wallet.getBonus());
                transaction.setTotalBalance(walletAmount);
                transaction.setTransactionType(ProjectConstant.TRANSACTION_TYPE.PROMOTION_BONUS);
                transaction.setToRecive(ProjectConstant.WALLET_TYPE.BONUS);
                walletService.addBonusWallet(username, amount);
            }

            // create Transaction
            transaction.setTransactionId(GenerateRandomString.generateUUID());
            transaction.setAddBalance(amount);
            transaction.setStatus(ProjectConstant.STATUS.SUCCESS);
            transaction.setAfterBalance(walletAmount);
            transaction.setTransactionAmount(amount);
            transaction.setTransactionDate(new Date());
            transaction.setUsername(username);
            transaction.setFromSender("-");
            transaction.setUpdatedBy(UserLoginUtil.getUsername());
            allTransactionService.createTransaction(transaction);


            withdraw.setConditionStatus(ProjectConstant.WITHDRAW_CONDITION.NOT_PASS);

            if (data.getPromoType().equals(PromotionConstant.Type.registration)) {
                bonusInformationService.addOrUpdateTotalRegistrationBonus(req.getUsername(), data.getBonusAmount());
            } else if (data.getPromoType().equals(PromotionConstant.Type.firstAndSecondDeposit)
                    || data.getPromoType().equals(PromotionConstant.Type.generalDeposit)
                    || data.getPromoType().equals(PromotionConstant.Type.customized)) {
                bonusInformationService.addOrUpdateTotalDepositBonus(req.getUsername(), data.getBonusAmount());
            }

        } else {
            withdraw.setConditionStatus(ProjectConstant.WITHDRAW_CONDITION.PASS);
        }
        data.setStatusRequest(req.getStatusRequest());
        data.setUpdatedBy(UserLoginUtil.getUsername());
        data.setUpdatedAt(new Date());

        withdrawConditionService.saveWithdrawCondition(withdraw);
        promotionRequestJpa.save(data);

        WithdrawCondition withdrawCondition = withdrawConditionService.getWithdrawConditionByUsernameAndConditionType(req.getUsername(),
                ProjectConstant.WITHDRAW_CONDITION.GENERAL, ProjectConstant.WITHDRAW_CONDITION.NOT_PASS);
        if (withdrawCondition != null) {
            withdrawCondition.setConditionStatus(ProjectConstant.WITHDRAW_CONDITION.PASS);
            withdrawConditionService.saveWithdrawCondition(withdrawCondition);
        }

    }

}
