import { Component, OnInit, ViewEncapsulation , Input,Output, EventEmitter } from '@angular/core';
import {SelectItem} from 'primeng/primeng';
import {Company} from '../company';

@Component({
 selector : 'add-profile', 
  templateUrl: './addProfile.component.html',
  encapsulation: ViewEncapsulation.None 
})

export class AddProfile implements OnInit {
  private allFunds:Array<Company>;
  private profiles:SelectItem[];
  private companyID:number;
  private companyName: string;
  private selectedSchemeCode:number;
  @Input()
    companyIDSelected :string;
    @Output() onSchemeSelect :EventEmitter<number> = new EventEmitter();
    constructor() { }

    ngOnInit(): void {
      this.profiles = [];
     this.allFunds = JSON.parse(localStorage.getItem('allFunds'));
     this.companyID = parseInt(this.companyIDSelected.substring(0,this.companyIDSelected.indexOf("#")));
     this.companyName = this.companyIDSelected.substring(this.companyIDSelected.indexOf("#")+1);
     let companyProfiles = this.allFunds[this.companyID].navs;
     for (let i=0;i<companyProfiles.length;i++){
      this.profiles.push({label:companyProfiles[i].SchemeName, value:companyProfiles[i].SchemeName});
     }
  }

  private notifyParentAboutSchemeSelection(){
      this.onSchemeSelect.emit(this.selectedSchemeCode); //Value comes from ValueAccessorBase
    } 

 
}