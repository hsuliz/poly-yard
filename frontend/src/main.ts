import './index.css'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { vueKeycloak } from '@josempgon/vue-keycloak'

import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.use(vueKeycloak, {
  config: {
    url: `http://localhost:8080`,
    realm: 'polyyard',
    clientId: 'front'
  },
  initOptions: {
    onLoad: 'check-sso',
    checkLoginIframe: true
  }
})

app.mount('#app')
