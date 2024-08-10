import {Component, OnInit} from '@angular/core';
import {BehaviorSubject, catchError, map, Observable, of, startWith} from "rxjs";
import {State} from "../../../interface/state";
import {CustomHttpResponse, Page} from "../../../interface/appstates";
import {User} from "../../../interface/user";
import {Router} from "@angular/router";
import {Product} from "../../../interface/product";
import {ProductService} from "../../../service/product.service";
import {DataState} from "../../../enum/datastate.enum";
import {NgForm} from "@angular/forms";
import {HttpEvent, HttpEventType} from "@angular/common/http";
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements  OnInit{
  productsState$: Observable<State<CustomHttpResponse<Page<Product> & User>>>;
  private dataSubject = new BehaviorSubject<CustomHttpResponse<Page<Product> & User>>(null);
  private isLoadingSubject = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoadingSubject.asObservable();
  private currentPageSubject = new BehaviorSubject<number>(0);
  currentPage$ = this.currentPageSubject.asObservable();
  private showLogsSubject = new BehaviorSubject<boolean>(false);
  showLogs$ = this.showLogsSubject.asObservable();
    private fileStatusSubject = new BehaviorSubject<{ status: string, type: string, percent: number }>(undefined);
    fileStatus$ = this.fileStatusSubject.asObservable();
  readonly DataState = DataState;

  constructor(private router: Router, private productService: ProductService) { }
  ngOnInit(): void {
    this.productsState$ = this.productService.searchProducts$()
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
    report(): void {
        this.productsState$ = this.productService.downloadReport$()
            .pipe(
                map(response => {
                    console.log(response);
                    this.reportProgress(response);
                    return { dataState: DataState.LOADED, appData: this.dataSubject.value };
                }),
                startWith({ dataState: DataState.LOADED, appData: this.dataSubject.value }),
                catchError((error: string) => {
                    return of({ dataState: DataState.LOADED, error, appData: this.dataSubject.value })
                })
            )
    }
    private reportProgress(httpEvent: HttpEvent<string[] | Blob>): void {
        switch (httpEvent.type) {
            case HttpEventType.DownloadProgress || HttpEventType.UploadProgress:
                this.fileStatusSubject.next({ status: 'progress', type: 'Downloading...', percent: Math.round(100 * httpEvent.loaded / httpEvent.total) });
                break;
            case HttpEventType.ResponseHeader:
                console.log('Got response Headers', httpEvent);
                break;
            case HttpEventType.Response:
                saveAs(new File([<Blob>httpEvent.body], httpEvent.headers.get('File-Name'),
                    { type: `${httpEvent.headers.get('Content-Type')};charset-utf-8` }));
                this.fileStatusSubject.next(undefined);
                break;
            default:
                console.log(httpEvent);
                break;
        }
    }

  searchProducts(searchForm: NgForm): void {
    this.currentPageSubject.next(0);
    this.productsState$ = this.productService.searchProducts$(searchForm.value.name)
      .pipe(
        map(response => {
          console.log(response);
          this.dataSubject.next(response);
          return { dataState: DataState.LOADED, appData: response };
        }),
        startWith({ dataState: DataState.LOADED, appData: this.dataSubject.value }),
        catchError((error: string) => {
          return of({ dataState: DataState.ERROR, error })
        })
      )
  }


  goToPage(pageNumber?: number, name?: string): void {
    this.productsState$ = this.productService.searchProducts$(name, pageNumber)
      .pipe(
        map(response => {
          console.log(response);
          this.dataSubject.next(response);
          this.currentPageSubject.next(pageNumber);
          return { dataState: DataState.LOADED, appData: response };
        }),
        startWith({ dataState: DataState.LOADED, appData: this.dataSubject.value }),
        catchError((error: string) => {
          return of({ dataState: DataState.LOADED, error, appData: this.dataSubject.value })
        })
      )
  }

  goToNextOrPreviousPage(direction?: string, name?: string): void {
    this.goToPage(direction === 'forward' ? this.currentPageSubject.value + 1 : this.currentPageSubject.value - 1, name);
  }
  selectProduct(product: Product): void {
    this.router.navigate([`/product/${product.id}`]);
  }
}
