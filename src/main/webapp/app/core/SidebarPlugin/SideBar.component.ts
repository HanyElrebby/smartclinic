import AccountService from '@/account/account.service';
import VisitService from '@/entities/visit/visit.service';
import { EventBus } from '@/event-bus';
import { IVisit } from '@/shared/model/visit.model';
import { Component, Vue } from 'vue-property-decorator';
import { Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import TrackerService from './tracker.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Visit extends Vue {
  @Inject('visitService') private visitService: () => VisitService;
  @Inject('trackerService') private trackerService: () => TrackerService;
  @Inject('accountService') private accountService: () => AccountService;

  visits: IVisit[] = [];

  public loadWaited() {
    this.visitService()
      .retrieveWaited()
      .then(res => {
        res.data;
        console.log(this.visits);
      });
  }

  public destroyed(): void {
    this.trackerService().unsubscribe();
  }

  public init(): void {
    console.log('sb --------->');

    this.trackerService().subscribe();
    this.trackerService()
      .receive()
      .subscribe(activity => {
        this.loadWaited();
      });
  }

  public mounted(): void {
    console.log('tttttttttttttttttttttttttttttt');
    this.init();
    EventBus.$on('waitedVisits', waitedVisits => {
      this.visits = waitedVisits;
    });
    this.loadWaited();
  }
}
