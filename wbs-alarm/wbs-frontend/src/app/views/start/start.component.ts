import {
    Component,
    Input,
    OnInit
} from '@angular/core';
import { Language } from 'angular-l10n';

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

    constructor()
    {
    }

    public ngOnInit():void
    {
    }

    public redirectToSomethingWentWrong():void
    {
        window.open('https://www.youtube.com/watch?v=t3otBjVZzT0', '_blank');
    }
}
