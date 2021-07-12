import { Component, Inject, Vue } from 'vue-property-decorator';
import { SERVER_API_URL, VERSION } from '@/constants';
import LoginService from '@/account/login.service';
import AccountService from '@/account/account.service';
import TranslationService from '@/locale/translation.service';
import PatientService from '@/entities/patient/patient.service';
import { IPatient, Patient } from '@/shared/model/patient.model';

@Component
export default class JhiNavbar extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;

  @Inject('translationService') private translationService: () => TranslationService;

  @Inject('accountService') private accountService: () => AccountService;

  @Inject('patientService') private patientService: () => PatientService;

  patients: IPatient[] = [];

  public version = VERSION ? 'v' + VERSION : '';
  private currentLanguage = this.$store.getters.currentLanguage;
  private languages: any = this.$store.getters.languages;
  private hasAnyAuthorityValue = false;

  private baseUrlExport = SERVER_API_URL;

  public selectedpatient: IPatient = new Patient();

  created() {}

  public subIsActive(input) {
    const paths = Array.isArray(input) ? input : [input];
    return paths.some(path => {
      return this.$route.path.indexOf(path) === 0; // current path starts with this path string
    });
  }

  public logout(): void {
    localStorage.removeItem('jhi-authenticationToken');
    sessionStorage.removeItem('jhi-authenticationToken');
    this.$store.commit('logout');
    this.$router.push('/', () => {});
  }

  public changeLanguage(newLanguage: string): void {
    this.translationService().refreshTranslation(newLanguage);
  }

  public isActiveLanguage(key: string): boolean {
    return key === this.$store.getters.currentLanguage;
  }

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public downloadPatientsVisitReport(): void {
    if (this.selectedpatient.id !== null && this.selectedpatient.id !== undefined) {
      window.open(`${this.baseUrlExport}/api/patients/visits/report/${this.selectedpatient.id}/${this.selectedpatient.name}`, '_self');
    }
  }
  public closeDialogPatientVisitReport(): void {
    (<any>this.$refs.patientVisitReport).hide();
  }
  public openDialogPatientVisitReport(): void {
    this.retrivePatients();
  }

  public downloadPatientsReport(): void {
    window.open(`${this.baseUrlExport}/api/patients/report`, '_self');
  }
  public closeDialogPatientReport(): void {
    (<any>this.$refs.patientReport).hide();
  }
  public openDialogPatientReport(): void {
    (<any>this.$refs.patientReport).show();
  }

  public hasAnyAuthority(authorities: any): boolean {
    this.accountService()
      .hasAnyAuthorityAndCheckAuth(authorities)
      .then(value => {
        this.hasAnyAuthorityValue = value;
      });
    return this.hasAnyAuthorityValue;
  }

  public get openAPIEnabled(): boolean {
    return this.$store.getters.activeProfiles.indexOf('api-docs') > -1;
  }

  public get inProduction(): boolean {
    return this.$store.getters.activeProfiles.indexOf('prod') > -1;
  }

  retrivePatients(): void {
    this.patientService()
      .retrieve()
      .then(res => {
        this.patients = res.data;
        (<any>this.$refs.patientVisitReport).show();
      });
  }
}
