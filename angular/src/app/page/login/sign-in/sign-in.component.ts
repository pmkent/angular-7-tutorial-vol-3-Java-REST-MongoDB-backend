import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';
import { User } from 'src/app/model/user.model';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styles: []
})
export class SignInComponent {

  errorMsg: any;
  loginForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authSvc: AuthService
  ) {
    this.buildLoginForm();
  }

  buildLoginForm() {
    this.loginForm = this.formBuilder.group(
      {
        username: ['', [Validators.required, Validators.minLength(5), Validators.email] ],
        password: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(20) ]]
      }
    );
  }

  onLogin() {
    this.authSvc.login(this.loginForm.controls['username'].value, this.loginForm.controls['password'].value)
      .subscribe(
        (user: User) => {
          this.authSvc.errorMsg = null;
          if (user) {
            this.errorMsg = null;
            console.log('&&& found username >' + user.username + '< >' + JSON.stringify(user));
            this.router.navigateByUrl('/list-user'); // need to reroute to refresh sort, pagination!
          } else {
            this.errorMsg = 'Username and/or password do not match!';
            this.router.navigate(['sign-in']);
          }
        }
      );
  }

  // onLogin() {
  //   const username = this.loginForm.controls['username'].value;
  //   const password = this.loginForm.controls['password'].value;

  //   if ((username === 'userone@gmail.com') && (password === 'password')) {
  //     localStorage.setItem('accessToken', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaGlsbWtpZXRpQGhvdG1haWw' +
  //     'uY29tIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2FwaS9sb2dpbiIsImlhdCI6MTU1NjM3MjAyMiwiZXhwIjox' +
  //     'NTU2MzczODIyfQ.eVdGbsiOheUoWydx3JU-KEf4k1XV4yORSpFweo1j3Es');
  //     this.errorMsg = null;
  //     this.router.navigateByUrl('/list-user');
  //   } else {
  //     this.errorMsg = 'Username and/or password do not match!';
  //     this.router.navigate(['sign-in']);
  //   }
  // }

}
