import { Component } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { DomSanitizer, SafeResourceUrl, SafeUrl } from '@angular/platform-browser';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { LocationStrategy } from '@angular/common';

import { DecoratorService } from '../header/decorator.service';

import { ShowService } from './show.service';
import { Show } from '../Interfaces/Show/show.component';
import { CommentShow } from '../Interfaces/Show/commentshow.component';
import { PointShow } from '../Interfaces/Show/pointshow.component';

import { UserComponent } from '../user/user.component';

@Component({
  selector: 'show-component',
  templateUrl: './show.html',
  styleUrls: ['./show.css']
})

export class ShowComponent {
  private isPopState = false;
  private nameShow: string;
  private show: Show;
  private startShow: number;
  private startShowUser = 0;
  private url: SafeResourceUrl;
  private commentsShow: CommentShow[] = [];
  private relationShow: Show[] = [];
  private validationMessageForm: FormGroup;

  constructor(private decorator: DecoratorService, private userComponent: UserComponent, private sanitizer: DomSanitizer, private router: Router, private activatedRoute: ActivatedRoute, private showService: ShowService, private fb: FormBuilder, private locStrat: LocationStrategy) { 
    this.decorator.clearActive();

    this.validationMessageForm = fb.group({
      'message': [null, Validators.required],
    });
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe(
      params => {
        this.nameShow = params["name"];
        this.loadShow();
        this.loadComments();
      }
    );

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

  private loadShow() {
    this.showService.getShow(this.nameShow).subscribe(
      show => {
        this.show = show;
        this.url = this.sanitizer.bypassSecurityTrustResourceUrl(this.show.trailer);
        this.loadRealted();
        this.loadPointShow();
      }
    )
  }

  private loadPointShow(){
    this.showService.getPointsShow(this.nameShow).subscribe(
      pointShow => 
      {
        let sum = 0;

        for(let pShow of pointShow){
          if(this.userComponent.user !== undefined && this.userComponent.user.name === pShow.user.name){
            this.startShowUser = pShow.points;
          }

          sum += pShow.points;
        }

        sum /= pointShow.length;
        this.startShow = sum;
      }
    )
  }

  private loadComments() {
    this.showService.getCommentsShow(this.nameShow).subscribe(
      comments => {
        this.commentsShow = comments;
      }
    )
  }

  private loadRealted() {
    this.showService.getRelationShow(this.show.id, 0, 8).subscribe(
      related => this.relationShow = related.content
    )
  }

  private changePointUser() {
    this.showService.updatePoint(this.nameShow, { points: this.startShowUser }).subscribe(
      response => {
        this.loadPointShow();
      }
    )
  }

  private deleteComment(id: number){
    this.showService.deleteCommentShow(id).subscribe(
      response => {
        this.loadComments();
      }
    );
  }

  private newComment(message: string) {
    this.validationMessageForm = this.fb.group({
      'message': [null, Validators.required],
    });

    this.showService.newCommentShow(this.nameShow, { comment: message }).subscribe(
      response => {
        this.loadComments();
      }
    )
  }
}
