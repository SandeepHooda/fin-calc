
export class StockAnalysisVO {
	public _id : string;
	public category : string;
	public  maxCaptureYear : number ;
	public  companyName : string;
	public  companyCode : string;
	public moneyControlResources : string[] ;
	public  borderColor : string;
	public  fill : boolean= false;
	/**
	 * For a good company -
	 * 	Revenue should increase yearly bases - if revenue decreases it means company is no more growing. it is stagnent
	 *  Profit should also increase - if revenue increases but profit decreases it means that cost has increases
	 *  Profit margin - if revenue increases numbers and profit increases more than number than it means profitability of company (i.e. PBDITMargin) is increasing
	 */
	public  revenueOperationPerShare : number[];//it is Sales not income (Income = Sales - Cost)
	public  PBDITPerShare : number[];//Profit before depriciations ,intrest  & taxes
	public  PBDITMargin : number[];// =(profit/revenue) @@@@@@@@@ profitability
	
	public  revenueOperationPerShareYOY  : number[];
	public  PBDITPerShareYOY : number[];
	public  PBDITMarginYOY  : number[];
	
	public  quaterlyRevenueOp  : number[];
	public  quaterlyPBDIT : number[] ;
	public  quaterlyPBDITMargin  : number[];
	public  quaterlyRevenueOpQoQ  : number[];
	public  quaterlyPBDITQoQ  : number[];
	public  quaterlyPBDITMarginQoQ  : number[];
}