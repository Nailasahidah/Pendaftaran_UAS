import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpEvent} from "@angular/common/http";
import {catchError, Observable, tap, throwError} from "rxjs";
import {CustomerState, CustomHttpResponse, Page, ProductState} from "../interface/appstates";
import {User} from "../interface/user";
import {Stats} from "../interface/stats";
import {Product} from "../interface/product";
import {Customer} from "../interface/customer";
import {Invoice} from "../interface/invoice";

@Injectable({providedIn: 'root'})
export class ProductService {
  private readonly server: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  products$ = (page: number = 0) => <Observable<CustomHttpResponse<Page<Product> & User & Stats>>>
    this.http.get<CustomHttpResponse<Page<Product> & User & Stats>>
    (`${this.server}/product/list?page=${page}`)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );
  product$ = (productId: number) => <Observable<CustomHttpResponse<ProductState>>>
    this.http.get<CustomHttpResponse<ProductState>>
    (`${this.server}/product/get/${productId}`)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );
  update$ = (product: Product) => <Observable<CustomHttpResponse<ProductState>>>
    this.http.put<CustomHttpResponse<ProductState>>
    (`${this.server}/product/update`, product)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );

  newProducts$= (product: Product) => <Observable<CustomHttpResponse<Product & User>>>
    this.http.post<CustomHttpResponse<Product & User>>
    (`${this.server}/product/create`, product)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );

  searchProducts$ = (name: string = '', page: number = 0) => <Observable<CustomHttpResponse<Page<Product> & User>>>
    this.http.get<CustomHttpResponse<Page<Product> & User>>
    (`${this.server}/product/search?name=${name}&page=${page}`)
      .pipe(
        tap(console.log),
        catchError(this.handleError)
      );

  newInvoice$ = () => <Observable<CustomHttpResponse<Product[] & User>>>
      this.http.get<CustomHttpResponse<Product[] & User>>
      (`${this.server}/product/invoice/new`)
          .pipe(
              tap(console.log),
              catchError(this.handleError)
          );

  createInvoice$ = (productId: number, invoice: Invoice) => <Observable<CustomHttpResponse<Product[] & User>>>
      this.http.post<CustomHttpResponse<Product[] & User>>
      (`${this.server}/product/invoice/addtoproduct/${productId}`, invoice)
          .pipe(
              tap(console.log),
              catchError(this.handleError)
          );

  invoices$ = (page: number = 0) => <Observable<CustomHttpResponse<Page<Invoice> & User>>>
      this.http.get<CustomHttpResponse<Page<Invoice> & User>>
      (`${this.server}/product/invoice/list?page=${page}`)
          .pipe(
              tap(console.log),
              catchError(this.handleError)
          );

  invoice$ = (invoiceId: number) => <Observable<CustomHttpResponse<Product & Invoice & User>>>
      this.http.get<CustomHttpResponse<Product & Invoice & User>>
      (`${this.server}/product/invoice/get/${invoiceId}`)
          .pipe(
              tap(console.log),
              catchError(this.handleError)
          );

  downloadReport$ = () => <Observable<HttpEvent<Blob>>>
      this.http.get(`${this.server}/product/download/report`,
          { reportProgress: true, observe: 'events', responseType: 'blob' })
          .pipe(
              tap(console.log),
              catchError(this.handleError)
          );

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.log(error);
    let errorMessage: string;
    if (error.error instanceof ErrorEvent) {
      errorMessage = `A client error occurred - ${error.error.message}`;
    } else {
      if (error.error.reason) {
        errorMessage = error.error.reason;
        console.log(errorMessage);
      } else {
        errorMessage = `An error occurred - Error status ${error.status}`;
      }
    }
    return throwError(() => errorMessage);
  }
}
