import { NgModule } from '@angular/core';
import * as mat from '@angular/material';

@NgModule({
  declarations: [],
  imports: [
    mat.MatToolbarModule,
    mat.MatCardModule,
    mat.MatButtonModule,
    mat.MatIconModule,
    mat.MatDividerModule,
    mat.MatGridListModule,
    mat.MatFormFieldModule,
    mat.MatInputModule,

    mat.MatBadgeModule,
    mat.MatTableModule,
    mat.MatPaginatorModule,
    // mat.MatSnackBarModule
  ],
  exports: [
    mat.MatToolbarModule,
    mat.MatCardModule,
    mat.MatButtonModule,
    mat.MatIconModule,
    mat.MatDividerModule,
    mat.MatGridListModule,
    mat.MatFormFieldModule,
    mat.MatInputModule,

    mat.MatBadgeModule,
    mat.MatTableModule,
    mat.MatPaginatorModule,
    // mat.MatSnackBarModule
  ]
})
export class AppMaterialModule { }
