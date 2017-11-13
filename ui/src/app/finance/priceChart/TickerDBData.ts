import {StockPrice} from './StockPrice';
export class TickerDBData {
	public  _id : string;
	public currentMarketPrice : number;
	public  xirr5: number;
	public  xirr10: number;
	public  xirr30: number;
	public  xirr182: number;
    public  xirr365: number;
    public stockPriceList : Array<StockPrice>

} 