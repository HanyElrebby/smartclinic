import VisitService from '@/entities/visit/visit.service';
import { IVisit } from '@/shared/model/visit.model';
import { Component, Vue } from 'vue-property-decorator';
import { Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Visit extends Vue {
  @Inject('visitService') private visitService: () => VisitService;

  public visits: IVisit[] = [];

  public loadWaited() {
    this.visitService()
      .retrieveWaited()
      .then(res => {
        this.visits = res.data;
      });
  }

  public mounted(): void {
    console.log('tttttttttttttttttttttttttttttt');

    this.loadWaited();
  }
}
