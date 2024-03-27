package framework.constant;

public class ProjectConstant {

	public class TRANSACTION_TYPE {
//		Income
		public static final String DEPOSIT = "DEPOSIT";
		public static final String MANUAL_ADD = "MANUAL_ADD";
		
//		Outlay
		public static final String PROMOTION_BALANCE = "PROMOTION_BALANCE";
		public static final String PROMOTION_BONUS = "PROMOTION_BONUS";
		public static final String WITHDRAW = "WITHDRAW";
		public static final String WITHDRAW_AF = "WITHDRAW_AF";
		public static final String MANUAL_SUB = "MANUAL_SUB";
		public static final String REBATE = "REBATE";
		public static final String CASHBACK = "CASHBACK";
		
//		Transfer
		public static final String TRANFER_TO_SUB = "TRANFER_TO_SUB";
		public static final String TRANFER_TO_MAIN = "TRANFER_TO_MAIN";
	}
	
	public class WALLET {
		public static final String MAIN_WALLET = "MAIN";
		public static final String SUB_WALLET = "SUB";
		public static final String AFFILIATE_WALLET = "AFFILIATE";
	}
	
	public class WALLET_TYPE {
		public static final String BALANCE = "BALANCE";
		public static final String BONUS = "BONUS";
	}
	
	public class GAME {
		public static final String FAV= "FAV";
		public static final String NON_FAV= "NON_FAV";
	}
	public class INBOX_MESSAGE {
		public static final String NORMAL= "NORMAL";
		public static final String PROMOTION= "PROMOTION";
		public static final String AUTO= "AUTO";
	}
	
	public class DEPOSIT {
		public static final String BO = "BO";
		public static final String BB = "BB";
	}
	
	public class BANKBOT_BANK_ID {
		public static final String KTB = "006";
		public static final String SCB = "014";
		public static final String KBANK = "004";
	}

	public class WITHDRAW{
		public static final String REJECT= "REJECT";
		public static final String PENDING= "PENDING";
		public static final String BANK_APPROVED= "BANK_APPROVED";
		public static final String WITHDRAW_APPROVED= "WITHDRAW_APPROVED";
	}
	
	public class WITHDRAW_CONDITION {
		public static final String PASS= "PASS";
		public static final String PENDING= "PENDING";
		public static final String NOT_PASS= "NOT_PASS";
		
		public static final String REBATE= "REBATE";
		public static final String GENERAL= "GENERAL";
		public static final String PROMOTION = "PROMOTION";
		
		public static final String FIXED= "FIXED";
		public static final String MULTIPLIER= "MULTIPLIER";
	}
	
	public class USER_BANK_STATUS {
		public static final String ACCESS= "ACCESS";
		public static final String REJECT= "REJECT";
		public static final String CONDITION= "CONDITION";
	}
	
	public class DEPOSIT_SYSTEM_STATUS {
		public static final String MANUAL= "MANUAL";
		public static final String AUTO= "AUTO";
		public static final String CLOSED= "CLOSED";
	}
	
	public class STATUS {
		public static final String SUCCESS = "SUCCESS";
		public static final String FAILED = "FAILED";
		
		public static final String SHOW = "SHOW";
		public static final String HIDE = "HIDE";
		
		public static final String ACTIVE = "ACTIVE";
		public static final String INACTIVE = "INACTIVE";
		
		public static final String PENDING = "PENDING";
		public static final String APPROVE = "APPROVE";
		public static final String REJECT = "REJECT";
		
		public static final String MX_0000 = "0000";
		public static final String MX_1018 = "1018";
		public static final String MX_1028 = "1028";
		public static final int SBO_0 = 0;
		public static final String SA_0 = "0";
	}
	
	public class LOTTO_STATUS{
		public static final String WIN = "WIN";
		public static final String LOSE = "LOSE";
		public static final String CANCEL = "CANCEL";
	}
	
	public class JOKER_PROVIDER_METHOD {
		public static final String LIST_GAMES = "ListGames";
		public static final String PLAY = "PLAY";
		public static final String GET_CREDIT = "GC";
		public static final String TRANFER_CREDIT = "TC";
		public static final String CREATE_USER = "CU";
	}

	public class PROVIDERS{
		// AE Casino Live
		public static final String MX = "MX";
		// Kingmaker Live
		public static final String KM = "KM";
		// SA Gaming Live
		public static final String SA = "SA";
		
		// JOKER Slot
		public static final String JOKER = "JOKER";
		// Jili Slot
		public static final String JILI = "JILI";
		// PG Slot
		public static final String PG = "PG";
		// RT Slot
		public static final String RT = "RT";
		
		// SBOBET Sports 
		public static final String SBO = "SBO";
		// Lottery API
		public static final String LOTTO = "LOTTO";
	}
	
	public class PROVIDER_OPEN{
		public static final String ONEPAGE = "ONEPAGE";
		public static final String LIST = "LIST";
	}
	
	public class CURRENCY{
		public static final String THB = "THB";
	}

 	
	public class HISTORY_TYPE{
		public static final String WIN = "WIN";
		public static final String DRAW = "DRAW";
		public static final String LOSE = "LOSE";
		public static final String RUNNING = "RUNNING";
		public static final String PENDING = "PENDING";
		public static final String DEPOSIT = "DEPOSIT";
		public static final String WITHDRAW = "WITHDRAW";
		public static final String PROMOTION = "PROMOTION";
		public static final String CASHBACK = "CASHBACK";
		public static final String REBATE = "REBATE";
	}
	
	public class HISTORY_PERIOD{
		public static final String CURRENT = "CURRENT";
		public static final String PAST = "PAST";
	}
	
	public class  TRANSACTION_METHOD{
		public static final String TRANSACTION_HOURS = "TS";
		public static final String TRANSACTION_MINUTES = "TSM";
		public static final String TRANSACTION_SUMMARY = "TRX";
		public static final String TRANSACTION_WINLOSS = "RWL";
		public static final String TRANSACTION_HISTORY = "History";
	}
	
	public class LOGIN_MESSAGE{
		public static final String SUCCESS = "เข้าสู่ระบบสำเร็จ";
		public static final String BLOCK = "USERNAME โดนระงับจากระบบชั่วคราว";
		public static final String FAILED = "เข้าสู่ระบบไม่สำเร็จ";
		public static final String PASSWORD_VALID = "PASSWORD ไม่ถูกต้อง";
		public static final String USERNAME_VALID = "USERNAME ไม่ถูกต้อง";
	}
}
