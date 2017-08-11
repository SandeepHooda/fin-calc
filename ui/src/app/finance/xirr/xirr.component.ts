import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import  {XirrService} from './xirr-service';



@Component({
 selector : 'xirr', 
  templateUrl: './xirr.component.html',
  styleUrls: [ './xirr.component.css' ],
  encapsulation: ViewEncapsulation.None 
})
export class XIRR implements OnInit {
private signedInUser:string ;
private signedInUserEmail:string ;

private httpError:String


  constructor(private xirrService:XirrService) {

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
    this.xirrService.loadChartData().subscribe( 
        result => {},
        error => {}
      );
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
    
   //localStorage.setItem('signedInUser', 'Sandeep Hooda');  
   //localStorage.setItem('signedInUserEmail', 'sonu.hooda@gmail.com'); 
  //this.signedInUser = "sonu";
}

private deleteLocalStorage(){
  localStorage.removeItem('signedInUser');
  localStorage.removeItem('signedInUserEmail');
  localStorage.removeItem('lastKnownPortFolio');
}
   
}
