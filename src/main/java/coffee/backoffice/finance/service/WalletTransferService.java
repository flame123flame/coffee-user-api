package coffee.backoffice.finance.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.backoffice.finance.model.WalletTransfer;
import coffee.backoffice.finance.repository.dao.WalletTranferDao;
import coffee.backoffice.finance.repository.jpa.WalletTransferRepository;
import framework.constant.ProjectConstant.STATUS;
import framework.constant.ProjectConstant.WALLET;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WalletTransferService {

	@Autowired
	private WalletTransferRepository walletTransferRepository;
	
	@Autowired
	private WalletTranferDao walletTranferDao;
   
	public void tranferFromMain(String orderId , String username , String to , BigDecimal amount) {
		WalletTransfer data = new WalletTransfer();
		data.setOrderId(orderId);
		data.setUsername(username);
		data.setTransferAmount(amount);
		data.setFromWallet(WALLET.MAIN_WALLET);
		data.setToWallet(to);
		data.setCreatedBy("_system");
		data.setStatus(STATUS.SUCCESS);
		data.setRemark("");
		walletTransferRepository.save(data);
	}
	
	public void tranferToMain(String orderId ,String username , String from , BigDecimal amount) {
		WalletTransfer data = new WalletTransfer();
		data.setOrderId(orderId);
		data.setUsername(username);
		data.setFromWallet(from);
		data.setTransferAmount(amount);
		data.setToWallet(WALLET.MAIN_WALLET);
		data.setCreatedBy("_system");
		data.setStatus(STATUS.SUCCESS);
		data.setRemark("");
		walletTransferRepository.save(data);
	}
	
	public DataTableResponse<WalletTransfer> getAllPaginate(DatatableRequest req) {
		DataTableResponse<WalletTransfer> paginateData = walletTranferDao.paginateAllWalletTranfer(req);
		DataTableResponse<WalletTransfer> dataTable = new DataTableResponse<>();
		List<WalletTransfer> data = paginateData.getData();

		dataTable.setRecordsTotal(paginateData.getRecordsTotal());
		dataTable.setDraw(paginateData.getDraw());
		dataTable.setData(data);
		dataTable.setPage(req.getPage());
		return paginateData;
	}
}
