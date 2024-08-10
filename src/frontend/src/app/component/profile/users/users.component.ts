import { Component } from '@angular/core';
import {BehaviorSubject, catchError, map, Observable, of, startWith} from "rxjs";
import {State} from "../../../interface/state";
import {CustomHttpResponse, Page} from "../../../interface/appstates";
import {Customer} from "../../../interface/customer";
import {User} from "../../../interface/user";
import {Router} from "@angular/router";
import {CustomerService} from "../../../service/customer.service";
import {NgForm} from "@angular/forms";
import {DataState} from "../../../enum/datastate.enum";
import {UserService} from "../../../service/user.service";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent {
    usersState$: Observable<State<CustomHttpResponse<Page<User> & User>>>;
    private dataSubject = new BehaviorSubject<CustomHttpResponse<Page<User> & User>>(null);
    private isLoadingSubject = new BehaviorSubject<boolean>(false);
    isLoading$ = this.isLoadingSubject.asObservable();
    private currentPageSubject = new BehaviorSubject<number>(0);
    currentPage$ = this.currentPageSubject.asObservable();
    private showLogsSubject = new BehaviorSubject<boolean>(false);
    showLogs$ = this.showLogsSubject.asObservable();
    readonly DataState = DataState;

    constructor(private router: Router, private userService: UserService) { }

    ngOnInit(): void {
        this.usersState$ = this.userService.searchUsers$()
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

    searchUsers(searchForm: NgForm): void {
        this.currentPageSubject.next(0);
        this.usersState$ = this.userService.searchUsers$(searchForm.value.name)
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
        this.usersState$ = this.userService.searchUsers$(name, pageNumber)
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
    selectUser(user: User): void {
        this.router.navigate([`/user/${user.id}`]);
    }
}
