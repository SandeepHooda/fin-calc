import { Injectable , Inject} from '@angular/core';
import { Http, Response }          from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import  'rxjs/add/operator/map';
import { Headers, RequestOptions } from '@angular/http';
import {StockVO} from './stockVO';
import {StockPortfolioVO} from './StockPortfolioVO';
import { DOCUMENT } from '@angular/platform-browser';
import {WishList} from './wishList';
@Injectable()
export class StockService {
     

  private getAllListedStocksUrl:string = '/web/data/allListedStocks.json';  // URL to web API
  private addStockToProfileUrl:string = '/AddStock';
  private GetStockProfileUrl:string = '/GetStockProfile';
  
 
     constructor (private http: Http, @Inject(DOCUMENT) document: any) {}
   
    getAllListedStocks(): Observable<Array<StockVO>> {
       return this.http.get(this.getAllListedStocksUrl)
                        .map(this.extractData)
                        .catch(this.handleError);
    }
    saveWishList(wishListEquity:  Array<WishList>) : Observable<string> {
      let hostName:string = '';
      if (document.location.href.indexOf("localhost") > 0){
       hostName = "http://localhost:8080"
      }
       let saveWishListUrl = hostName+"/SaveWishList"
     var headers = new Headers({ 'Content-Type': 'application/json' });
     var options = new RequestOptions({ headers: headers });
     return this.http.post(saveWishListUrl, { wishListEquity }, options)
               .map(this.extractData)
               .catch(this.handleError);
    }
    getStockProfile(): Observable<StockPortfolioVO> {
      let hostName:string = '';
      if (document.location.href.indexOf("localhost") > 0){
        hostName = "http://localhost:8080"
       }
       return this.http.get(hostName+this.GetStockProfileUrl)
                        .map(this.extractData)
                        .catch(this.handleError);
    }
    getWishList(): Observable<Array<WishList>> {
      let hostName:string = '';
      if (document.location.href.indexOf("localhost") > 0){
        hostName = "http://localhost:8080"
       }
       return this.http.get(hostName+'/GetWishList')
                        .map(this.extractData)
                        .catch(this.handleError);
    }
    getStockProfile_eq_archive(): Observable<StockPortfolioVO> {
      let hostName:string = '';
      if (document.location.href.indexOf("localhost") > 0){
        hostName = "http://localhost:8080"
       }
       let url  = "/GetStockProfile_eq_archive";
      return this.http.get(hostName+url)
                       .map(this.extractData)
                       .catch(this.handleError);
   }
   public editProfiles_eq_archive(allProfiles_eq_archive:Array<StockVO>) : Observable<string>{
    let hostName:string = '';
    if (document.location.href.indexOf("localhost") > 0){
     hostName = "http://localhost:8080"
    }
     let EditProfiles_eq_archiveUrl = hostName+"/EditProfiles_eq_archive"
   var headers = new Headers({ 'Content-Type': 'application/json' });
   var options = new RequestOptions({ headers: headers });
   return this.http.post(EditProfiles_eq_archiveUrl, { allProfiles_eq_archive }, options)
             .map(this.extractData)
             .catch(this.handleError);
 }
    deleteFromProfile(profileID : number) : Observable<string>{
      let hostName:string = '';
      if (document.location.href.indexOf("localhost") > 0){
        hostName = "http://localhost:8080"
       }
      return this.http.delete(hostName+'/DeleteStockFromProfile?profileID='+profileID)
            .map(this.extractData)
            .catch(this.handleError);
    }
  private extractData(res: Response) {
  
    let body = res.json();
    return body;
  }
 
 public addStockToProfile(selectedStock : StockVO) : Observable<string>{
  let hostName:string = '';
  if (document.location.href.indexOf("localhost") > 0){
    hostName = "http://localhost:8080"
   }
     var headers = new Headers({ 'Content-Type': 'application/json' });
  var options = new RequestOptions({ headers: headers });
  return this.http.post(hostName+this.addStockToProfileUrl, { selectedStock }, options)
            .map(this.extractData)
            .catch(this.handleError)
 }
  private handleError (error: Response | any) {
    // In a real world app, you might use a remote logging infrastructure
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    
    return Observable.throw(errMsg);
  }
}