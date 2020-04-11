import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppWbsKleiderkammerModule } from './app';
import {environment} from "./environments/environment";


if(environment.production) {
  enableProdMode();
}

platformBrowserDynamic().bootstrapModule(AppWbsKleiderkammerModule);

