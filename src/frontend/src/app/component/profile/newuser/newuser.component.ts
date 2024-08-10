import {Component, OnInit} from '@angular/core';
import {BehaviorSubject, catchError, map, Observable, of, startWith} from "rxjs";
import {State} from "../../../interface/state";
import {CustomHttpResponse, Page} from "../../../interface/appstates";
import {User} from "../../../interface/user";
import {DataState} from "../../../enum/datastate.enum";
import {UserService} from "../../../service/user.service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-newuser',
  templateUrl: './newuser.component.html',
  styleUrls: ['./newuser.component.css']
})
export class NewuserComponent implements OnInit{
  newUserState$: Observable<State<CustomHttpResponse<Page<User> & User>>>;
  private dataSubject = new BehaviorSubject<CustomHttpResponse<Page<User> & User>>(null);
  private isLoadingSubject = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoadingSubject.asObservable();
  readonly DataState = DataState;

  constructor(private userService: UserService) { }

    ngOnInit(): void {
        this.newUserState$ = this.userService.profiles$()
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
    createUser(newUserForm: NgForm): void {
        this.isLoadingSubject.next(true);
        this.newUserState$ = this.userService.newUsers$(newUserForm.value)
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
}
