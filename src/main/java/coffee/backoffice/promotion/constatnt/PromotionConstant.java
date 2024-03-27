package coffee.backoffice.promotion.constatnt;

public class PromotionConstant {
	public static class Type {
		public static String registration = "Registration";
		public static String firstAndSecondDeposit = "1st&2ndDeposit";
		public static String generalDeposit = "GeneralDeposit";
		public static String customized = "Customized";
		public static String posting = "Posting";
	}

	public static class FirstAndSecondDepositType {
		public static String first = "1st";
		public static String second = "2nd";
	}

	public static class PromoPeriodType {
		public static String indefinite = "Indefinite";
		public static String datePeriod = "DatePeriod";
	}
	
	public static class VerificationType {
		public static String Auto = "Auto";
		public static String Manual = "Manual";
	}
	
	public static class WalletType {
		public static String BALANCE = "BALANCE";
		public static String BONUS = "BONUS";
	}

	public static class Status {
		public static String active = "Active";
		public static String inactive = "Inactive";

		public static String SHOW = "SHOW";
		public static String HIDE = "HIDE";
		
		public static String REJECT = "REJECT";
		public static String APPROVE = "APPROVE";
		public static String PENDING = "PENDING";
		
	}

	public static class BonusType {
		public static String fixedAmount = "FixedAmount";
		public static String ratioAmount = "RatioAmount";
		public static String randomAmount = "RandomAmount";
	}
	
	public static class ConditionType {
		public static String fixedAmount = "FixedAmount";
		public static String multiplierCondition = "MultiplierCondition";
	}

}
