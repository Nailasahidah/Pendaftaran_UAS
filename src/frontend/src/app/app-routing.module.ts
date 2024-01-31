import { NgModule } from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./component/auth/login/login.component";
import {RegisterComponent} from "./component/auth/register/register.component";
import {ResetpasswordComponent} from "./component/auth/resetpassword/resetpassword.component";
import {VerifyComponent} from "./component/auth/verify/verify.component";
import {CustomersComponent} from "./component/customer/customers/customers.component";
import {UserComponent} from "./component/profile/user/user.component";
import {HomeComponent} from "./component/home/home/home.component";
import {AuthenticationGuard} from "./guard/authentication.guard";
import {NewcustomerComponent} from "./component/customer/newcustomer/newcustomer.component";
import {NewinvoiceComponent} from "./component/invoice/newinvoice/newinvoice.component";
import {InvoicesComponent} from "./component/invoice/invoices/invoices.component";
import {CustomerDetailComponent} from "./component/customer/customer-detail/customer-detail.component";
import {InvoiceDetailComponent} from "./component/invoice/invoice-detail/invoice-detail.component";

const routes: Routes = [
  { path: 'profile', loadChildren: () => import('./component/profile/user.module').then(module => module.UserModule) },
  { path: ' ', redirectTo: '/', pathMatch: 'full'},
  { path: '**', component: HomeComponent, canActivate: [AuthenticationGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
