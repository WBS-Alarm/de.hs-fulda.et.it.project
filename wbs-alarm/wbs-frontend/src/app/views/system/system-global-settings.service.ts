import {Injectable} from "../../../../node_modules/@angular/core";
import {UserDataInterface} from "../../core/service/rest/users/user-data.interface";
import { SystemZielortInterface } from './components/targetplaces/data/system-zielort.interface';
import {SystemCarrierInterface} from "./components/carrier/data/system-carrier.interface";
import {SystemCategoryInterface} from "./components/categories/data/system-category.interface";
import { UsersService } from '../../core/service/rest/users/users.service';

@Injectable()
export class SystemGlobalSettingsService
{
    public traegerId:number;

    public traegers:Array<any> = [];
    public benutzer:Array<any> = [];
    public zielorte:Array<any> = [];
    public kategorien:Array<any> = [];

    constructor(public userService:UsersService)
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

    public getSingleTraeger(id:number):SystemCarrierInterface
    {
        return this.traegers.find((traeger:SystemCarrierInterface) => id === traeger.id)
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

    public setTraegers(traegers:Array<any>)
    {
        if(this.traegers.indexOf(traegers) === -1)
        {
            this.traegers = this.traegers.concat(traegers);
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

    public getSingleCategory(categoryId:number):SystemCategoryInterface
    {
        return this.kategorien.find((kategorie) => kategorie.id === categoryId);
    }

    public getSingleUser(userId:number):UserDataInterface
    {
        return this.benutzer.find((benutzer) =>
        {
            return benutzer.id === userId;
        })
    }
}