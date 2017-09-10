export class Profile {
    public investmentDate : string;
    public exitDate : string;
    public nav: number;
    public investmentAmount: number;
    public units: number;
    public exitUnits: number;
    public schemeName : string;
    public schemeCode : string;
    public absoluteGain : number;
    public currentValue: number;
    public xirr: number;
    public percentGainAbsolute : number;
    public percentGainAnual : number;
    public companyName : string;
    public profileID : number;
    public currentNav : number;
    public companyXirr : number;
    public companyAbsoluteGainPercent : number;
    public companyTotalInvestment : number;
    public companyCurrentValue : number;
    public companyTotalGain : number;
    public navDate : String;
    private  lastKnownNav : number;
}