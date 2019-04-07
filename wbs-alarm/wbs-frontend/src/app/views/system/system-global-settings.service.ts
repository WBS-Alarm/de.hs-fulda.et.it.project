import {Injectable} from "../../../../node_modules/@angular/core";
import {UserDataInterface} from "../../core/service/rest/users/user-data.interface";

@Injectable()
export class SystemGlobalSettingsService
{
    private traegerId:number;

    private benutzer:Array<any> = [];
    private zielorte:Array<any> = [];
    private kategorien:Array<any> = [];

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

    public setBenutzer(benutzer:Array<any>)
    {
        if(this.benutzer.indexOf(benutzer) === -1)
        {
            this.benutzer = this.benutzer.concat(benutzer);

            console.log('Benutzer:');
            console.log(this.benutzer);

        }
    }

    public setZielOrte(zielorte:Array<any>)
    {
        if(this.zielorte.indexOf(zielorte) === -1)
        {
            this.zielorte = this.zielorte.concat(zielorte);
        }
    }

    public setKategorien(kategorien:Array<any>)
    {
        if(this.kategorien.indexOf(kategorien) === -1)
        {
            this.kategorien = this.kategorien.concat(kategorien);
        }
    }

    public getSingleUser(userId:number):UserDataInterface
    {
        return this.benutzer.find((benutzer) =>
        {
            return benutzer.id === userId;
        })
    }
}