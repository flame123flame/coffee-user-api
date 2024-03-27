package coffee.backoffice.finance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.finance.model.Bank;
import coffee.backoffice.finance.repository.dao.BankDao;
import coffee.backoffice.finance.repository.jpa.BankRepository;
import coffee.backoffice.finance.vo.req.BankReq;
import coffee.backoffice.finance.vo.res.BankRes;
import coffee.backoffice.player.vo.res.TagManagementRes;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.utils.GenerateRandomString;
import framework.utils.UserLoginUtil;

@Service
public class BankService {
	@Autowired
	private BankRepository bankRepo;

	@Autowired
	private BankDao bankDao;

	public List<Bank> getBankAll() {
		return bankRepo.findAll();
	}

	public DataTableResponse<BankRes> getPaginateModel(DatatableRequest req) {
		DataTableResponse<BankRes> dataTable = new DataTableResponse<>();
		DataTableResponse<BankRes> bank = new DataTableResponse<>();
		bank = bankDao.paginate(req);
		dataTable.setRecordsTotal(bank.getRecordsTotal());
		dataTable.setDraw(bank.getDraw());
		dataTable.setData(bank.getData());
		return dataTable;
	}

	public void saveBank(BankReq form) {
		Bank dataBank = new Bank();
		dataBank.setBankCode(GenerateRandomString.generate());
		dataBank.setBankNameEn(form.getBankNameEn());
		dataBank.setBankNameTh(form.getBankNameTh());
		dataBank.setBankUrl(form.getBankUrl());
		dataBank.setBankImg(form.getBankImg());
		dataBank.setEnable(form.getEnable());
		dataBank.setCreatedBy(UserLoginUtil.getUsername());
		bankRepo.save(dataBank);
	}

	public void editBank(BankReq form) {
		Bank dataBank = bankRepo.findByBankCode(form.getBankCode());
		dataBank.setBankNameEn(form.getBankNameEn());
		dataBank.setBankNameTh(form.getBankNameTh());
		dataBank.setBankUrl(form.getBankUrl());
		dataBank.setBankImg(form.getBankImg());
		dataBank.setEnable(form.getEnable());
		dataBank.setUpdatedBy(UserLoginUtil.getUsername());
		dataBank.setUpdatedDate(new Date());
		bankRepo.save(dataBank);
	}

	public BankRes getBankByCode(String bankCode) {
		Bank dataBank = bankRepo.findByBankCode(bankCode);
		BankRes dataRes = new BankRes();
		dataRes.setId(dataBank.getId());
		dataRes.setBankCode(dataBank.getBankCode());
		dataRes.setBankNameEn(dataBank.getBankNameEn());
		dataRes.setBankNameTh(dataBank.getBankNameTh());
		dataRes.setBankImg(dataBank.getBankImg());
		dataRes.setBankUrl(dataBank.getBankUrl());
		dataRes.setBankImg(dataBank.getBankImg());
		dataRes.setEnable(dataBank.getEnable());
		return dataRes;
	}

	@Transactional
	public void deleteBankByCode(String bankCode) {
		bankRepo.deleteByBankCode(bankCode);
	}
}
