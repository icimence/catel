import Vue from 'vue';
import axios from 'axios';
import {VueAxios} from './axios';
import {message, notification} from 'ant-design-vue';
import store from '@/store';

// 创建 axios 实例
const service = axios.create({
    baseURL: process.env.NODE_ENV === 'production' ? '' : 'http://localhost:8080',
    withCredentials: true,
});
console.log(process.env.NODE_ENV);
const err = (error) => {
    if (error.response) {
        const data = error.response.data;
        const token = Vue.ls.get('ACCESS_TOKEN');
        if (error.response.status === 403) {
            notification.error({
                message: 'Forbidden',
                description: data.message,
            });
        }
        if (error.response.status === 401 && !(data.result && data.result.isLogin)) {
            notification.error({
                message: 'Unauthorized',
                description: 'Authorization verification failed',
            });
            if (token) {
                store.dispatch('Logout').then(() => {
                    setTimeout(() => {
                        window.location.reload();
                    }, 1500);
                });
            }
        }
    }
    return Promise.reject(error);
};

//request incerceptor
service.interceptors.request.use((config) => {
    const requestConfig = {
        ...config,
        url: `${config.url}`,
    };
    return requestConfig;
}, err);

service.interceptors.response.use((response) => {
    switch (response.status) {
        case 200:
            if (response.data.success) {
                if (response.data.message) message.success(response.data.message);
                return response.data;
            } else {
                message.error(response.data.message);
                return response.data;
            }
        case 404:
            return false;
        default:
            message.error(response.data.message);
    }
});

const installer = {
    vm: {},
    install(Vue) {
        Vue.use(VueAxios, service);
    },
};

export {
    installer as VueAxios,
    service as axios,
};
