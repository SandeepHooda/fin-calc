import { Component, OnInit, ViewEncapsulation, Output, EventEmitter,ViewChild, ElementRef, Renderer  } from '@angular/core';
import {   NG_VALUE_ACCESSOR,NgModel} from '@angular/forms';
import {FundService} from './fundservice';
import {Company} from './company';
import {NAV} from './nav';
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
  @Output() onCompanySelect :EventEmitter<number> = new EventEmitter();
    private companyIDSelected :number;
    public companyNames : SelectItem[];
    private navOfACompany : SelectItem[];
    private allNavs:Array<Company> = [];
    private httpError:any;
    @ViewChild(NgModel) model: NgModel; //http://blog.rangle.io/angular-2-ngmodel-and-custom-form-components/
    @ViewChild('spinnerElement') spinnerElement: ElementRef;
    constructor(private fundService :FundService,  private renderer: Renderer) { 
    
    }

    private notifyParentAboutCompanySelection(){
      this.onCompanySelect.emit(this.companyIDSelected); //Value comes from ValueAccessorBase
    } 
     

    ngOnInit(): void {
      
      this.companyNames = JSON.parse(localStorage.getItem('companyNames'));
      this.allNavs = JSON.parse(localStorage.getItem('allFunds'));
      if(!this. companyNames || this. companyNames.length <=0){
        this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','block');
        this.fundService.getAllFunds().subscribe( 
        funds => this.showAllFunds(funds),
        error => this.showError(error)
      );
      }else {
        this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
      }
       
  }

  private showAllFunds(funds:Array<Company>){
   console.log("data loaded")
    this.companyNames = [];
    let returnedNAV : SelectItem[] = [];
    let companiesLen: number = funds.length;
    for ( let i=0;i<companiesLen;i++){
      let navLen: number = funds[i].navs.length;
      this.companyNames.push({label:funds[i].companyName, value:i})
      
   }
   localStorage.setItem('companyNames', JSON.stringify(this.companyNames));
   localStorage.setItem('allFunds', JSON.stringify(funds));
     this.renderer.setElementStyle(this.spinnerElement.nativeElement, 'display','none');
  }

  private showError(error:any) {
    this.httpError = error;
  }
}