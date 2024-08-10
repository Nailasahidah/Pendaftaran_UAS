import {Component, OnInit} from '@angular/core';
import {BehaviorSubject, catchError, map, Observable, of, startWith, switchMap} from "rxjs";
import {State} from "../../../interface/state";
import {CustomerState, CustomHttpResponse, ProductState} from "../../../interface/appstates";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {CustomerService} from "../../../service/customer.service";
import {DataState} from "../../../enum/datastate.enum";
import {ProductService} from "../../../service/product.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-productdetail',
  templateUrl: './productdetail.component.html',
  styleUrls: ['./productdetail.component.css']
})
export class ProductdetailComponent implements OnInit{
  productState$: Observable<State<CustomHttpResponse<ProductState>>>;
  readonly DataState = DataState;
  private dataSubject = new BehaviorSubject<CustomHttpResponse<ProductState>>(null);
  private isLoadingSubject = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoadingSubject.asObservable();
  private readonly PRODUCT_ID: string = 'id';

  constructor(private activatedRoute: ActivatedRoute, private productService: ProductService) {
  }

  ngOnInit(): void {
    this.productState$ = this.activatedRoute.paramMap.pipe(
      switchMap((params: ParamMap) => {
        return this.productService.product$(+params.get(this.PRODUCT_ID))
          .pipe(
            map(response => {
              console.log(response);
              this.dataSubject.next(response);
              return {dataState: DataState.LOADED, appData: response};
            }),
            startWith({dataState: DataState.LOADING}),
            catchError((error: string) => {
              return of({dataState: DataState.ERROR, error})
            })
          )
      })
    );
  }
  updateProduct(productForm: NgForm): void {
    this.isLoadingSubject.next(true);
    this.productState$ = this.productService.update$(productForm.value)
      .pipe(
        map(response => {
          console.log(response);
          this.dataSubject.next({
            ...response,
            data: {
              ...response.data,
              product: {
                ...response.data.product,
                invoices: this.dataSubject.value.data.product.invoices
              }
            }
          });

          this.isLoadingSubject.next(false);
          return {dataState: DataState.LOADED, appData: this.dataSubject.value};
        }),
        startWith({dataState: DataState.LOADED, appData: this.dataSubject.value}),
        catchError((error: string) => {
          this.isLoadingSubject.next(false);
          return of({dataState: DataState.ERROR, error})
        })
      )
  }

  // updatePicture(image: File): void {
  //
  //   if (image) {
  //     this.isLoadingSubject.next(true);
  //     this.productState$ = this.productService.updateImage$(this.getFormData(image))
  //       .pipe(
  //         map(response => {
  //           console.log(response);
  //           this.isLoadingSubject.next(false);
  //           return {dataState: DataState.LOADED, appData: this.dataSubject.value};
  //         }),
  //         startWith({dataState: DataState.LOADED, appData: this.dataSubject.value}),
  //         catchError((error: string) => {
  //           this.isLoadingSubject.next(false);
  //           return of({dataState: DataState.LOADED, appData: this.dataSubject.value, error})
  //         })
  //       )
  //   }
  // }
  // private getFormData(image: File): FormData {
  //   const formData = new FormData();
  //   formData.append('image', image);
  //   return formData;
  // }
}
