package coffee.backoffice.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coffee.backoffice.finance.model.WalletTransfer;
import coffee.backoffice.finance.service.WalletTransferService;
import framework.constant.ResponseConstant.RESPONSE_MESSAGE;
import framework.constant.ResponseConstant.RESPONSE_STATUS;
import framework.model.DataTableResponse;
import framework.model.DatatableRequest;
import framework.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@CrossOrigin
@RestController
@ConditionalOnProperty(prefix="controller-config.enable", name="bo", havingValue = "true")
@RequestMapping("api/wallet-tranfer/")
public class WalletTranferController {
	
	@Autowired
	WalletTransferService walletTransferService;
	
	@PostMapping("get-paginate")
	public ResponseData<DataTableResponse<WalletTransfer>> getDepositListPaginate(@RequestBody DatatableRequest param) {
		ResponseData<DataTableResponse<WalletTransfer>> response = new ResponseData<DataTableResponse<WalletTransfer>>();
		try {
			response.setData(walletTransferService.getAllPaginate(param));
			response.setMessage(RESPONSE_MESSAGE.GET.SUCCESS);
			response.setStatus(RESPONSE_STATUS.SUCCESS);
			log.info("Success Calling API DepositListController => getDepositListPaginate");
		} catch (Exception e) {
			response.setMessage(RESPONSE_MESSAGE.GET.FAILED);
			response.setStatus(RESPONSE_STATUS.FAILED);
			log.error("Error Calling API DepositListController => getDepositListPaginate :" + e);
		}
		return response;
	}
}
