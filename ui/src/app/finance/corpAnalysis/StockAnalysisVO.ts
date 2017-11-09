
export class StockAnalysisVO {
    public  _id : string  ;
    public category : string  ;
	
	public  currentMarketPrice : number ;
	public  salesTurnOver : number ;
    public  netProfit1 : number;
    public netProfit2 : number; 
    public netProfit3 : number; 
    public netProfit4 : number;
    public netProfit5 : number ;
	public  totalStockHoldersFund : number ;
	public  totalDebt : number ;
	public  bookValue : number ;
	public  pe : number ;
	public  eps : number ;
	public  high52 : number ;
	public  low52 : number ;
	public  currentAssets : number ;
	public  currentLiabilities : number ;
	public  netBlock : number ;
	
	//calculated
	public  debt_eq : number ;
	public  p_e : number ;
	public  p_b : number ;
	public  low52DifferencePercent : number ;
	public  high52DifferencePercent : number ;
    public  growthRate1 : number;
    public growthRate2 : number;
    public growthRate3 : number;
    public  growthRate4 : number ;
	public  p_e_g : number ;
	public  profit_Equity : number ;
	public  price_profit : number ;
	public  asset_Liabilities : number ;
	public  profit_sales : number ;
	
	public  promotoresShare : number ;
	public  preference : number ;
}