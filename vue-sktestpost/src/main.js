// import './assets/w3.css'
import './assets/common.css'

import { createApp } from 'vue'
import App from './App.vue'
import axios from './utils/axios'
import router from './router'
import store from './vuex/store'
import { quillEditor } from 'vue3-quill'

// Quill Editor 스타일
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

const app = createApp(App)
app.config.globalProperties.$axios = axios
app.config.globalProperties.$serverUrl = '//localhost:8081'
app.config.globalProperties.$store = store
app.use(router).use(store).use(quillEditor).mount('#app')
