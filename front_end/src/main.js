import Vue from 'vue';
import App from './App.vue';
import router from './router';
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.less';
import store from './store';

import '@/permission';

Vue.config.productionTip = false;
const options = {
    namespace: 'pro__',
    name: 'ls',
    storage: 'local',
};
Vue.use(Antd);

Vue.directive('title', {
    inserted: function (el) {
        document.title = el.dataset.title;
    },
});

Vue.directive('focus', {
    inserted: function (el) {
        el.focus();
    },
});

new Vue({
    el: '#app',
    router,
    store,
    options,
    render: h => h(App),
});
