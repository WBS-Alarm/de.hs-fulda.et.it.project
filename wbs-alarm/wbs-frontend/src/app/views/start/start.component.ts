import {
    Component,
    Input,
    OnInit
} from '@angular/core';
import { Language } from 'angular-l10n';
import { LoginService } from '../../core/service/rest/login/login.service';

@Component({
    selector: 'start',
    template: require('./start.component.html'),
    styles:   [require('./start.component.scss')],
})
export class StartComponent implements OnInit
{
    @Language()
    public lang:string;

    @Input()
    public myTitle:string;

    constructor(private loginService:LoginService)
    {
    }

    public ngOnInit():void
    {
    }

    public redirectToSomethingWentWrong():void
    {
        //window.open('https://www.youtube.com/watch?v=t3otBjVZzT0', '_blank');
        this.loginService.logout().subscribe(
            (result) =>
            {
                console.log('Logout erfolgreich!');
            },
            (error:Error) =>
            {
                console.log('Beim Logout ist ein Fehler aufgetreten');
            }
        )
    }
}
