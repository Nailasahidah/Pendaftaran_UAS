
import {NgModule} from "@angular/core";
import {AuthenticationGuard} from "../../guard/authentication.guard";
import {ProductsComponent} from "./products/products.component";
import {RouterModule, Routes} from "@angular/router";
import {NewproductComponent} from "./newproduct/newproduct.component";
import {ProductdetailComponent} from "./productdetail/productdetail.component";

const customerRoutes: Routes = [
  { path: 'products', component: ProductsComponent, canActivate: [AuthenticationGuard] },
  { path: 'products/new', component: NewproductComponent, canActivate: [AuthenticationGuard] },
  { path: 'product/:id', component: ProductdetailComponent, canActivate: [AuthenticationGuard] },
]
@NgModule({
  imports: [RouterModule.forChild(customerRoutes)],
  exports: [RouterModule]
})
export class ProductRoutingModule { }
