import { Injectable } from '@angular/core';
import { UserDataInterface } from '../service/rest/users/user-data.interface';

@Injectable()
export class GlobalRegistryService
{
    private isLoggedIn:boolean = false;

    private gravatarHash:string;

    constructor()
    {

    }

    public setisLoggedIn(value:boolean)
    {
        this.isLoggedIn = value;
    }

    public getIsLoggedIn():boolean
    {
        return this.isLoggedIn
    }

    public setGravatarHash(hash:string):void
    {
        this.gravatarHash = hash;
    }

    public getGravatarHash():string
    {
        return this.gravatarHash;
    }
}