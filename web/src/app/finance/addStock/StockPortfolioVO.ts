
import {StockVO} from './stockVO';
export class StockPortfolioVO {

    public  allStocks : Array<StockVO>;
	public  totalGain : number; //absolute gain
	public  totalXirr : number;
	public  percentGainAbsolute : number;

	public  totalInvetment : number; //absolute value invested
}