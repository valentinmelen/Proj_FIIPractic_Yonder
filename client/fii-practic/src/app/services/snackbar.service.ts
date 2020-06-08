import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material';

@Injectable()
export class SnackBarService {

    constructor(private snackBar: MatSnackBar) {}

    showSnackBar(msg: string) {
        this.snackBar.open(msg, 'Ok', {
            duration: 3000
        });
    }
}