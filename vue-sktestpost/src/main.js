// import './assets/w3.css'
import './assets/common.css'

import { createApp } from 'vue'
import App from './App.vue'
import axios from './utils/axios'
import router from './router'
import store from './vuex/store'

const app = createApp(App)
app.config.globalProperties.$axios = axios
app.config.globalProperties.$serverUrl = '//localhost:8081'
app.config.globalProperties.$store = store
app.use(router).use(store).mount('#app')
