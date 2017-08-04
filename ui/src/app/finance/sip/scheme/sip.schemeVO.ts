import {Withdrawal} from './withdrawal';
export class SipSchemeVO {
    public schemeID : number;
    public schemeName : string;
    public startDate : Date;
    public endDate : Date;
    public startDateLong : number;
    public endDateLong : number;
    public sipAmount : number; 
    public returnOnInsvement : number;
    public withdrawlsRows : Array<Withdrawal> = [ { "date" : null , "dateLong" : null, amount : null } ]; 
}