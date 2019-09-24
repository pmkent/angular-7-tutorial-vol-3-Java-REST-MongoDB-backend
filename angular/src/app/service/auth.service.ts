import { Injectable } from '@angular/core';
import { User } from '../model/user.model';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  errorMsg: any;
  // private loginUrl = 'api/login'; //
  private authUrl = 'http://localhost:8080/api/login';
  private _loggedInUser?: User;

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  get loggedInUser(): User {
    return this._loggedInUser;
  }

  set loggedInUser(user: User) {
    this._loggedInUser = user;
  }

  httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
    })
};

  public login(username: string, password: string) {
    return this.http.post<User>(this.authUrl, JSON.stringify({username: username, password: password}), this.httpOptions)
      .pipe (
        tap (
          (user: User) => {
            if (user) {
              localStorage.setItem('accessToken', user.token);
              console.log(`Logged in username=${username} with password=${password}` + ' successfully!');
              this.loggedInUser = user;
            } else {
              this.loggedInUser = null;
              console.log(`Login FAILED for username=${username} with password=${password}`);
            }
          }
        )
      );
  }

  logout(origin: string, error: string) {
    localStorage.removeItem('accessToken');
    if (error) {
      this.errorMsg = error;
    }
    this.loggedInUser = null;
    this.router.navigate(['/']);
    console.log('AuthSvc: Logout from ' + origin);
  }
}
