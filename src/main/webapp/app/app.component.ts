import Vue from 'vue';
import Component from 'vue-class-component';
import Ribbon from '@/core/ribbon/ribbon.vue';
import JhiFooter from '@/core/jhi-footer/jhi-footer.vue';
import JhiNavbar from '@/core/jhi-navbar/jhi-navbar.vue';
import LoginForm from '@/account/login-form/login-form.vue';
import waitingPanel from '@/core/SidebarPlugin/SideBar.vue';

import '@/shared/config/dayjs';
import { Inject } from 'vue-property-decorator';
import TranslationService from './locale/translation.service';

@Component({
  components: {
    ribbon: Ribbon,
    'jhi-navbar': JhiNavbar,
    'login-form': LoginForm,
    'waiting-panel': waitingPanel,
    'jhi-footer': JhiFooter,
  },
})
export default class App extends Vue {
  @Inject('translationService') private translationService: () => TranslationService;

  private currentLanguage = this.$store.getters.currentLanguage;

  created() {
    this.translationService().refreshTranslation(this.currentLanguage);
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }
}
