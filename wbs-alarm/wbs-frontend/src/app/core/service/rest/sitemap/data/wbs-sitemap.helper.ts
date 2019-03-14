export class WbsSitemapHelper
{
    private siteMaps:any;

    constructor()
    {
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
