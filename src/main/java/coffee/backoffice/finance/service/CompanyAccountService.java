package coffee.backoffice.finance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.finance.model.CompanyAccount;
import coffee.backoffice.finance.repository.dao.CompanyAccountDao;
import coffee.backoffice.finance.repository.jpa.CompanyAccountJpa;
import coffee.backoffice.finance.vo.req.CompanyAccountReq;
import coffee.backoffice.finance.vo.res.BankRes;
import coffee.backoffice.finance.vo.res.CompanyAccountRes;
import coffee.backoffice.player.model.GroupLevel;
import coffee.backoffice.player.repository.jpa.GroupLevelRepository;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.GenerateRandomString;

@Service
public class CompanyAccountService {
	
	@Autowired
    private BankService bankService;
	
    @Autowired
    private CompanyAccountDao companyAccountDao;

    @Autowired
    private CompanyAccountJpa companyAccountJpa;

    @Autowired
    private GroupLevelRepository groupLevelRepository;

//    @Autowired
//    private Group companyAccountJpa;

    public CompanyAccountRes getOne(String companyAccountCode) {
        CompanyAccount data = companyAccountJpa.findByCompanyAccountCode(companyAccountCode);
        if (data == null)
            return null;
        CompanyAccountRes returnData = new CompanyAccountRes();
        returnData.setEntityToRes(data);
        return returnData;
    }
    
    public CompanyAccount getCompanyByCode(String code) {
    	return companyAccountJpa.findByCompanyAccountCode(code);
    }
    
    public CompanyAccount getCompanyByBankIdAndBankAccount(String bankId,String bankAccount) {
    	return companyAccountJpa.findByBankIdAndBankAccount(bankId,bankAccount);
    }
    
    public void saveCompanyAccount(CompanyAccount entity) {
    	companyAccountJpa.save(entity);
    }

    public List<CompanyAccountRes> getAll() throws Exception {
        List<CompanyAccount> data = companyAccountJpa.findAll();
        if (data.isEmpty())
            return null;
        List<CompanyAccountRes> returnData = new ArrayList<>();
        for (CompanyAccount i : data) {
            CompanyAccountRes oneData = new CompanyAccountRes();
            oneData.setEntityToRes(i);
            returnData.add(oneData);
        }
        return returnData;
    }

    public DataTableResponse<CompanyAccountRes> getPaginateModel(DatatableRequest req) {
        DataTableResponse<CompanyAccountRes> dataTable = new DataTableResponse<>();
        DataTableResponse<CompanyAccountRes> tag = new DataTableResponse<>();
        tag = companyAccountDao.paginate(req);
        dataTable.setRecordsTotal(tag.getRecordsTotal());
        dataTable.setDraw(tag.getDraw());
        List<CompanyAccountRes> data = tag.getData();
        List<CompanyAccountRes> newarr = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            CompanyAccountRes da = data.get(i);
            GroupLevel group = groupLevelRepository.findByGroupCode(da.getGroupCode());

            da.setGroup(group);
            newarr.add(da);
        }
        dataTable.setData(newarr);
        return dataTable;
    }

    public void insertOne(CompanyAccountReq form) throws Exception {
    	CompanyAccount dataInsert = companyAccountJpa.findByBankAccountAndBankCode(form.getBankAccount(),form.getBankCode());
    	
    	if(dataInsert == null) {
    	//  create insert
            dataInsert = new CompanyAccount();
            BankRes temp = bankService.getBankByCode(form.getBankCode());
            if(temp != null) {
            	dataInsert.setBank(temp.getBankNameTh());
            }else {
            	dataInsert.setBank("UNKNOW");
            }
            
            dataInsert.setCompanyAccountCode(GenerateRandomString.generateUUID());
            dataInsert.setBankId(form.getBankId());
            dataInsert.setDisplayName(form.getDisplayName());
            dataInsert.setBankAccount(form.getBankAccount());
            dataInsert.setAccountName(form.getAccountName());
            dataInsert.setBankCode(form.getBankCode());
            dataInsert.setMaxDepositDaily(form.getMaxDepositDaily());
            dataInsert.setBankId(form.getBankId());
            dataInsert.setMaxWithdrawDaily(form.getMaxWithdrawDaily());
            dataInsert.setGroupCode(form.getGroupCode());
            dataInsert.setCreatedAt(new Date());
//            dataInsert.setBalance(form.getBalance());
            companyAccountJpa.save(dataInsert);
    	}
        
    }

    public void updateOne(CompanyAccountReq form) throws Exception {
        //  create insert
        CompanyAccount dataUpdate = companyAccountJpa.findByCompanyAccountCode(form.getCompanyAccountCode());
        dataUpdate.setBank(form.getBank());
        dataUpdate.setBankId(form.getBankId());
        dataUpdate.setBankAccount(form.getBankAccount());
        dataUpdate.setAccountName(form.getAccountName());
        dataUpdate.setBankCode(form.getBankCode());
        dataUpdate.setMaxDepositDaily(form.getMaxDepositDaily());
        dataUpdate.setMaxWithdrawDaily(form.getMaxWithdrawDaily());
        dataUpdate.setGroupCode(form.getGroupCode());
        dataUpdate.setBankId(form.getBankId());
        dataUpdate.setUpdatedAt(new Date());
        dataUpdate.setBalance(form.getBalance());
        companyAccountJpa.save(dataUpdate);
    }

    public CompanyAccountRes delete(long id) throws Exception {
        //  create insert
        Optional<CompanyAccount> dataDelete = companyAccountJpa.findById(id);
        if (!dataDelete.isPresent())
            return null;
        companyAccountJpa.deleteById(id);
        CompanyAccountRes returnData = new CompanyAccountRes();
        returnData.setEntityToRes(dataDelete.get());
        return returnData;
    }

}
