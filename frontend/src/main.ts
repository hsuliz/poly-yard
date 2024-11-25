import "./index.css"
import { createApp } from "vue"
import { vueKeycloak } from "@josempgon/vue-keycloak"

import App from "./App.vue"
import router from "./router"

const app = createApp(App)

app.use(router)

app.use(vueKeycloak, {
  config: {
    url: "http://localhost:8080",
    realm: "polyyard",
    clientId: "front-end-client"
  },
  initOptions: {
    onLoad: "check-sso",
    checkLoginIframe: true
  }
})

app.mount("#app")
