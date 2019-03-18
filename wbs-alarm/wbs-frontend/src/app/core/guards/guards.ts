import { IsLoggedInGuard } from './isLoggedInGuard';
import { Injectable } from '@angular/core';


export const GUARDS:Array<Injectable> =
    [
        IsLoggedInGuard
    ];