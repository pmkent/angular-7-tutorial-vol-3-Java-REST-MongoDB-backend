import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { User } from '../model/user.model';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';
import { tap, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  // private usrUrl = 'api/user'; //
  private usrUrl = 'http://localhost:8080/api/user';

  constructor(
    private http: HttpClient,
    private authSvc: AuthService,
    private router: Router
  ) {}

  getHeaders(): { headers: HttpHeaders } {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
      })
    };
    return httpOptions;
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.usrUrl, this.getHeaders())
    .pipe(
      tap(users => this.log(`Fetched all ${users.length} users.`)),
      catchError(this.handleError('getUsers', []))
    );
  }

  deleteUser(user: User | number): Observable<boolean> {
    const id = typeof user === 'number' ? user : user.id;
    const url = `${this.usrUrl}/${id}`;

    return this.http.delete<boolean>(url, this.getHeaders()).pipe(
      tap(_ => console.log(`Deleted user userId=${id}`)),
      catchError(this.handleError<boolean>('deleteUser'))
    );
  }

  getUser(id: string): Observable<User> {
    const url = `${this.usrUrl}/${id}`;
    return this.http.get<User>(url, this.getHeaders()).pipe(
      tap(_ => console.log(`Fetched one user id=${id}`)),
      tap((user: User) => console.log(`Fetched one user w/ username=${user.username}`)),
      catchError(this.handleError<User>(`getUser id=${id}`))
    );
  }

  getUserByUsername(username: string): Observable<User> {
    const url = `${this.usrUrl}/username/${username}`;
    return this.http.get<User>(url, this.getHeaders())
      .pipe(
        tap(_ => this.log(`Fetched user by username=${username}`)),
        catchError(this.handleError<User>(`getUserByUsername username=${username}`))
      );
  }

  updateUser(user: User): Observable<boolean> {
    return this.http.put<boolean>(this.usrUrl, user, this.getHeaders()).pipe(
      tap(_ => console.log(`Updated user userId=${user.userId}`)),
      catchError(this.handleError<boolean>('updateUser'))
    );
  }

  addUser(user: User): Observable<boolean> {
    const url = `${this.usrUrl}/add`;
    return this.http.post<boolean>(url, user, this.getHeaders()).pipe(
      tap((result: boolean) => console.log(`Added user w/ result=${result}`)),
      catchError(this.handleError<boolean>('addUser'))
    );
  }

  /* GET users whose name contains search term */ // TODO Test and use this
  searchUsers(term: string): Observable<User[]> {
    if (!term.trim()) {
      // if not search term, return empty user array.
      return of([]);
    }
    return this.http.get<User[]>(this.usrUrl + `/search?name=${term}`, this.getHeaders()).pipe(
      tap(_ => this.log(`Found user(s) matching "${term}"`)),
      catchError(
        this.handleError<User[]>('searchUsers', [])
      )
    );
  }

  private log(message: string) {
    console.log('usrSvc : ' + message + '');
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      if (error.status === 401) {
        localStorage.removeItem('accessToken');
        console.log('@@@@@ Trapped authentication error');
        this.logUserOut();
      } else if (error.status === 403) {
        console.log('%%%% Trapped duplicate user error');
      } else if (error.status === 500) {
        console.log('#### Error 500 happened. Ooops!');
        this.authSvc.errorMsg = 'Login token expired. Please log in again';
        this.router.navigate(['login']); // .navigateByUrl('/login');
      }

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  logUserOut(): void {
    localStorage.removeItem('accessToken');
    this.authSvc.loggedInUser = null;
    this.log('UsrSvc: logUserOut !!');
    this.authSvc.errorMsg = 'Login token expired. Please log in again';
    this.router.navigate(['login']);
  }
}
