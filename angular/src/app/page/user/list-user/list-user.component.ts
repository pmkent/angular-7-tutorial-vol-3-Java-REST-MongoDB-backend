import { Component, AfterViewInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { User } from 'src/app/model/user.model';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styles: []
})
export class ListUserComponent implements AfterViewInit {

  dataSource: MatTableDataSource<User>;
  displayedColumns = ['userId', 'firstName', 'middleName', 'lastName', 'username', 'dateOfBirth', 'edit', 'delete' ];

  @ViewChild('listUserPaginator') paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  // MatPaginator Output
  pageEvent: PageEvent;

  errorMsg: any;

  length = 10;
  pageSize = 5;
  pageSizeOptions: number[] = [3, 5, 8];

  users: User[] = [];

  setPageSizeOtions(setPageSizeOtions: string) {
    this.pageSizeOptions = setPageSizeOtions.split(',').map(str => +str);
  }

  constructor(
    private router: Router,
    private userSvc: UserService
  ) {
    this.populateUserTable();
  }

  private populateUserTable() {
    this.dataSource = new MatTableDataSource(this.users);

    this.userSvc.getUsers()
    .subscribe(
      users => {
        this.dataSource.data = users;
        if (users) {
          this.users = users;
          this.errorMsg = null;
        } else {
          this.errorMsg = 'Unable get users ' + users + ' list. Going to the login page.';
          this.router.navigate(['sign-in']);
        }
      }
    );
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  editUser(user: User): void {
    console.log('editUser method called for user id ' + user.id);
  }

  deleteUser(user: User): void {
    console.log('deleteUser method called for user id ' + user.id);
  }

  logout() {
    localStorage.removeItem('accessToken');
    this.errorMsg = 'Logged out. Please log in again.';
    this.router.navigate(['sign-in']);
  }

}
