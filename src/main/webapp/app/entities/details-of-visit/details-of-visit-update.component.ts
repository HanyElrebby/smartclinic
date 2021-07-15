import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, maxLength } from 'vuelidate/lib/validators';

import VisitService from '@/entities/visit/visit.service';
import { IVisit } from '@/shared/model/visit.model';

import { IDetailsOfVisit, DetailsOfVisit } from '@/shared/model/details-of-visit.model';
import DetailsOfVisitService from './details-of-visit.service';

const validations: any = {
  detailsOfVisit: {
    descriptionAilments: {
      required,
      maxLength: maxLength(50),
    },
    nameOfDisease: {
      required,
      maxLength: maxLength(30),
    },
    recommendations: {
      maxLength: maxLength(30),
    },
    medicines: {
      maxLength: maxLength(30),
    },
    dosage: {
      maxLength: maxLength(30),
    },
  },
};

@Component({
  validations,
})
export default class DetailsOfVisitUpdate extends Vue {
  @Inject('detailsOfVisitService') private detailsOfVisitService: () => DetailsOfVisitService;
  public detailsOfVisit: IDetailsOfVisit = new DetailsOfVisit();

  @Inject('visitService') private visitService: () => VisitService;

  public visits: IVisit[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.detailsOfVisitId) {
        vm.retrieveDetailsOfVisit(to.params.detailsOfVisitId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }

  public translate(key: string): string {
    return this.$t(key) as string;
  }

  public save(): void {
    this.isSaving = true;
    if (this.detailsOfVisit.id) {
      this.detailsOfVisit.createdBy = this.username;
      this.detailsOfVisit.updatedBy = this.username;
      this.detailsOfVisitService()
        .update(this.detailsOfVisit)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A DetailsOfVisit is updated with identifier ' + param.id;
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.detailsOfVisit.updatedBy = this.username;
      this.detailsOfVisitService()
        .create(this.detailsOfVisit)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A DetailsOfVisit is created with identifier ' + param.id;
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveDetailsOfVisit(detailsOfVisitId): void {
    this.detailsOfVisitService()
      .find(detailsOfVisitId)
      .then(res => {
        this.detailsOfVisit = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.visitService()
      .retrieve()
      .then(res => {
        this.visits = res.data;
      });
  }
}
