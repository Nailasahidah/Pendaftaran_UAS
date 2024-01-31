import {NgModule} from "@angular/core";
import {UserComponent} from "./user/user.component";
import {SharedModule} from "../../shared/shared.module";
import {UserRoutingModules} from "./user-routing.modules";
import {NavbarModule} from "../navbar/navbar.module";
import {UsersComponent} from "./users/users.component";
import {NewuserComponent} from "./newuser/newuser.component";

@NgModule({
  declarations: [
    UserComponent,
    UsersComponent,
    NewuserComponent
  ],
  imports: [ SharedModule, UserRoutingModules, NavbarModule]
})
export class UserModule { }
