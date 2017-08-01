import {Withdrawal} from './withdrawal';
export class SipSchemeVO {
    public schemeName : string;
    public startDate : Date;
    public endDate : Date;
    public sipAmount : number; 
    public returnOnInsvement : number;
    public withdrawlsRows : Array<Withdrawal> = [ { "date" : null , "amount" : null } ]; 
}