import { Component, OnInit, ViewEncapsulation , Input,Output, EventEmitter } from '@angular/core';
import {SelectItem} from 'primeng/primeng';
import {Company} from '../company';
import {NAV} from "../nav";

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
    companyNameSelected :string;
    @Output() onSchemeSelect :EventEmitter<NAV> = new EventEmitter();
    constructor() { }

    ngOnInit(): void {
      this.profiles = [];
     this.allFunds = JSON.parse(localStorage.getItem('allFunds'));
     this.companyID = parseInt(this.companyNameSelected.substring(0,this.companyNameSelected.indexOf("#")));
     this.companyName = this.companyNameSelected.substring(this.companyNameSelected.indexOf("#")+1);
     let companyProfiles = this.allFunds[this.companyID].navs;
     for (let i=0;i<companyProfiles.length;i++){
      this.profiles.push({label:companyProfiles[i].SchemeName, value:companyProfiles[i].SchemeCode});
     }
  }

  private notifyParentAboutSchemeSelection(){
    let nav: NAV = new NAV();
      nav.SchemeCode = this.selectedSchemeCode;
      for (let i=0;i<this.profiles.length;i++){
        if (this.profiles[i].value == this.selectedSchemeCode){
          nav.SchemeName = this.profiles[i].label;
          break;
        }
      }
      this.onSchemeSelect.emit(nav); //Value comes from ValueAccessorBase
    } 

 
}