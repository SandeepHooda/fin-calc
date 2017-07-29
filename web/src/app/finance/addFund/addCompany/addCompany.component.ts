import { Component, OnInit, ViewEncapsulation, Output, EventEmitter,ViewChild ,Input} from '@angular/core';
import {   NG_VALUE_ACCESSOR,NgModel} from '@angular/forms';
import {SelectItem} from 'primeng/primeng';
import {ValueAccessorBase} from '../../../common/value.access';


@Component({
 selector : 'add-company', 
  templateUrl: './addCompany.component.html',
  encapsulation: ViewEncapsulation.None,
   providers: [
    {provide: NG_VALUE_ACCESSOR, useExisting: AddCompany, multi: true}
  ], 
})

export class AddCompany  implements OnInit{
  @Input()
  companyNames :SelectItem[];
  @Output() onCompanySelect :EventEmitter<string> = new EventEmitter();
    private companyNameSelected :string;
   
    @ViewChild(NgModel) model: NgModel; //http://blog.rangle.io/angular-2-ngmodel-and-custom-form-components/
   
    constructor(  ) {  }

    private notifyParentAboutCompanySelection(){
      this.onCompanySelect.emit(this.companyNameSelected); //Value comes from ValueAccessorBase
    } 
     

    ngOnInit(): void {
     
  }

 
}