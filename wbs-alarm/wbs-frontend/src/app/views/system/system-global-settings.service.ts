import {Injectable} from "../../../../node_modules/@angular/core";
import {UserDataInterface} from "../../core/service/rest/users/user-data.interface";
import { SystemZielortInterface } from './components/targetplaces/data/system-zielort.interface';

@Injectable()
export class SystemGlobalSettingsService
{
    private traegerId:number;

    private traegers:Array<any> = [];
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

    public getSingleTraeger()
    {
        return this.traegers.find((traeger:any) => this.traegerId === traeger.id)
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

    public getSingleZielort(id:number):SystemZielortInterface
    {
        console.log(this.zielorte.find((zielort:any) => id === zielort.id));

        return this.zielorte.find((zielort:any) => id === zielort.id)
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