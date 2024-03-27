package coffee.backoffice.finance.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.finance.model.Wallet;
import coffee.backoffice.finance.model.WithdrawCondition;
import coffee.backoffice.finance.repository.dao.WithdrawConditionDao;
import coffee.backoffice.finance.repository.jpa.WithdrawConditionJpa;
import coffee.backoffice.finance.repository.jpa.WithdrawConditionRepository;
import coffee.backoffice.finance.vo.req.WithdrawConditionReq;
import coffee.backoffice.finance.vo.req.WithdrawConditionStausReq;
import coffee.backoffice.finance.vo.res.WithdrawConditionRes;
import coffee.backoffice.promotion.model.Promotion;
import coffee.backoffice.promotion.model.PromotionMapping;
import coffee.backoffice.promotion.repository.jpa.PromotionMappingRepository;
import coffee.backoffice.promotion.service.PromotionService;
import framework.constant.ProjectConstant;
import framework.constant.ProjectConstant.STATUS;
import framework.constant.ProjectConstant.WITHDRAW_CONDITION;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.UserLoginUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WithdrawConditionService {

    @Autowired
    private WithdrawConditionRepository withdrawConditionRepository;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private WithdrawConditionDao conditionDao;

    @Autowired
    private WithdrawConditionJpa withdrawConditionJpa;

    @Autowired
    private PromotionMappingRepository promotionMappingRepository;

    @Autowired
    private WalletService walletService;

    public void create(WithdrawConditionReq req) {
        WithdrawCondition temp = new WithdrawCondition();
        temp.setUsername(req.getUsername());
        temp.setConditionType(req.getCondtionType());
        temp.setCalculateType(req.getCalculateType());
        temp.setAmount(req.getAmount());
        temp.setOperatorValue(req.getOperatorValue());
        temp.setCurrentTurnover(BigDecimal.ZERO);
        if (ProjectConstant.WITHDRAW_CONDITION.FIXED.equals(req.getCondtionType())) {

            temp.setTargetTurnover(req.getOperatorValue());
        } else {
            temp.setTargetTurnover(req.getAmount().multiply(req.getOperatorValue().divide(new BigDecimal(100))));
        }
        temp.setConditionStatus(ProjectConstant.WITHDRAW_CONDITION.NOT_PASS);
        temp.setCreatedBy(UserLoginUtil.getUsername());

        withdrawConditionRepository.save(temp);

    }

    public List<WithdrawCondition> getWithdrawConditionByUsername(String username, String conditionStatus) {
        return withdrawConditionRepository.findByUsernameAndConditionStatus(username, conditionStatus);
    }

    public WithdrawCondition getWithdrawConditionByUsernameAndConditionType(String username, String conditionType,
                                                                            String conditionStatus) {
        return withdrawConditionRepository.findByUsernameAndConditionTypeAndConditionStatus(username, conditionType,
                conditionStatus);
    }

    public List<WithdrawCondition> getWithdrawConditionByUsernameAndPromoCode(String username, String promoCode) {
        return withdrawConditionRepository.findByUsernameAndPromoCode(username, promoCode);
    }

    public List<WithdrawCondition> getWithdrawConditionByUsernameAndT(String username, String promoCode) {
        return withdrawConditionRepository.findByUsernameAndPromoCode(username, promoCode);
    }

    public WithdrawCondition getWithdrawConditionByUsernameAndConditionAndPromoCode(String username,
                                                                                    String conditionType, String promoCode) {
        return withdrawConditionRepository.findByUsernameAndConditionStatusAndPromoCode(username, conditionType,
                promoCode);
    }

    public void saveWithdrawCondition(WithdrawCondition req) {
        withdrawConditionRepository.save(req);
    }

    public DataTableResponse<WithdrawConditionRes> getWithdrawCondition(DatatableRequest req) {
        DataTableResponse<WithdrawConditionRes> paginateData = conditionDao.WithdrawConditionResPaginate(req);
        DataTableResponse<WithdrawConditionRes> dataTable = new DataTableResponse<>();
        List<WithdrawConditionRes> data = paginateData.getData();
        for (WithdrawConditionRes item : data) {
            Promotion dataPromo = promotionService.getByPromoCode(item.getPromoCode());
            if (dataPromo != null)
                item.setPromotion(dataPromo);
        }
        dataTable.setRecordsTotal(paginateData.getRecordsTotal());
        dataTable.setDraw(paginateData.getDraw());
        dataTable.setData(data);
        dataTable.setPage(req.getPage());
        return paginateData;
    }

    public void changeStatusWithdrawCondition(WithdrawConditionStausReq req) {
        WithdrawCondition findData = withdrawConditionJpa.findByUsernameAndConditionStatus(req.getUsername(),
                WITHDRAW_CONDITION.NOT_PASS);
        if (findData != null) {
            findData.setConditionStatus(req.getConditionStatus());
            findData.setUpdatedBy(UserLoginUtil.getUsername());
            findData.setUpdatedDate(new Date());
            withdrawConditionJpa.save(findData);

            if (findData.getPromoCode() != null) {
                PromotionMapping dataPromo = promotionService.findByUsernameAndPromoCodeAndStatus(req.getUsername(),
                        findData.getPromoCode(), STATUS.ACTIVE);
                dataPromo.setStatus(STATUS.INACTIVE);
                dataPromo.setUpdatedBy(UserLoginUtil.getUsername());
                dataPromo.setUpdatedDate(new Date());
                promotionMappingRepository.save(dataPromo);

                Wallet dataWallet = walletService.findWalletData(req.getUsername());
                dataWallet.setBalance(dataWallet.getBalance().add(dataWallet.getBonus()));
                dataWallet.setBonus(BigDecimal.ZERO);
                dataWallet.setUpdatedBy("_system");
                walletService.editWallet(dataWallet);
            }

        }

    }

    public void increasedOrCreateGeneral(String username, BigDecimal amount, BigDecimal conditionMultiplier) {
        WithdrawCondition findData = withdrawConditionJpa.findByUsernameAndConditionTypeAndConditionStatus(username,
                WITHDRAW_CONDITION.GENERAL,WITHDRAW_CONDITION.NOT_PASS);
        if (findData != null) {
            findData.setTargetTurnover(findData.getTargetTurnover().add(amount));
        } else {
            findData = new WithdrawCondition();
            findData.setUsername(username);
            findData.setConditionType(WITHDRAW_CONDITION.GENERAL);
            findData.setCalculateType(WITHDRAW_CONDITION.MULTIPLIER);
            findData.setAmount(amount);
            findData.setOperatorValue(amount.multiply(conditionMultiplier));
            findData.setCurrentTurnover(BigDecimal.valueOf(0));
            findData.setTargetTurnover(amount);
            findData.setConditionStatus(WITHDRAW_CONDITION.NOT_PASS);
            findData.setCreatedBy(UserLoginUtil.getUsername());
        }
    }

}
