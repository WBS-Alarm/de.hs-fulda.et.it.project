import {Component} from "@angular/core";
import { ActivatedRoute } from '@angular/router';
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

    constructor(private route:ActivatedRoute,
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
        this.newPasswordService.newPassword({token: this.token, password:this.password}).subscribe((result:any) =>
        {
            this.alert.success('Ihr Passwort wurde zurückgesetzt!');
        })
    }
}