import { Routes } from '@angular/router';
import { LoginComponent } from './authentication/login/login.component';
import {HomeComponent} from "./home/home.component";
import {RegisterComponent} from "./authentication/register/register.component";
import {canActivate} from "./services/auth.guard";
import {ProfileComponent} from "./profile/profile.component";
import {ImageViewComponent} from "./imageviewer/imageviewer.component";
import {ImageuploaderComponent} from "./imageuploader/imageuploader.component";
import {RicetteComponent} from "./ricette/ricette.component";
import {NuovaricettaComponent} from "./nuovaricetta/nuovaricetta.component";

const routeConfig: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [canActivate]},
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, canActivate: [canActivate]},
  { path: 'register', component: RegisterComponent, canActivate: [canActivate]},
  { path: 'profile', component: ProfileComponent, canActivate: [canActivate]},
  { path: 'imagev', component: ImageViewComponent, canActivate: [canActivate]},
  { path: 'imageu', component: ImageuploaderComponent, canActivate: [canActivate]},
  { path: 'ricette', component: RicetteComponent, canActivate: [canActivate]},
  { path: 'ricette/nuovaricetta', component: NuovaricettaComponent, canActivate: [canActivate]},
];

export default routeConfig;
