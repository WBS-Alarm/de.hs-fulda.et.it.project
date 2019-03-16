import { Injectable } from '@angular/core';

@Injectable()
export class GlobalRegistryService
{
    private isLoggedIn:boolean = false;

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
}