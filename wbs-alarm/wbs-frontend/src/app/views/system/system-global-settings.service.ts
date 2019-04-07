import {Injectable} from "../../../../node_modules/@angular/core";

@Injectable()
export class SystemGlobalSettingsService
{
    private traegerId:number;

    constructor()
    {

    }

    public getTraegerId():number
    {
        return this.traegerId;
    }

    public setTraegerId(id:number)
    {
        this.traegerId = id;
    }
}