import { Component, OnInit , ViewEncapsulation ,Input} from '@angular/core';

@Component({
 selector : 'add-fund-details', 
  templateUrl: './addFundDetails.component.html',
  encapsulation: ViewEncapsulation.None 
})

export class AddFundsDetails implements OnInit {
    @Input()
    companyIDSelected :string;
    @Input()
    schemeCode :number;
    private maxDate :Date;
    private investmentDate:Date;
    private error :string;
     constructor() {} 
     ngOnInit(): void {
         this.error = "";
         this.maxDate = new Date();
         this.maxDate.setDate(this.maxDate.getDate() - 1);
     }

     private fetchNAV(){
         this.error = "";
         let today:Date = new Date();
  
       
     }
}