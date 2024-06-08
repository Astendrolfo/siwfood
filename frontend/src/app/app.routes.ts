import { Routes } from '@angular/router';
import { LoginComponent } from './authentication/login/login.component';
import {HomeComponent} from "./home/home.component";
import {RegisterComponent} from "./authentication/register/register.component";
import {canActivate} from "./services/auth.guard";
import {ProfileComponent} from "./profile/profile.component";

const routeConfig: Routes = [
  { path: 'login', component: LoginComponent},
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'profile', component: ProfileComponent}
];

export default routeConfig;
