import { Component, HostListener } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { LocationStrategy } from '@angular/common';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';

import { DecoratorService } from '../header/decorator.service';

import { UserComponent } from '../user/user.component';
import { User } from '../Interfaces/User/user.component';

import { ListsService } from '../lists/lists.service';
import { UserService } from '../user/user.service';
import { ImageService } from '../uploadimage/image.service'
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

class modifyUser {
  email?: string;
  password?: string;
  image?: string;
}

@Component({
  selector: 'profile',
  templateUrl: './profile.html',
  styleUrls: ['./profile.css']
})

export class ProfileComponent {

  private isPopState = false;
  private show = "ACOUNT";
  private user = this.userComponent.user;
  private file = null;
  private lists = [];
  private printList = [];
  private pdf = false;
  private profileValidation: FormGroup;
  private showAddList = false;
  private isProfileEdited = false;
  private errorProfileEdited = false;
  private newListValidation: FormGroup;
  private urlPDF: SafeResourceUrl;
  private errorCreateList = false;
  private isCollapsed: boolean = true;

  constructor(private decorator: DecoratorService, private userComponent: UserComponent, private serviceList: ListsService, private userService: UserService, private fb: FormBuilder, private imageService: ImageService, private router: Router, private locStrat: LocationStrategy, private sanitized: DomSanitizer) {
    this.decorator.activeButton("profile");
    this.newValidator();
    this.validateNewList();
  }

  ngOnInit() {
    this.loadUrlPdf();
    this.loadLists();

    this.locStrat.onPopState(() => {
      this.isPopState = true;
    });

    this.router.events.subscribe(event => {
      // Scroll to top if accessing a page, not via browser history stack
      if (event instanceof NavigationEnd && !this.isPopState) {
        window.scrollTo(0, 0);
        this.isPopState = false;
      }

      // Ensures that isPopState is reset
      if (event instanceof NavigationEnd) {
        this.isPopState = false;
      }
    });
  }

  private fileChange(inputFile) {
    if (inputFile.files.length > 0) {
      this.file = inputFile.files[0];
    }
  }

  private changePdf(change: boolean) {
    this.pdf = change;
  }

  private changeShowAddList() {
    if (this.showAddList) {
      this.showAddList = false;
    } else {
      this.showAddList = true;
    }
  }

  private loadLists() {
    this.serviceList.getContentList().subscribe(
      lists => {
        this.lists = lists
      }
    );
  }

  private createList(listName: string) {
    this.validateNewList();
    this.serviceList.createList(listName).subscribe(
     response => {
        if (response.status === 226) {
          this.errorCreateList = true;
          setTimeout(() => this.closeErrorCreateListAlert(), 20000);
        } 
        this.loadLists();
        this.userComponent.reqLists();          
      }    
    );
  }

  private closeErrorCreateListAlert(){
    this.errorCreateList = false;
  }

  private editUser(email: string, pass: string) {
    let editUser = new modifyUser();

    if (email !== "") {
      editUser.email = email;
    }

    if (pass !== "") {
      editUser.password = pass;
    }

    if (this.file !== null) {
      this.imageService.uploadImage(this.file).subscribe(
        image => {
          this.file = null;
          editUser.image = image.text();
          this.sendEditUser(editUser);
        }
      )
    } else {
      this.sendEditUser(editUser);
    }
  }

  private sendEditUser(editUser: User) {
    this.userService.editUser(this.user.name, editUser).subscribe(
      user => {
        this.user = user
        this.userComponent.user = user;
        this.isProfileEdited = true;
        setTimeout(() => this.closeEditedProfileAlert(), 20000);
      }, error => {
        this.errorProfileEdited = true;
        setTimeout(() => this.closeErrorEditedProfileAlert(), 20000);
      }
    )
  }

  private closeEditedProfileAlert() {
    this.isProfileEdited = false;
  }

  private closeErrorEditedProfileAlert() {
    this.errorProfileEdited = false;
  }

  private matchPassword(AC: AbstractControl) {
    let password = AC.get('password').value; // to get value in input tag
    let confirmPassword = AC.get('passwordConfirm').value; // to get value in input tag
    if (password != confirmPassword) {
      AC.get('passwordConfirm').setErrors({ MatchPassword: true })
    } else {
      return null
    }
  }

  private newValidator() {
    this.profileValidation = this.fb.group({
      'email': [this.user.email, Validators.pattern('^[a-z0-9]+(\.[_a-z0-9]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,15})$')],
      'password': [""],
      'passwordConfirm': [""],
    }, {
        validator: this.matchPassword
      });
  }

  private changePass(passOne: String, passTwo: string) {
    if (passOne === "" && passTwo === "") {
      this.newValidator();
    }
  }

  private deleteList(listName: string) {
    this.serviceList.deleteList(listName).subscribe(
      lists => {
        this.loadLists();
        this.userComponent.reqLists();
        this.loadUrlPdf();
      }
    )
  }

  private deleteElem(listName: string, typeElem: string, itemName: string) {
    this.serviceList.deleteElement(listName, typeElem, itemName).subscribe(
      lists => {
        this.loadLists();
        this.loadUrlPdf();
      }
    )
  }

  private validateNewList() {
    this.newListValidation = this.fb.group({
      "name": [null, Validators.required]
    });
  }

  private loadUrlPdf(){
    this.urlPDF = this.sanitized.bypassSecurityTrustResourceUrl("https://localhost:8443/crearpdflistas");
  }

}
