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

private httpError:String


  constructor(private xirrService:XirrService) {

   }

 private showName(name: string) {
    if (null == name || 'null' == name || '' == name){
      this.signedInUser = undefined;
    }else {
      this.signedInUser = name;
    }
    
  }
  private showError(error:any) {
    this.httpError = error;
  }
 
  ngOnInit(): void {
   this.xirrService.signedUserName().subscribe( 
        name => this.showName(name),
        error => this.showError(error)
      ); 
     
 
  }
  
   
}
