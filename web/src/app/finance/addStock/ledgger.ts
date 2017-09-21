import { Component, OnInit, ViewEncapsulation , ViewChild, Input, ElementRef, Renderer  } from '@angular/core';
import {SelectItem} from 'primeng/primeng';
import {Message} from 'primeng/primeng';

import {Router} from '@angular/router';
import {EventService} from '../../common/EventService';

import {WishList} from './wishList';
import {StockService} from './stockService';
@Component({
 selector : 'ledgger', 
  templateUrl: './ledgger.html',
  encapsulation: ViewEncapsulation.None 
})

export class Ledgger implements OnInit {
 
  private msgs : Message[] = [];
 
  private displayConfirmation : boolean = false;
  private profileIDToBedeleted : number;
 
  private displayConfirmation_wishList : boolean = false;
  
 @ViewChild('spinnerElement') spinnerElement: ElementRef;

 
 private profileIDToBedeleted_pastEQ : number;
 private wishListEquity : Array<WishList> = [];
 private eq_archive_totalReturn : number;
 private eq_archive_totalProfit : number;
 private eq_archive_totalProfitPercent : number;
  
    constructor(private renderer: Renderer, private stockService : StockService,
    private eventService : EventService,private router:Router) {} 
   
    ngOnInit(): void {
  
     this.refreshPage();
     
      
  }

  private addToWishList(){
    let wishList = this.wishListEquity;
    let wish : WishList = new WishList();
    wish.profileID = new Date().getTime();
    wishList.push(wish);
    this.wishListEquity = [];
    if (wishList){
      for(let i=0;i<wishList.length;i++ ){
        this.wishListEquity.push(wishList[i]);
      }
    }
    
    console.log("wishListEquity "+this.wishListEquity)
  }
  private deleteFromWishList(profileID : number){
    console.log(" do you want to delete "+profileID)
     this.profileIDToBedeleted_pastEQ = profileID;
     this.displayConfirmation_wishList = true;
     
  }
  private confirmDeleteFromWishList(){
    this.displayConfirmation_wishList = false;
  let  wishListAfterDelete : Array<WishList> = [];
  for (let i =0;i<this.wishListEquity.length;i++){
       if(this.wishListEquity[i].profileID != this.profileIDToBedeleted_pastEQ){
        wishListAfterDelete.push(this.wishListEquity[i]);
       }else {
         console.log(" I will delte "+this.profileIDToBedeleted_pastEQ)
       }
     }
     this.wishListEquity = wishListAfterDelete;
     this.saveWishList();
  }

 
private toggleInfo() {
        
        if ( this.msgs.length ==0){
          this.msgs.push({severity:'info: ', summary:'Info', detail:"Track your Stock's absolute and % growth."});
        }else {
          this.msgs = [];
        }
        
    }

 
   private refreshPage(){
    
     this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
     
    this.stockService.getWishList().subscribe(
      funds => this.gotWishList(funds),
      error => this.showError(error));
  }
private deleteFromProfile(profileID : number){
  this.profileIDToBedeleted = profileID;
  this.displayConfirmation = true;
}  
private confirmDelete(){
this.displayConfirmation = false;
 console.log("deleting "+this.profileIDToBedeleted);
 this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
 this.stockService.deleteFromProfile(this.profileIDToBedeleted).subscribe( 
        msg => this.deletedFromProfile(msg),
        error => this.showError(error));
}
private saveWishList(){
  this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
  this.stockService.saveWishList(this.wishListEquity).subscribe( 
    msg => this.saveWishListSuccess(msg),
    error => this.showError(error));
}
private getWishList(){
  this.stockService.getWishList().subscribe( 
    msg => this.gotWishList(msg),
    error => this.showError(error));
}
private deletedFromProfile(message:string){
   this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
   this.refreshPage();
}

  private gotWishList(wishList : Array<WishList>){
    this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
    this.wishListEquity = wishList;
  }
  private saveWishListSuccess(result: string){
    this.getWishList();
  }
  
 
 

    private editSuccess(data : string){
      this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
    }


    private showError(error:any) {
      
      this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
    }

    

}