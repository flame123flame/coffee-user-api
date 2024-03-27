package coffee.provider.joker.vo.model;

import java.util.List;

import coffee.provider.joker.vo.res.TransactionGameListRes;
import coffee.provider.joker.vo.res.TransactionGameRes;
import lombok.Data;

@Data
public class TransactionList {
	private ListGame data;
	
	private List<TransactionGameRes> games;
	private String nextId;
	private String Message;
	
	@Data
	public static class ListGame{
		private List<TransactionGameListRes> Game;
	}
	
}
