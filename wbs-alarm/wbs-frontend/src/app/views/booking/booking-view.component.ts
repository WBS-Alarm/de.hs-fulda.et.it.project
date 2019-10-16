import {
    Component,
    OnInit
} from '@angular/core';
import { TerraSelectBoxValueInterface } from '@plentymarkets/terra-components';
import { CategoryService } from '../../core/service/rest/categories/category.service';
import { GlobalRegistryService } from '../../core/global-registry/global-registry.service';
import { UsersService } from '../../core/service/rest/users/users.service';

@Component({
    selector: 'booking',
    template: require('./booking-view.component.html'),
    styles:   [require('./booking-view.component.scss')]
})
export class BookingViewComponent implements OnInit
{
    public _traeger:string = 'Eschenstruth';
    public _zielorte:Array<TerraSelectBoxValueInterface> = [];
    public _von:TerraSelectBoxValueInterface;
    public _nach:TerraSelectBoxValueInterface;
    public _modus:string ='buchen';

    public _kategorien:Array<TerraSelectBoxValueInterface> = [];
    public _kategorie:TerraSelectBoxValueInterface;
    public _groessen:Array<TerraSelectBoxValueInterface> = []
    public _anzahl:number;

    constructor(private categoryService:CategoryService,
                private userService:UsersService,
                private globalRegistryService:GlobalRegistryService)
    {}

    public ngOnInit():void
    {
        let traegerId:number;

        if(this.globalRegistryService.currentUser)
        {
            traegerId = this.globalRegistryService.currentUser._embedded.traeger[0].id;

            this.categoryService.getCategories(1).subscribe((kategorien:any) =>
            {
                this.addCategoriesToSelectBox(kategorien._embedded.elemente);
            });
        }
        else
        {
            this.userService.getCurrentUsers().subscribe((user:any) =>
            {
                this.globalRegistryService.currentUser = user;
                traegerId = user._embedded.traeger[0].id;


                this.categoryService.getCategories(1).subscribe((kategorien:any) =>
                {
                    this.addCategoriesToSelectBox(kategorien._embedded.elemente);
                });
            })
        }

        this._zielorte = [
            {
                value: 1,
                caption: 'Eschenstruth'
        },
            {
                value: 2,
                caption: 'Helsa'
            }]
    }

    public _buchen():void
    {
        console.log('von:' + this._von);
        console.log('nach:' + this._nach);
    }

    public fuelleGroesse(kategroie:any):void
    {
        //this._groessen =
    }

    private addCategoriesToSelectBox(kategorien:Array<any>):void
    {
        kategorien.forEach((kategorie:any) =>
        {
            this._kategorien.push({
                value: kategorie,
                caption: kategorie.name
            })
        })
    }


}
