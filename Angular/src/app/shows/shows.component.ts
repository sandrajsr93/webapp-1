import { Component } from '@angular/core';

import { DecoratorService } from '../header/decorator.service';

import { CommonFunction } from '../common-code/commonfunction.component';
import { ShowService } from './show.service';
import { ListsService } from '../lists/lists.service';
import { Show } from '../Interfaces/Show/show.component';

import { UserComponent } from '../user/user.component';



const NUM_ELEMENS_FROM_PAGE = 10;

@Component({
  selector: 'shows-component',
  templateUrl: './shows.html',
  styleUrls: ['./shows.css']
})

export class ShowsComponent {
  private shows: Show[] = [];
  private showsCarousel: Show[] = [];

  private isShowShows = true;

  private totalPages: number;
  private countPages = 1;
  private morePages = true;

  private addedContent = false;
  private errorAddedContent = false;


  private allShows = {
    "filter-active": true
  }

  private bestShows = {
    "filter-active": false
  }

  constructor(private decorator: DecoratorService, private userComponent: UserComponent, private commonFunction: CommonFunction, private showService: ShowService, private serviceList: ListsService) {
    this.decorator.activeButton("shows");
  }

  ngOnInit() {
    this.loadCorousel();
    this.refresh(0, NUM_ELEMENS_FROM_PAGE);
  }

  private loadCorousel() {
    this.showService.getLastAdded(4).subscribe(
      shows => {
        this.showsCarousel = shows;
        this.showsCarousel[0].firstInList = true;
      }
    );
  }

  private cleanLets() {
    this.shows = [];
    this.countPages = 1;
    this.morePages = true;
    this.allShows["filter-active"] = false;
    this.bestShows["filter-active"] = false;
  }

  private showShows() {
    this.cleanLets();
    this.isShowShows = true;
    this.allShows["filter-active"] = true;
    this.totalPages = this.commonFunction.addElementsToArray(this.shows, this.showService.getShows(0, NUM_ELEMENS_FROM_PAGE));
  }

  private showBestShows() {
    this.cleanLets();
    this.isShowShows = false;
    this.bestShows["filter-active"] = true;
    this.totalPages = this.commonFunction.addElementsToArray(this.shows, this.showService.getBestShows(0, NUM_ELEMENS_FROM_PAGE));
  }

  private refresh(page: number, size: number) {
    this.totalPages = this.commonFunction.addElementsToArray(this.shows, this.showService.getShows(page, size));
    this.decorator.loadSpinner = false;
  }

  private refreshBest(page: number, size: number) {
    this.totalPages = this.commonFunction.addElementsToArray(this.shows, this.showService.getBestShows(page, size));
    this.decorator.loadSpinner = false;
  }

  private loadMore() {
    this.decorator.loadSpinner = true;

    setTimeout(() => {
      if (this.isShowShows) {
        this.refresh(this.countPages, NUM_ELEMENS_FROM_PAGE);
      } else {
        this.refreshBest(this.countPages, NUM_ELEMENS_FROM_PAGE);
      }
      this.countPages++;
      if (this.countPages >= this.totalPages) {
        this.morePages = false;
      }
    }, 500);
  }

  private addElemToList(nameList: string, showName: string){
    let list = {
      name: nameList,
      shows: [showName],
    }
    this.serviceList.addElement(list).subscribe(
      result =>{
        if (result == true){
          this.closeAlerts();
          this.addedContent = true;
          setTimeout(() => this.closeAddedContentAlert(), 20000);
        }else{
          this.closeAlerts();
          this.errorAddedContent = true;
          setTimeout(() => this.closeErrorAddedContentAlert(), 20000);
        }
      }
    )
  }

  private closeAddedContentAlert(){
    this.addedContent = false;
  }

  private closeErrorAddedContentAlert(){
    this.errorAddedContent = false;
  }

  private closeAlerts(){
    this.addedContent = false;
    this.errorAddedContent = false;
  }

}
