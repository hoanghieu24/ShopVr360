import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router/index.js'
import 'vue3-toastify/dist/index.css'
import Vue3Toastify from 'vue3-toastify'
import '@fortawesome/fontawesome-free/css/all.min.css'

const app = createApp(App)

app.use(router)
app.use(Vue3Toastify, {
  autoClose: 2500,
})

app.mount('#app')  // Chỉ mount MỘT lần