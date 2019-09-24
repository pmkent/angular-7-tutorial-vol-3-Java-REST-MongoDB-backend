import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SignInComponent } from './page/login/sign-in/sign-in.component';
import { ListUserComponent } from './page/user/list-user/list-user.component';
import { AuthGuard } from './util/auth.guard';

const routes: Routes = [
  { path: 'sign-in', component: SignInComponent },
  { path: '', redirectTo: 'list-user', pathMatch: 'full', canActivate: [AuthGuard] },
  { path: 'list-user', component: ListUserComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
