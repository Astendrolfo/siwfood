import { Routes } from '@angular/router';
import { LoginComponent } from './authentication/login/login.component';
import {HomeComponent} from "./home/home.component";
import {RegisterComponent} from "./register/register.component";
import {canActivate} from "./services/auth.guard";
import {ProfileComponent} from "./profile/profile.component";
import {ImageViewComponent} from "./imageviewer/imageviewer.component";
import {ImageuploaderComponent} from "./imageuploader/imageuploader.component";
import {RicetteComponent} from "./ricette/ricette.component";
import {NuovaricettaComponent} from "./nuovaricetta/nuovaricetta.component";
import {MyrecipesComponent} from "./myrecipes/myrecipes.component";
import {RicettadetailComponent} from "./ricettadetail/ricettadetail.component";
import {ListacuochiComponent} from "./listacuochi/listacuochi.component";

const routeConfig: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [canActivate]},
  { path: 'register', component: RegisterComponent, canActivate: [canActivate]},
  { path: 'myprofile', component: ProfileComponent, canActivate: [canActivate]},
  { path: 'imagev', component: ImageViewComponent, canActivate: [canActivate]},
  { path: 'imageu', component: ImageuploaderComponent, canActivate: [canActivate]},
  { path: '', component: RicetteComponent, canActivate: [canActivate]},
  { path: 'nuovaricetta', component: NuovaricettaComponent, canActivate: [canActivate]},
  { path: 'register', component: RegisterComponent, canActivate: [canActivate]},
  { path: 'myrecipes', component: MyrecipesComponent, canActivate: [canActivate]},
  { path: 'ricette/:id', component: RicettadetailComponent, canActivate: [canActivate]},
  { path: 'cuochi', component: ListacuochiComponent, canActivate: [canActivate]},
  { path: 'cuochi/:id', component: ProfileComponent, canActivate: [canActivate]},
  { path: '**', redirectTo: '', pathMatch: 'full' }
];

export default routeConfig;
