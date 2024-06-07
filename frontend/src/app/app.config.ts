import {ApplicationConfig} from '@angular/core';
import { provideRouter } from '@angular/router';
import routeConfig from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {provideHttpClient, withInterceptors} from "@angular/common/http";
import {customInterceptor} from "./custom.interceptor";


export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routeConfig), provideAnimationsAsync(), provideHttpClient(withInterceptors([customInterceptor])),]
};
