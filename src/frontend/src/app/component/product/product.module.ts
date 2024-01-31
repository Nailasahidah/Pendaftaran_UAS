import {NgModule} from "@angular/core";
import {ProductsComponent} from "./products/products.component";
import {NewproductComponent} from "./newproduct/newproduct.component";
import {ProductdetailComponent} from "./productdetail/productdetail.component";
import {SharedModule} from "../../shared/shared.module";
import {ProductRoutingModule} from "./product-routing.module";
import {NavbarModule} from "../navbar/navbar.module";

@NgModule({
  declarations: [
    ProductsComponent,
    NewproductComponent,
    ProductdetailComponent
  ],
  imports: [
    SharedModule,
    ProductRoutingModule,
    NavbarModule
  ]
})
export class ProductModule { }
