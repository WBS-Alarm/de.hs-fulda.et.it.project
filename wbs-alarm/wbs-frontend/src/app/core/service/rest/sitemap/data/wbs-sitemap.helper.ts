export class WbsSitemapHelper
{
    private siteMaps:any;
    private bearer:string;

    constructor()
    {
    }

    public set Bearer(value:string)
    {
        this.bearer = value;
    }

    public get Bearer():string
    {
        return this.bearer;
    }

    public set sitemaps(value:any)
    {
        this.siteMaps = value;
    }

    public get sitemaps():any
    {
        return this.siteMaps;
    }

    public getLogin():any
    {
        return Object.values(this.siteMaps)[0]['userLogin'][0].href;
    }
}
