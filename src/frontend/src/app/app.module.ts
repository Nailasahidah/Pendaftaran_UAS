import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './component/auth/login/login.component';
import { RegisterComponent } from './component/auth/register/register.component';
import { VerifyComponent } from './component/auth/verify/verify.component';
import { ResetpasswordComponent } from './component/auth/resetpassword/resetpassword.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import { UserComponent } from './component/profile/user/user.component';
import { HomeComponent } from './component/home/home/home.component';
import { CustomersComponent } from './component/customer/customers/customers.component';
import { StatsComponent } from './component/stats/stats.component';
import { NavbarComponent } from './component/navbar/navbar.component';
import {TokenInterceptor} from "./interceptor/token.interceptor";
import { InvoicesComponent } from './component/invoice/invoices/invoices.component';
import { NewinvoiceComponent } from './component/invoice/newinvoice/newinvoice.component';
import { NewcustomerComponent } from './component/customer/newcustomer/newcustomer.component';
import { CustomerDetailComponent } from './component/customer/customer-detail/customer-detail.component';
import { InvoiceDetailComponent } from './component/invoice/invoice-detail/invoice-detail.component';
import {ExtractArrayValue} from './pipes/extractvalue.pipe';
import {CacheInterceptor} from "./interceptor/cache.interceptor";
import {CoreModule} from "./core/core.module";
import {AuthRoutingModule} from "./component/auth/auth-routing.module";
import {AuthModule} from "./component/auth/auth.module";
import {CustomerModule} from "./component/customer/customer.module";
import {InvoiceModule} from "./component/invoice/invoice.module";
import {HomeModule} from "./component/home/home.module";
import {NotificationModule} from "./notification.module";
import { ProductdetailComponent } from './component/product/productdetail/productdetail.component';
import { ProductsComponent } from './component/product/products/products.component';
import { NewproductComponent } from './component/product/newproduct/newproduct.component';
import {ProductModule} from "./component/product/product.module";
import { UsersComponent } from './component/profile/users/users.component';
import { NewuserComponent } from './component/profile/newuser/newuser.component';
// import {TokenInterceptor} from "./interceptor/token.interceptor";

@NgModule({
  declarations: [ AppComponent ],
  imports: [
    BrowserModule,
    CoreModule,
    AuthModule,
    CustomerModule,
    ProductModule,
    InvoiceModule,
    HomeModule,
    AppRoutingModule,
    NotificationModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
