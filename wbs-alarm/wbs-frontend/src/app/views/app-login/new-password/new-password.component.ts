import {Component} from "@angular/core";
import {ActivatedRoute, Router} from '@angular/router';
import { OnInit } from '@angular/core';
import {AlertService} from "@plentymarkets/terra-components";
import {NewPasswordService} from "./service/new-password.service";

@Component({
    selector: 'new-password',
    templateUrl: './new-password.component.html',
    styleUrls: ['./new-password.component.scss']
})
export class NewPasswordComponent implements OnInit
{
    public token:string = '';

    public password:string ='';
    public repeatPassword:string ='';

    constructor(private route:ActivatedRoute,
                private router:Router,
                private newPasswordService:NewPasswordService,
                private alert:AlertService)
    {
    }

    public ngOnInit():void
    {
        this.route.params.subscribe((params:any) =>
        {
            this.token = params.token;
        })
    }

    public newPassword():void
    {
        if(this.password === this.repeatPassword)
        {
            this.newPasswordService.newPassword({token: this.token, password:this.password}).subscribe((result:any) =>
            {
                this.alert.success('Ihr Passwort wurde zurückgesetzt!');
                this.router.navigateByUrl('login');
            })
        }
        else
        {
            this.alert.error('Die Passwörter stimmen nicht überein!')
        }
    }
}