import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import  {XirrService} from './xirr-service';
import {EventService} from '../../common/EventService';
import {Router} from '@angular/router';

@Component({
 selector : 'xirr', 
  templateUrl: './xirr.component.html',
  styleUrls: [ './xirr.component.css' ],
  encapsulation: ViewEncapsulation.None 
})
export class XIRR implements OnInit {
private signedInUser:string ;
private noAuthState : boolean;
private signedInUserEmail:string ;
private showRefreshButton : boolean;

private httpError:String


  constructor(private xirrService:XirrService, private eventService : EventService, private route : Router) {

   }
private refreshPage(){
  this.eventService.refresh();
}
 private showName(name: string) {
    if (null == name || 'null' == name || '' == name){
      this.signedInUser = undefined;
    }else {
      this.signedInUser = name;
      //localStorage.setItem('signedInUser', name);
    }
    
  }
  private showEmail(name: string) {
    if (null == name || 'null' == name || '' == name){
      this.signedInUserEmail = undefined;
    }else {
      this.signedInUserEmail = name;
      //localStorage.setItem('signedInUserEmail', name);
    }
    
  }
  
  private showError(error:any) {
    this.httpError = error;
  }
 
  ngOnInit(): void {
    //Get chart historical data on page load- just make a background call so that serve refresh its cache
    
      /*this.xirrService.loadMyProfileChartData().subscribe( 
        result => {},
        error => {}
      );*/
      
      
   this.signedInUserEmail =  localStorage.getItem('signedInUserEmail');
   this.signedInUser =  localStorage.getItem('signedInUser');
   if (!this.signedInUserEmail){
    this.xirrService.signedUserName().subscribe( 
        name => this.showName(name),
        error => this.showError(error)
      ); 
      this.xirrService.signedUserEmail().subscribe( 
        email => this.showEmail(email),
        error => this.showError(error)
      );
    
   }
    
    this.route.events.subscribe(params => {
      if (this.route.url === '/lumpsump' || 
      this.route.url === '/Analytics' || this.route.url ==='/Stock'){
        this.showRefreshButton = true;
      }else {
        this.showRefreshButton = false;
      }
      if (this.route.url === '/PriceChart' || this.route.url === '/BulkDeals' 
      || this.route.url === '/Radar'|| this.route.url === '/Gems' 
      || this.route.url === '/HighLow'){
        this.noAuthState = true;
      }else {
        this.noAuthState = false;
      }
       localStorage.setItem('mostRecentState', this.route.url);
   
});
   //localStorage.setItem('signedInUser', 'Sandeep Hooda');  
   //localStorage.setItem('signedInUserEmail', 'sonu.hooda@gmail.com'); 
  //this.signedInUser = "sonu";
  let  mostRecentState : string =localStorage.getItem('mostRecentState');
  if (mostRecentState){
    this.route.navigateByUrl(mostRecentState);
  }
  
}

private deleteLocalStorage(){
  localStorage.removeItem('signedInUser');
  localStorage.removeItem('signedInUserEmail');
  localStorage.removeItem('lastKnownPortFolio');
  localStorage.removeItem('allListedStocksNSE');
  localStorage.removeItem('allListedStocksBSE');
  localStorage.removeItem('allListedStocksBSE');
  localStorage.removeItem('lastKnownStockProfile');
  
}
   
}
