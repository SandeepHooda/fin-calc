import {Withdrawal} from './withdrawal';
export class SipSchemeVO {
    public schemeID : number;
    public schemeName : string;
    public startDate : Date;
    public endDate : Date;
    public startDateLong : number;
    public endDateLong : number;
    public sipAmount : number; 
    public pensionStartDate : Date;
    public pensionEndDate : Date;
    public pensionStartDateLong : number;
    public pensionEndDateLong : number;
    public pensionAmount : number;  
    public returnOnInsvement : number; //Xirr
    public loanIntrestRate : number; // Compount intrest rate
    public withdrawlsRows : Array<Withdrawal> = [ { "date" : null , "dateLong" : null, amount : null } ]; 
}