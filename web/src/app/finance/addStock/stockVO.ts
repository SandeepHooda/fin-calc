
export class StockVO {
    public  profileID : number;
    public isin : string;
    public companyName : string;
    public ticker : string;
    public exchange : string;
    public purchaseDate : Date;
    public purchaseDateStr : string;
    public purchaseQty : number;
    public purchasePrice : number;
    public investmentAmount : number;
    public lastTradePrice : string;
    public asOfDate : string;

    public  absoluteGain :number;
	public  currentValue :number;
	public  currentPrice:number;
	public  lastKnownPrice:number;
    public  xirr:number;
    public  percentGainAbsolute:number;
    public  percentGainAnual:number;
    public  companyXirr:number;
    public  companyAbsoluteGainPercent:number;
    public  companyTotalInvestment:number;
    public  companyCurrentValue:number;
    public  companyTotalGain:number;
}