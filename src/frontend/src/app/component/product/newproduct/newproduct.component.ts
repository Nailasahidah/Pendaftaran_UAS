import {Component, OnInit} from '@angular/core';
import {BehaviorSubject, catchError, map, Observable, of, startWith} from "rxjs";
import {State} from "../../../interface/state";
import {CustomHttpResponse, Page} from "../../../interface/appstates";
import {User} from "../../../interface/user";
import {DataState} from "../../../enum/datastate.enum";
import {Product} from "../../../interface/product";
import {ProductService} from "../../../service/product.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-newproduct',
  templateUrl: './newproduct.component.html',
  styleUrls: ['./newproduct.component.css']
})
export class NewproductComponent implements OnInit{
  newProductState$: Observable<State<CustomHttpResponse<Page<Product> & User>>>;
  private dataSubject = new BehaviorSubject<CustomHttpResponse<Page<Product> & User>>(null);
  private isLoadingSubject = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoadingSubject.asObservable();
  readonly DataState = DataState;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.newProductState$ = this.productService.products$()
      .pipe(
        map(response => {
          console.log(response);
          this.dataSubject.next(response);
          return { dataState: DataState.LOADED, appData: response };
        }),
        startWith({ dataState: DataState.LOADING }),
        catchError((error: string) => {
          return of({ dataState: DataState.ERROR, error })
        })
      )
  }

  createProduct(newProductForm: NgForm): void {
    this.isLoadingSubject.next(true);
    this.newProductState$ = this.productService.newProducts$(newProductForm.value)
      .pipe(
        map(response => {
          console.log(response);
          this.isLoadingSubject.next(false);
          return { dataState: DataState.LOADED, appData: this.dataSubject.value };
        }),
        startWith({ dataState: DataState.LOADED, appData: this.dataSubject.value }),
        catchError((error: string) => {
          this.isLoadingSubject.next(false);
          return of({ dataState: DataState.LOADED, error })
        })
      )
  }
  // updatePicture(image: File): void {
  //
  //   if (image) {
  //     this.isLoadingSubject.next(true);
  //     this.newProductState$ = this.productService.updateImage$(this.getFormData(image))
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
