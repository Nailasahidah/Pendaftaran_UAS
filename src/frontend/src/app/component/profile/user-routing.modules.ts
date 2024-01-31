import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {UserComponent} from "./user/user.component";
import {AuthenticationGuard} from "../../guard/authentication.guard";
import {NewuserComponent} from "./newuser/newuser.component";
import {UsersComponent} from "./users/users.component";

const userRoutes: Routes = [
  { path: '', children: [{ path: '', component: UserComponent, canActivate: [AuthenticationGuard] } ]},
  { path: 'users', component: UsersComponent, canActivate: [AuthenticationGuard] },
  { path: 'users/new', component: NewuserComponent, canActivate: [AuthenticationGuard] },
  { path: 'user/:id', component: UserComponent, canActivate: [AuthenticationGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(userRoutes)],
  exports: [RouterModule]
})
export class UserRoutingModules { }
